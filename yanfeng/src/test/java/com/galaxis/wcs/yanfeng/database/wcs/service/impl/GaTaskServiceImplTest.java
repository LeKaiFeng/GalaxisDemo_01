package com.galaxis.wcs.yanfeng.database.wcs.service.impl;

import com.galaxis.wcs.yanfeng.database.wcs.domain.GaTask;
import com.galaxis.wcs.yanfeng.database.wcs.service.GaTaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GaTaskServiceImplTest {
    @Autowired
    private GaTaskService gaTaskService;

    @Test
    public void addPriority() {
        GaTask gaTask = new GaTask();
        gaTask.setBoxNumber("08-WH0050");
        gaTask.setWmsid("100000104");
        gaTask.setPriority(100);
        int i = gaTaskService.addPriority(gaTask);
        System.out.println("i = " + i);
    }
}