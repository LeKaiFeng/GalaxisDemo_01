package com.galaxis.wcs.yanfeng.database.oes.service.impl;

import com.galaxis.wcs.yanfeng.database.oes.domain.Line;
import com.galaxis.wcs.yanfeng.database.oes.domain.LineExample;
import com.galaxis.wcs.yanfeng.database.oes.mapper.LineMapper;
import com.galaxis.wcs.yanfeng.database.oes.mapper.MyBatisBaseDao;
import com.galaxis.wcs.yanfeng.database.oes.service.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LineServiceImpl extends AbstractServiceImpl<Line, Integer, LineExample> implements LineService {

    @Autowired
    private LineMapper lineMapper;

    @Override
    protected MyBatisBaseDao getMapper() {
        return lineMapper;
    }

}
