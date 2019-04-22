package com.galaxis.wcs.yanfeng.database.oes.mapper;

import com.galaxis.wcs.yanfeng.database.oes.domain.Kt;
import com.galaxis.wcs.yanfeng.database.oes.domain.KtExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * KtMapper继承基类
 */
@Repository
public interface KtMapper extends MyBatisBaseDao<Kt, Integer, KtExample> {
    int updateNumber(@Param("id") Integer id, @Param("inc") Integer inc, @Param("columns") List<String> columns);
}