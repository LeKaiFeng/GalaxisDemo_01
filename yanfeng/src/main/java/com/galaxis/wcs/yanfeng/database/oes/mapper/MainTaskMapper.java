package com.galaxis.wcs.yanfeng.database.oes.mapper;

import com.galaxis.wcs.yanfeng.database.oes.domain.MainTask;
import com.galaxis.wcs.yanfeng.database.oes.domain.MainTaskExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MainTaskMapper继承基类
 */
@Repository
public interface MainTaskMapper extends MyBatisBaseDao<MainTask, Integer, MainTaskExample> {

    /**
     * 根据收货批次, 物料号以及主任务类型, 状态查询box
     *
     * @param recLot   收货批次, 为null则不作为条件
     * @param partNo   物料号
     * @param types    主任务类型
     * @param statuses 主任务状态
     * @return boxes
     */
    List<MainTask> selectMainTaskWithRecLot(@Param("recLot") String recLot, @Param("partNo") String partNo
            , @Param("types") List<Integer> types, @Param("statuses") List<Integer> statuses);
}