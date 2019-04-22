package com.galaxis.wcs.yanfeng.database.oes.mapper;

import com.galaxis.wcs.yanfeng.database.oes.domain.Warehousing;
import com.galaxis.wcs.yanfeng.database.oes.domain.WarehousingExample;
import org.springframework.stereotype.Repository;

/**
 * WarehousingMapper继承基类
 */
@Repository
public interface WarehousingMapper extends MyBatisBaseDao<Warehousing, Integer, WarehousingExample> {
}