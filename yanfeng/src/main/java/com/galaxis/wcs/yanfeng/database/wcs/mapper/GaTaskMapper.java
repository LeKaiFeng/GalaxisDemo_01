package com.galaxis.wcs.yanfeng.database.wcs.mapper;

import com.galaxis.wcs.yanfeng.database.oes.mapper.MyBatisBaseDao;
import com.galaxis.wcs.yanfeng.database.wcs.domain.GaTask;
import com.galaxis.wcs.yanfeng.database.wcs.domain.GaTaskExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * GaTaskMapper继承基类
 */
@Repository
public interface GaTaskMapper extends MyBatisBaseDao<GaTask, Integer, GaTaskExample> {

    /**
     * 更新gis的任务优先级, 只能更新还未运行的
     *
     * @param wmsid     oes的seq
     * @param boxNumber 箱号
     * @param priority  增长的优先级
     * @return 更新的条数
     */
    int incrementPriority(@Param("wmsid") String wmsid, @Param("boxNumber") String boxNumber, @Param("priority") Integer priority);
}