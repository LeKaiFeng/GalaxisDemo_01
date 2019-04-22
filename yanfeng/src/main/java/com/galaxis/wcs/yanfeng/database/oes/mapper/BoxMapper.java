package com.galaxis.wcs.yanfeng.database.oes.mapper;

import com.galaxis.wcs.yanfeng.database.oes.domain.Box;
import com.galaxis.wcs.yanfeng.database.oes.domain.BoxExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * BoxMapper继承基类
 */
@Repository
public interface BoxMapper extends MyBatisBaseDao<Box, Integer, BoxExample> {

    /**
     * 根据id更新map中的数据
     *
     * @param id  id
     * @param map 参数组
     * @return 更新的条数
     */
    int updateByIdOnMap(@Param("id") Integer id, @Param("map") Map<String, Object> map);

    int updateByCartonNoOnMap(@Param("cartonNo") String cartonNo, @Param("map") Map<String, Object> map);

    /**
     * 根据物料号查询各层该种物料的数量
     *
     * @param partNo 物料号
     * @return 集合, level -> location(此处)暂时用location表示数量
     */
    List<Box> selectLevelCountByPartNo(@Param("partNo") String partNo);

    /**
     * 根据收货批次, 物料号以及主任务类型, 状态查询box
     *
     * @param recLot   收货批次, 为null则不作为条件
     * @param partNo   物料号
     * @param types    主任务类型
     * @param statuses 主任务状态
     * @return boxes
     */
    List<Box> selectBoxWithMainTask(@Param("recLot") String recLot, @Param("partNo") String partNo
            , @Param("types") List<Integer> types, @Param("statuses") List<Integer> statuses);

    /**
     * 结合location表, 查询满足条件的料箱
     *
     * @param locationStatuses location的状态表
     * @param partNo           物料号
     * @param recLot           收货批次
     * @param level            优先的层
     * @return 满足条件的料箱
     */
    List<Box> selectBoxWithLocation(@Param("locationStatuses") List<Integer> locationStatuses
            , @Param("partNo") String partNo, @Param("recLot") String recLot, @Param("level") Integer level);

    List<Box> selectFreeBox(@Param("partNo") String partNo, @Param("recLot") String recLot, @Param("level") Integer level, @Param("locationEndWith") List<Integer> locationEndWith);
}