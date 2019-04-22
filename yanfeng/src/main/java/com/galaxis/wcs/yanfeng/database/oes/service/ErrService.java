package com.galaxis.wcs.yanfeng.database.oes.service;

import com.galaxis.wcs.yanfeng.database.oes.domain.Err;
import com.galaxis.wcs.yanfeng.database.oes.domain.ErrExample;

public interface ErrService extends BaseService<Err, Integer, ErrExample> {
    int addErr(String cartonNo, String reason);
}
