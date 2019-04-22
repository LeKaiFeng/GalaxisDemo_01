package com.galaxis.wcs.yanfeng.database.oes.mapper;

import com.galaxis.wcs.yanfeng.database.oes.domain.Err;
import com.galaxis.wcs.yanfeng.database.oes.domain.ErrExample;
import org.springframework.stereotype.Repository;

/**
 * ErrMapper继承基类
 */
@Repository
public interface ErrMapper extends MyBatisBaseDao<Err, Integer, ErrExample> {
}