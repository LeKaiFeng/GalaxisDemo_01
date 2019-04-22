package com.galaxis.wcs.yanfeng.database.oes.mapper;

import com.galaxis.wcs.yanfeng.database.oes.domain.Rezult;
import com.galaxis.wcs.yanfeng.database.oes.domain.RezultExample;
import org.springframework.stereotype.Repository;

/**
 * RezultMapper继承基类
 */
@Repository
public interface RezultMapper extends MyBatisBaseDao<Rezult, Integer, RezultExample> {
}