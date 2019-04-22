package com.galaxis.wcs.yanfeng.database.wms.service.impl;

import com.galaxis.wcs.yanfeng.database.oes.mapper.MyBatisBaseDao;
import com.galaxis.wcs.yanfeng.database.oes.service.impl.AbstractServiceImpl;
import com.galaxis.wcs.yanfeng.database.wms.domain.ItStock;
import com.galaxis.wcs.yanfeng.database.wms.domain.ItStockExample;
import com.galaxis.wcs.yanfeng.database.wms.mapper.ItStockMapper;
import com.galaxis.wcs.yanfeng.database.wms.service.ItStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItStockServiceImpl extends AbstractServiceImpl<ItStock, Integer, ItStockExample> implements ItStockService {

    @Autowired
    private ItStockMapper itStockMapper;

    @Override
    protected MyBatisBaseDao getMapper() {
        return itStockMapper;
    }

}
