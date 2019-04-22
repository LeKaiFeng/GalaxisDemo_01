package com.galaxis.wcs.yanfeng.database.oes.service.impl;

import com.galaxis.wcs.yanfeng.database.oes.domain.SkuInfo;
import com.galaxis.wcs.yanfeng.database.oes.domain.SkuInfoExample;
import com.galaxis.wcs.yanfeng.database.oes.mapper.SkuInfoMapper;
import com.galaxis.wcs.yanfeng.database.oes.mapper.MyBatisBaseDao;
import com.galaxis.wcs.yanfeng.database.oes.service.SkuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkuInfoServiceImpl extends AbstractServiceImpl<SkuInfo, Integer, SkuInfoExample> implements SkuInfoService {

    @Autowired
    private SkuInfoMapper skuInfoMapper;

    @Override
    protected MyBatisBaseDao getMapper() {
        return skuInfoMapper;
    }

}
