package com.galaxis.wcs.yanfeng.database.oes.mapper;

import com.galaxis.wcs.yanfeng.database.oes.domain.Configure;
import com.galaxis.wcs.yanfeng.database.oes.domain.ConfigureExample;
import org.springframework.stereotype.Repository;

/**
 * ConfigureMapper继承基类
 */
@Repository
public interface ConfigureMapper extends MyBatisBaseDao<Configure, String, ConfigureExample> {
}