package com.galaxis.wcs.yanfeng.database.wcs.service.impl;

import com.galaxis.wcs.yanfeng.database.oes.mapper.MyBatisBaseDao;
import com.galaxis.wcs.yanfeng.database.oes.service.impl.AbstractServiceImpl;
import com.galaxis.wcs.yanfeng.database.wcs.domain.GaTask;
import com.galaxis.wcs.yanfeng.database.wcs.domain.GaTaskExample;
import com.galaxis.wcs.yanfeng.database.wcs.mapper.GaTaskMapper;
import com.galaxis.wcs.yanfeng.database.wcs.service.GaTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GaTaskServiceImpl extends AbstractServiceImpl<GaTask, Integer, GaTaskExample> implements GaTaskService {
    private static final Logger log = LoggerFactory.getLogger(GaTaskServiceImpl.class);

    @Autowired
    private GaTaskMapper gaTaskMapper;

    @Override
    protected MyBatisBaseDao<GaTask, Integer, GaTaskExample> getMapper() {
        return gaTaskMapper;
    }

    @Override
    public int addPriority(GaTask gaTask) {
        return gaTaskMapper.incrementPriority(gaTask.getWmsid(), gaTask.getBoxNumber(), gaTask.getPriority());
    }
}
