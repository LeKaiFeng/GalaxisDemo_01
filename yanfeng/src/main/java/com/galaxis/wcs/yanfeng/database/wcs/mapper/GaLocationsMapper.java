package com.galaxis.wcs.yanfeng.database.wcs.mapper;

import com.galaxis.wcs.yanfeng.database.oes.mapper.MyBatisBaseDao;
import com.galaxis.wcs.yanfeng.database.wcs.domain.GaLocation;
import com.galaxis.wcs.yanfeng.database.wcs.domain.GaLocationExample;
import com.galaxis.wcs.yanfeng.database.wcs.domain.GaLocationKey;
import org.springframework.stereotype.Repository;

/**
 * GaLocationsMapper继承基类
 */
@Repository
public interface GaLocationsMapper extends MyBatisBaseDao<GaLocation, GaLocationKey, GaLocationExample> {
}