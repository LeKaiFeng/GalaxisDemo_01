package com.galaxis.wcs.yanfeng.database.oes.mapper;

import com.galaxis.wcs.yanfeng.database.oes.domain.Line;
import com.galaxis.wcs.yanfeng.database.oes.domain.LineExample;
import org.springframework.stereotype.Repository;

/**
 * LineMapper继承基类
 */
@Repository
public interface LineMapper extends MyBatisBaseDao<Line, Integer, LineExample> {
}