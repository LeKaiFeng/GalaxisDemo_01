package com.galaxis.wcs.yanfeng.database.oes.mapper;

import com.galaxis.wcs.yanfeng.database.oes.domain.SkuInfo;
import com.galaxis.wcs.yanfeng.database.oes.domain.SkuInfoExample;
import org.springframework.stereotype.Repository;

/**
 * SkuInfoMapper继承基类
 */
@Repository
public interface SkuInfoMapper extends MyBatisBaseDao<SkuInfo, Integer, SkuInfoExample> {
}