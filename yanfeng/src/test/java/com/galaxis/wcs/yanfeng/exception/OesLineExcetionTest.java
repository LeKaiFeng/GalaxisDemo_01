package com.galaxis.wcs.yanfeng.exception;

import com.galaxis.wcs.yanfeng.util.Constance;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OesLineExcetionTest {
    private static final Logger log = LoggerFactory.getLogger(OesLineExcetionTest.class);

    @Test
    public void testEx01() {
        try {
            throw new OesLineExcetion(Constance.LINE_NONE, 11, "err %s", "e11");
        } catch (OesLineExcetion e) {
            log.info("{}", e.getLocation());
            log.warn(e.getMessage(), e.getLocalizedMessage());
        } catch (OesException e) {
            log.warn(e.getMessage(), e);
        }
    }

}