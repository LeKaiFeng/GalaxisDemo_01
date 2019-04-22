package com.galaxis.wcs.yanfeng.database.oes.service.impl;

import com.galaxis.wcs.yanfeng.database.oes.domain.Configure;
import com.galaxis.wcs.yanfeng.database.oes.domain.ConfigureExample;
import com.galaxis.wcs.yanfeng.database.oes.mapper.ConfigureMapper;
import com.galaxis.wcs.yanfeng.database.oes.mapper.MyBatisBaseDao;
import com.galaxis.wcs.yanfeng.database.oes.service.ConfigureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigureServiceImpl extends AbstractServiceImpl<Configure, Integer, ConfigureExample> implements ConfigureService {

    @Autowired
    private ConfigureMapper configureMapper;

    @Override
    protected MyBatisBaseDao getMapper() {
        return configureMapper;
    }

}
