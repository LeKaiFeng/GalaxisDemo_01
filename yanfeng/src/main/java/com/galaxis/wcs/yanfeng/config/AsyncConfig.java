package com.galaxis.wcs.yanfeng.config;

import com.galaxis.wcs.yanfeng.util.PrefixThreadFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//@Configuration
//@EnableAsync
public class AsyncConfig implements AsyncConfigurer {
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                4, 8
                // 0 ms 回收线程(不自动回收线程)
                , 600L, TimeUnit.SECONDS
                , new LinkedBlockingQueue<>(16)
                , new PrefixThreadFactory("async-")
                /**
                 * 任务拒绝策略
                 * ThreadPoolExecutor.AbortPolicy()：    被拒绝后抛出RejectedExecutionException异常
                 * ThreadPoolExecutor.CallerRunsPolicy()：   被拒绝后给调用线程池的线程处理
                 * ThreadPoolExecutor.DiscardOldestPolicy()：    被拒绝后放弃队列中最旧的未处理的任务
                 * ThreadPoolExecutor.DiscardPolicy()：  被拒绝后放弃被拒绝的任务(当前新添加的任务)
                 */
                , new ThreadPoolExecutor.DiscardOldestPolicy());

        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }
}
