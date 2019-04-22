package com.galaxis.wcs.yanfeng.time;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class TimeTest {
    private static final Logger log = LoggerFactory.getLogger(TimeTest.class);

    @Test
    public void testStamp() {
        long epochSecond = Instant.now().toEpochMilli();
        Date date = new Date(epochSecond);
        log.info("{}",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
    }
}
