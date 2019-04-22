package com.galaxis.wcs.yanfeng.database.oes.mapper;

import com.galaxis.wcs.yanfeng.database.oes.domain.Quality;
import com.galaxis.wcs.yanfeng.database.oes.domain.QualityExample;
import org.springframework.stereotype.Repository;

/**
 * QualityMapper继承基类
 */
@Repository
public interface QualityMapper extends MyBatisBaseDao<Quality, Integer, QualityExample> {
}