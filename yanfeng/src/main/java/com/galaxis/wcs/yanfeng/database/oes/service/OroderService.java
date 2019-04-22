package com.galaxis.wcs.yanfeng.database.oes.service;

import com.galaxis.wcs.yanfeng.database.oes.domain.Oroder;
import com.galaxis.wcs.yanfeng.database.oes.domain.OroderExample;

public interface OroderService extends BaseService<Oroder, Integer, OroderExample> {
    Oroder getByCartonNo(String cartonNo);
}
