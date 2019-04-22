package com.galaxis.wcs.yanfeng.database.oes.service.impl;

import com.galaxis.wcs.yanfeng.database.oes.domain.Warehousing;
import com.galaxis.wcs.yanfeng.database.oes.domain.WarehousingExample;
import com.galaxis.wcs.yanfeng.database.oes.mapper.MyBatisBaseDao;
import com.galaxis.wcs.yanfeng.database.oes.mapper.WarehousingMapper;
import com.galaxis.wcs.yanfeng.database.oes.service.WarehousingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarehousingServiceImpl extends AbstractServiceImpl<Warehousing, Integer, WarehousingExample> implements WarehousingService {

    @Autowired
    private WarehousingMapper warehousingMapper;

    @Override
    protected MyBatisBaseDao getMapper() {
        return warehousingMapper;
    }

}
