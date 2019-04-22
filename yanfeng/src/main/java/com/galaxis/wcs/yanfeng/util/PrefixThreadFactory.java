package com.galaxis.wcs.yanfeng.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class PrefixThreadFactory implements ThreadFactory {

    private final String prefix;
    private AtomicInteger atomic = new AtomicInteger(0);

    public PrefixThreadFactory(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        return ThreadUtil.createThread(prefix + atomic.getAndIncrement(), r);
    }
}
