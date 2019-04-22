package com.galaxis.wcs.yanfeng.database.wcs.service.impl;

import com.galaxis.wcs.yanfeng.database.oes.mapper.MyBatisBaseDao;
import com.galaxis.wcs.yanfeng.database.oes.service.impl.AbstractServiceImpl;
import com.galaxis.wcs.yanfeng.database.wcs.domain.GaFlashstate;
import com.galaxis.wcs.yanfeng.database.wcs.domain.GaFlashstateExample;
import com.galaxis.wcs.yanfeng.database.wcs.mapper.GaFlashstateMapper;
import com.galaxis.wcs.yanfeng.database.wcs.service.GaFlashStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GaFlashStateServiceImpl extends AbstractServiceImpl<GaFlashstate, Integer, GaFlashstateExample> implements GaFlashStateService {
    @Autowired
    private GaFlashstateMapper gaFlashstateMapper;

    @Override
    protected MyBatisBaseDao<GaFlashstate, Integer, GaFlashstateExample> getMapper() {
        return gaFlashstateMapper;
    }
}
