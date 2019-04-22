package com.galaxis.wcs.yanfeng.device.library.controller;

import com.galaxis.wcs.yanfeng.database.oes.service.KtService;
import com.galaxis.wcs.yanfeng.device.library.YanFengTask;
import com.galaxis.wcs.yanfeng.util.Constance;
import com.galaxis.wcs.yanfeng.util.template.OesRequest;
import com.galaxis.wcs.yanfeng.work.manager.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/lib")
public class LibraryController {
    private static final Logger log = LoggerFactory.getLogger(LibraryController.class);

    @Autowired
    private ConfigManager configManager;
    @Autowired
    private YanFengTask yanFengTask;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private KtService ktService;

    @RequestMapping("/quality")
    public String quality(OesRequest request) {
        log.info("web发来质检/盘点任务, request: {}", request);
        return yanFengTask.qualityCheck(request);
    }

    @RequestMapping("/kt")
    public String kt(Integer ktId) {
        log.info("web发来KT叫料请求, ktId: {}", ktId);
        String message = ktService.checkAllow();
        if (!Constance.SUCCESS.equalsIgnoreCase(message)) {
            log.info(message);
            return message;
        }

        String result = yanFengTask.kt(ktId, Constance.KT_TYPE_MANUAL);
        if (Constance.SUCCESS.equals(result)) {
            ktService.incrementNumber(ktId, 1, "number");
            log.info("修改KT number +1, kt id: {}", ktId);
        }
        return result;
    }

    @RequestMapping("/clearKt")
    public Integer clearKt(Integer ktId) {
        log.info("web发来终止待补货任务请求, ktId: {}", ktId);
        int total = 0;
        // 清除缓存kt任务
        Long cache = redisTemplate.opsForList().remove(Constance.KEY_OES_KT_CACHE, 0, ktId);
        log.info("清除KT叫料任务缓存, ktId: {}, 清除条数: {}", ktId, cache);
        if (Objects.nonNull(cache)) {
            total += cache;
        }

        // 减去对应kt的number数
        ktService.incrementNumber(ktId, -total, "number");
        log.info("修改KT number {}, kt id: {}", -total, ktId);
        return total;
    }

}
