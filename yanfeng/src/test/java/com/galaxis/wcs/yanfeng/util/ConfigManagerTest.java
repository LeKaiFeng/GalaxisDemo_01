package com.galaxis.wcs.yanfeng.util;

import com.galaxis.wcs.yanfeng.work.manager.ConfigManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfigManagerTest {
    @Autowired
    private ConfigManager configManager;

    @Test
    public void testCon() throws InterruptedException {
        while (true) {
            String value = configManager.getConfigure("123");
            System.out.println(value);
            Thread.sleep(5000);
        }
    }
}