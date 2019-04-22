package com.galaxis.wcs.yanfeng.database.oes.service.impl;

import com.galaxis.wcs.yanfeng.database.oes.domain.Quality;
import com.galaxis.wcs.yanfeng.database.oes.domain.QualityExample;
import com.galaxis.wcs.yanfeng.database.oes.mapper.QualityMapper;
import com.galaxis.wcs.yanfeng.database.oes.mapper.MyBatisBaseDao;
import com.galaxis.wcs.yanfeng.database.oes.service.QualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QualityServiceImpl extends AbstractServiceImpl<Quality, Integer, QualityExample> implements QualityService {

    @Autowired
    private QualityMapper qualityMapper;

    @Override
    protected MyBatisBaseDao getMapper() {
        return qualityMapper;
    }

}
