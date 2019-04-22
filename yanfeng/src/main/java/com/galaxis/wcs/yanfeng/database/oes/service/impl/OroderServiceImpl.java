package com.galaxis.wcs.yanfeng.database.oes.service.impl;

import com.galaxis.wcs.yanfeng.database.oes.domain.Oroder;
import com.galaxis.wcs.yanfeng.database.oes.domain.OroderExample;
import com.galaxis.wcs.yanfeng.database.oes.mapper.OroderMapper;
import com.galaxis.wcs.yanfeng.database.oes.mapper.MyBatisBaseDao;
import com.galaxis.wcs.yanfeng.database.oes.service.OroderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OroderServiceImpl extends AbstractServiceImpl<Oroder, Integer, OroderExample> implements OroderService {

    @Autowired
    private OroderMapper oroderMapper;

    @Override
    protected MyBatisBaseDao getMapper() {
        return oroderMapper;
    }

    @Override
    public Oroder getByCartonNo(String cartonNo) {
        OroderExample oroderExample = new OroderExample();
        oroderExample.createCriteria()
                .andCartonNoEqualTo(cartonNo);
        oroderExample.setLimit(1);
        List<Oroder> oroders = selectByExample(oroderExample);
        if (oroders.size() > 0) {
            return oroders.get(0);
        }
        return null;
    }
}
