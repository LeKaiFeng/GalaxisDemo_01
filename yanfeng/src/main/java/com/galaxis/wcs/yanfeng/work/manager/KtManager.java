package com.galaxis.wcs.yanfeng.work.manager;

import com.galaxis.wcs.yanfeng.config.ScheduleConfig;
import com.galaxis.wcs.yanfeng.database.oes.domain.Kt;
import com.galaxis.wcs.yanfeng.database.oes.domain.MainTask;
import com.galaxis.wcs.yanfeng.database.oes.domain.MainTaskExample;
import com.galaxis.wcs.yanfeng.database.oes.service.KtService;
import com.galaxis.wcs.yanfeng.database.oes.service.MainTaskService;
import com.galaxis.wcs.yanfeng.database.oes.service.MessageService;
import com.galaxis.wcs.yanfeng.device.library.YanFengTask;
import com.galaxis.wcs.yanfeng.device.line.YanFengKtInboundHandler;
import com.galaxis.wcs.yanfeng.util.Constance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class KtManager {

    private static final Logger log = LoggerFactory.getLogger(KtManager.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private MessageService messageService;
    @Autowired
    private YanFengTask yanFengTask;
    @Autowired
    private MainTaskService mainTaskService;
    @Autowired
    private KtService ktService;

    @Scheduled(initialDelayString = "10000", fixedDelayString = ScheduleConfig.SCHEDULE_DELAY_SHORT)
    public void ktScheduled() {
        String message = ktService.checkAllow();
        if (!Constance.SUCCESS.equalsIgnoreCase(message)) {
            log.debug(message);
            return;
        }

        // 处理缓存中的kt叫料
        cacheKt();

        // 处理自动kt叫料
        autoKt();

    }

    private void autoKt() {
        // 获取当前时刻的kt状态列表
        byte[] ktStatuses = YanFengKtInboundHandler.ktStatuses;

        // 对比状态, 发现KT叫料字id
        List<Integer> plcKts = new ArrayList<>();
        for (int i = 1; i < ktStatuses.length; i++) {
            if (ktStatuses[i] == Constance.KT_STATUS_FREE) {
                plcKts.add(i);
            }
        }

        if (plcKts.isEmpty()) {
            log.debug("无KT叫料...");
            return;
        }

        log.info("自动叫料kts: {}", plcKts);

        // 判断叫料id有无对应的任务在执行, 或者缓存中有无该叫料id, 没有才生成叫料任务
        // redis缓存中的kt任务
        List<Integer> cacheKtIds = redisTemplate.opsForList()
                .range(Constance.KEY_OES_KT_CACHE, 0, -1)
                .stream().map(o -> Integer.valueOf(o.toString()))
                .distinct()
                .collect(Collectors.toList());
        // 正在运行的KT叫料任务
        MainTaskExample mainTaskExample = new MainTaskExample();
        mainTaskExample.createCriteria()
                .andStatusIn(Constance.TASK_STATUS_RUNNING_LIST)
                .andKtIdIsNotNull();
        List<MainTask> mainTasks = mainTaskService.selectByExample(mainTaskExample);
        List<Integer> dbKtIds = mainTasks.stream().map(MainTask::getKtId).distinct().collect(Collectors.toList());

        // 传感器有效的的kt
        List<Integer> validKtIds = ktService.listBySensor(Constance.KT_SENSOR_VALID)
                .stream().map(Kt::getId).collect(Collectors.toList());

        // 允许补料的kt
        List<Integer> allowKtIds = ktService.listByStatus(Constance.KT_STATUS_ALLOW)
                .stream().map(Kt::getId).collect(Collectors.toList());

        List<Integer> kts = plcKts.stream()
                // 过滤掉缓存中的
                .filter(ktId -> !cacheKtIds.contains(ktId))
                // 过滤掉有任务的
                .filter(ktId -> !dbKtIds.contains(ktId))
                // 过滤掉传感器无效的
                .filter(validKtIds::contains)
                // 过滤掉禁止补货的
                .filter(allowKtIds::contains)
                .collect(Collectors.toList());

        log.info("开始kt补料计算, kts: {}", kts);
        long s = System.currentTimeMillis();
        kts.parallelStream().forEach(ktId -> {
            // 并行, 依次执行
            log.info("向kt补料, id: {}", ktId);
            String result = yanFengTask.kt(ktId, Constance.KT_TYPE_AUTO);
            if (Constance.SUCCESS.equals(result)) {
                ktService.incrementNumber(ktId, 1, "number");
                log.info("修改KT number +1, kt id: {}", ktId);
            }
        });
        long e = System.currentTimeMillis();
        log.debug("计算kt补料完成, 个数: {}, 耗时: {}", kts.size(), e - s);
    }

    public void cacheKt() {
        List<Integer> cacheKtIds = messageService.getList(Constance.KEY_OES_KT_CACHE, true);
        log.info("缓存kt叫料: {}", cacheKtIds);
        cacheKtIds.parallelStream().forEach(this::ktCache);
    }

    public void ktCache(Integer ktId) {
        Kt kt = ktService.selectByPrimaryKey(ktId);
        String s = yanFengTask.checkKt(kt);
        if (!Constance.SUCCESS.equals(s)) {
            log.warn("{}, ktId: {}", s, ktId);
            return;
        }
        // 执行kt补料
        yanFengTask.executeKt(kt, Constance.KT_CACHE);

    }
}
