package com.galaxis.wcs.yanfeng.database.oes.mapper;

import com.galaxis.wcs.yanfeng.database.oes.domain.Oroder;
import com.galaxis.wcs.yanfeng.database.oes.domain.OroderExample;
import org.springframework.stereotype.Repository;

/**
 * OroderMapper继承基类
 */
@Repository
public interface OroderMapper extends MyBatisBaseDao<Oroder, Integer, OroderExample> {
}