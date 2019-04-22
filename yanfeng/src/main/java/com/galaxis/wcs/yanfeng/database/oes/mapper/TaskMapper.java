package com.galaxis.wcs.yanfeng.database.oes.mapper;

import com.galaxis.wcs.yanfeng.database.oes.domain.Task;
import com.galaxis.wcs.yanfeng.database.oes.domain.TaskExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TaskMapper继承基类
 */
@Repository
public interface TaskMapper extends MyBatisBaseDao<Task, Integer, TaskExample> {

    List<Task> selectLevelCountByPartNo(@Param("partNo") String partNo, @Param("types") List<Integer> types, @Param("statuses") List<Integer> statuses);
}