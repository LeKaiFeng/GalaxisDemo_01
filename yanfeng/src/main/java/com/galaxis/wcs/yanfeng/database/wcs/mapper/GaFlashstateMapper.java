package com.galaxis.wcs.yanfeng.database.wcs.mapper;

import com.galaxis.wcs.yanfeng.database.oes.mapper.MyBatisBaseDao;
import com.galaxis.wcs.yanfeng.database.wcs.domain.GaFlashstate;
import com.galaxis.wcs.yanfeng.database.wcs.domain.GaFlashstateExample;
import org.springframework.stereotype.Repository;

/**
 * GaFlashstateMapper继承基类
 */
@Repository
public interface GaFlashstateMapper extends MyBatisBaseDao<GaFlashstate, Integer, GaFlashstateExample> {
}