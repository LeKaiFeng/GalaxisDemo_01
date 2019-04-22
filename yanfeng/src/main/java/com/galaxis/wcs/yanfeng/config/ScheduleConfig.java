package com.galaxis.wcs.yanfeng.config;

import com.galaxis.wcs.yanfeng.util.PrefixThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableScheduling
public class ScheduleConfig implements SchedulingConfigurer {
    private static final Logger log = LoggerFactory.getLogger(ScheduleConfig.class);

    public static final String SCHEDULE_DELAY_INIT = "${schedule.delay.init}";
    public static final String SCHEDULE_DELAY_SHORT = "${schedule.delay.short}";
    public static final String SCHEDULE_DELAY_MIDDLE = "${schedule.delay.middle}";
    public static final String SCHEDULE_DELAY_LONG = "${schedule.delay.long}";
    public static final String SCHEDULE_DELAY_SYNC_BOX = "${schedule.delay.sync-box}";

    @Value("${schedule.pool.core}")
    public int corePoolSize = 8;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        log.info("任务调度线程池的核心线程数: {}", corePoolSize);
        ThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(
                corePoolSize
                , new PrefixThreadFactory("schedule-service-")
                , new ThreadPoolExecutor.DiscardOldestPolicy());
        // 设置线程池处理
        taskRegistrar.setScheduler(executor);
    }
}
