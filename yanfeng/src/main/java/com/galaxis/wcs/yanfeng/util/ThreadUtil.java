package com.galaxis.wcs.yanfeng.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class ThreadUtil {
    private static final Logger log = LoggerFactory.getLogger(ThreadUtil.class);

    private static ThreadFactory threadFactory = new SimpleThreadFactory();

    public static final int SLEEP_SHORT = 1000;
    public static final int SLEEP_MIDDLE = 5 * 1000;
    public static final int SLEEP_LONG = 10 * 1000;

    private static final int CORE_POOL_SIZE = 64;
    private static final int MAX_POOL_SIZE = 512;

    private static final ExecutorService POOL = new ThreadPoolExecutor(
            CORE_POOL_SIZE, MAX_POOL_SIZE
            , 60, TimeUnit.SECONDS
            , new LinkedBlockingQueue<>(1024)
            , new PrefixThreadFactory("oes-thread-pool-"));

    public static void execute(Runnable task) {
        POOL.execute(task);
    }

    public static <T> Future<T> submit(Callable<T> callable) {
        return POOL.submit(callable);
    }

    public static Thread createThread(String name, Runnable runnable) {
        Thread thread = threadFactory.newThread(runnable);
        thread.setName(name);
        return thread;
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.error("sleep异常: {}", e.getMessage(), e);
        }
    }

    private static class SimpleThreadFactory implements ThreadFactory {

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r);
        }
    }

}
