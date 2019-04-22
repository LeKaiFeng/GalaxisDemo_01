package com.galaxis.wcs.yanfeng.work.manager;

import com.galaxis.wcs.yanfeng.config.ScheduleConfig;
import com.galaxis.wcs.yanfeng.database.oes.domain.Configure;
import com.galaxis.wcs.yanfeng.database.oes.domain.ConfigureExample;
import com.galaxis.wcs.yanfeng.database.oes.service.ConfigureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ConfigManager {
    private static final Logger log = LoggerFactory.getLogger(ConfigManager.class);

    @Autowired
    private ConfigureService configureService;

    private volatile Map<String, String> configureMap = new HashMap<>();

    /**
     * 自动同步配置
     */
    @Scheduled(initialDelayString = "0", fixedDelayString = ScheduleConfig.SCHEDULE_DELAY_MIDDLE)
    public void syncConfigure() {
        log.debug("开始同步配置...");
        List<Configure> configures = configureService.selectByExample(new ConfigureExample());

        AtomicInteger i = new AtomicInteger(0);
        Map<String, String> map = new HashMap<>();
        configures.forEach(configure -> {
            map.put(configure.getName(), configure.getValue());
            i.incrementAndGet();

        });
        configureMap = map;
        log.debug("同步配置完成! 共同步了 {}条", i.intValue());
    }

    public String getConfigure(String name) {
        return configureMap.get(name);
    }

    public String getConfigure(String name, String defaultValue) {
        return configureMap.getOrDefault(name, defaultValue);
    }

    public Integer getNumber(String name) {
        String value = getConfigure(name);
        if (Objects.isNull(value)) {
            return 0;
        }

        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            log.error("请检查数据库t_configure表, 该项的值应为整数, name: {}, value: {}, e: {}", name, value, e.getMessage());
            return 0;
        }
    }

    public Integer getNumber(String name, Integer defaultValue) {
        String value = getConfigure(name);
        if (Objects.isNull(value)) {
            log.debug("配置项{} 没查到值, 返回默认值: {}", name, defaultValue);
            return defaultValue;
        }

        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            log.error("请检查数据库t_configure表, 该项的值应为整数, name: {}, value: {}, 返回默认值: {}, e: {}"
                    , name, value, defaultValue, e.getMessage());
            return defaultValue;
        }

    }
}
