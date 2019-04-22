package com.galaxis.wcs.yanfeng.device.library.service.impl;

import com.alibaba.fastjson.JSON;
import com.galaxis.wcs.yanfeng.database.oes.domain.Box;
import com.galaxis.wcs.yanfeng.database.oes.domain.Kt;
import com.galaxis.wcs.yanfeng.database.oes.domain.KtExample;
import com.galaxis.wcs.yanfeng.database.oes.domain.Location;
import com.galaxis.wcs.yanfeng.database.oes.service.BoxService;
import com.galaxis.wcs.yanfeng.database.oes.service.KtService;
import com.galaxis.wcs.yanfeng.database.oes.service.LocationService;
import com.galaxis.wcs.yanfeng.database.oes.service.TaskService;
import com.galaxis.wcs.yanfeng.database.wcs.domain.GaFlashstate;
import com.galaxis.wcs.yanfeng.database.wcs.domain.GaFlashstateExample;
import com.galaxis.wcs.yanfeng.database.wcs.service.GaFlashStateService;
import com.galaxis.wcs.yanfeng.device.library.service.InboundService;
import com.galaxis.wcs.yanfeng.util.Config;
import com.galaxis.wcs.yanfeng.util.Constance;
import com.galaxis.wcs.yanfeng.util.OesUtil;
import com.galaxis.wcs.yanfeng.work.manager.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InboundServiceImpl implements InboundService {
    private static final Logger log = LoggerFactory.getLogger(InboundServiceImpl.class);

    @Autowired
    private ConfigManager configManager;
    @Autowired
    private BoxService boxService;
    @Autowired
    private KtService ktService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private GaFlashStateService gaFlashStateService;

    @Override
    public Location computeLocation(String cartonNo, List<Integer> allowLevel) {
        Box box = boxService.getBoxByCartonNo(cartonNo);
        Boolean isLong = boxService.isLength(box);

        Set<Integer> levelSet = new LinkedHashSet<>();

        // 不入kT, 查找sku对应的KT列表, KT所在层有的该partNo数 除以 该层的该总KT数, 选择partNo平均数最少的一层
        TreeSet<Box> boxes = bestLevel(box, allowLevel);
        List<Integer> bestLevels = boxes.stream().map(Box::getLevel).collect(Collectors.toList());
        log.debug("bestLevels: {}, 对应treeSet: {}", bestLevels, JSON.toJSONString(boxes));
        levelSet.addAll(bestLevels);

        // 有车非kt层 10,12,22是故障, 直接不分配
        List<Integer> libLevels = locationService.getLevels();
        GaFlashstateExample gaFlashstateExample = new GaFlashstateExample();
        gaFlashstateExample.createCriteria()
                .andCarLevelIn(libLevels);
        List<GaFlashstate> gaFlashstates = gaFlashStateService.selectByExample(gaFlashstateExample);
        List<Integer> ktLevels = Arrays.asList(1, 2, 3);
        List<Integer> gaFlashStateLevels = gaFlashstates.stream().map(GaFlashstate::getCarLevel)
                // 排除kt层
                .filter(l -> !ktLevels.contains(l))
                .collect(Collectors.toList());
        log.debug("小车分布层(排除kt层) : {}", gaFlashStateLevels);
        levelSet.addAll(gaFlashStateLevels);

        // 异常小车所在层
        List<Integer> errorFlashLevels = gaFlashstates.stream()
                .filter(f -> Arrays.asList(10, 12, 22).contains(f.getFlashState()))
                .map(GaFlashstate::getCarLevel)
                .collect(Collectors.toList());
        log.debug("异常小车分布层: {}", errorFlashLevels);

        // 非该sku的kt层
        levelSet.addAll(ktLevels);

        // 无车层
        levelSet.addAll(libLevels);

        List<Integer> levels = levelSet.stream()
                // 异常小车所在层不能去
                .filter(l -> !errorFlashLevels.contains(l))
                // 如果有allowLevel限制, 则level必须在allowLevel中
                .filter(l -> {
                    if (Objects.isNull(allowLevel) || allowLevel.isEmpty()) {
                        return true;
                    }
                    return allowLevel.contains(l);
                })
                // 过滤掉输送线层和pd层
                .filter(l -> l > 0 && l < 100)
                .collect(Collectors.toList());
        log.debug("最终计算层: {}", levels);

        Map<Integer, Integer> mapLevelCountInboundTask = taskService.mapLevelCountByPartNo(null
                , Arrays.asList(Constance.TASK_TYPE_INBOUND)
                , Constance.TASK_STATUS_RUNNING_LIST);
        libLevels.forEach(l -> mapLevelCountInboundTask.putIfAbsent(l, 0));
        log.debug("各层入库任务数: {}", mapLevelCountInboundTask);

        // 进行层出入任务数校验
        boolean inboundMode = Config.VALUE_MODE_COMPUTER_LEVER_INBOUND.equalsIgnoreCase(configManager.getConfigure(Config.KEY_MODE_COMPUTER_LEVER));
        Integer maxCacheInbound = configManager.getNumber(Config.KEY_MAX_CACHE_INBOUND, 3);
        for (Integer level : levels) {
            // 入库模式下, 如果某层的入库任务数大于阈值, 则不计算该层
            if (inboundMode && mapLevelCountInboundTask.getOrDefault(level, 0) >= maxCacheInbound) {
                continue;
            }

            Location freeLocation = locationService.getFreeLocation(level, null, isLong, box);
            if (Objects.nonNull(freeLocation)) {
                return freeLocation;
            }
        }

        // 不进行层出入任务数校验, 但是考虑每层入库任务数
        List<Integer> levelsSortByTask = OesUtil.sortMapByValue(mapLevelCountInboundTask, false)
                .stream().filter(l -> !errorFlashLevels.contains(l)).collect(Collectors.toList());
        log.info("不进行入库任务数阈值检查, 在以下层位找空库位进行入库, levels: {}", levelsSortByTask);
        for (Integer level : levelsSortByTask) {
            Location freeLocation = locationService.getFreeLocation(level, null, isLong, box);
            if (Objects.nonNull(freeLocation)) {
                return freeLocation;
            }
        }

        return null;
    }

    /**
     * 检查该层允许的入库数是否大于阈值
     */

    private TreeSet<Box> bestLevel(Box box, List<Integer> allowLevel) {
        KtExample ktExample;// 该partNo的kt
        ktExample = new KtExample();
        KtExample.Criteria ktCriteria = ktExample.createCriteria()
                .andPartNoEqualTo(box.getPartNo());
        if (Objects.nonNull(allowLevel) && allowLevel.size() > 0) {
            ktCriteria.andLevelIn(allowLevel);
        }

        List<Kt> ktList = ktService.selectByExample(ktExample);
        log.debug("kt list: {}", JSON.toJSONString(ktList));
        // kt的 level -> count
        Map<Integer, Integer> ktLevelCount = new HashMap<>();
        ktList.stream().map(Kt::getLevel).forEach(lvl -> {
            Integer count = ktLevelCount.getOrDefault(lvl, 0) + 1;
            ktLevelCount.put(lvl, count);
        });
        log.debug("ktLevelCount: {}", JSON.toJSONString(ktLevelCount));

        // box的 level -> count
        Map<Integer, Integer> boxLevelCount = boxService.mapLevelCountByPartNo(box.getPartNo());
        log.debug("boxLevelCount: {}", JSON.toJSONString(boxLevelCount));
        // 正在入库的子任务 level -> count
        Map<Integer, Integer> taskLevelCount = taskService.mapLevelCountByPartNo(box.getPartNo()
                , Arrays.asList(Constance.TASK_TYPE_INBOUND)
                , Constance.TASK_STATUS_RUNNING_LIST);
        log.debug("inboundTaskLevelCount: {}", JSON.toJSONString(taskLevelCount));

        // 计算每层平均的partNo, 平均数少 ↑, 楼层低 ↑
        TreeSet<Box> treeSet = new TreeSet<>((box1, box2) -> {
            // 暂时使用qty封装平均数
            int i = box1.getQty().compareTo(box2.getQty());
            if (i != 0) {
                return i;
            }
            return box1.getLevel().compareTo(box2.getLevel());
        });
        // 添加到treeSet并自动排序
        ktLevelCount.forEach((l, c) -> {
            BigDecimal boxCount = BigDecimal.valueOf(boxLevelCount.getOrDefault(l, 0));
            BigDecimal taskCount = BigDecimal.valueOf(taskLevelCount.getOrDefault(l, 0));
            // 库内box占用 + 入库任务占用
            BigDecimal occupyCount = boxCount.add(taskCount);
            BigDecimal ktCount = BigDecimal.valueOf(c);
            Box bx = new Box();
            bx.setLevel(l);
            // 除法, 保留3位小数, 四舍五入
            bx.setQty(occupyCount.divide(ktCount, 3, RoundingMode.HALF_UP));
            treeSet.add(bx);
        });
        return treeSet;
    }
}
