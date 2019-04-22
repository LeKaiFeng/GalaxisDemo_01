package com.galaxis.wcs.yanfeng.database.oes.service.impl;

import com.galaxis.wcs.yanfeng.database.oes.domain.Rezult;
import com.galaxis.wcs.yanfeng.database.oes.domain.RezultExample;
import com.galaxis.wcs.yanfeng.database.oes.mapper.MyBatisBaseDao;
import com.galaxis.wcs.yanfeng.database.oes.mapper.RezultMapper;
import com.galaxis.wcs.yanfeng.database.oes.service.RezultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RezultServiceImpl extends AbstractServiceImpl<Rezult, Integer, RezultExample> implements RezultService {

    @Autowired
    private RezultMapper rezultMapper;

    @Override
    protected MyBatisBaseDao getMapper() {
        return rezultMapper;
    }

}
