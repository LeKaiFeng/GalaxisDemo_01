package com.galaxis.wcs.yanfeng.database.oes.service.impl;

import com.galaxis.wcs.yanfeng.database.oes.domain.Err;
import com.galaxis.wcs.yanfeng.database.oes.domain.ErrExample;
import com.galaxis.wcs.yanfeng.database.oes.mapper.ErrMapper;
import com.galaxis.wcs.yanfeng.database.oes.mapper.MyBatisBaseDao;
import com.galaxis.wcs.yanfeng.database.oes.service.ErrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ErrServiceImpl extends AbstractServiceImpl<Err, Integer, ErrExample> implements ErrService {
    private static final Logger log = LoggerFactory.getLogger(ErrServiceImpl.class);

    @Autowired
    private ErrMapper errMapper;

    @Override
    protected MyBatisBaseDao getMapper() {
        return errMapper;
    }

    @Override
    public int addErr(String cartonNo, String reason) {
        Err err = new Err();
        err.setCartonNo(cartonNo);
        err.setCreateTime(LocalDateTime.now());
        err.setReason(reason);
        err.setStatus(0);
        err.setRemove(0);
        int i = this.insertSelective(err);

        log.info("{} 被送去异常口, {}", cartonNo, reason);
        return i;
    }
}
