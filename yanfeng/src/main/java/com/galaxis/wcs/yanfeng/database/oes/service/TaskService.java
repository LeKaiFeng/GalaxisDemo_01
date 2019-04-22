package com.galaxis.wcs.yanfeng.database.oes.service;

import com.galaxis.wcs.yanfeng.database.oes.domain.Task;
import com.galaxis.wcs.yanfeng.database.oes.domain.TaskExample;

import java.util.List;
import java.util.Map;

public interface TaskService extends BaseService<Task, Integer, TaskExample> {
    /**
     * 通过PLC发来的中间/完成状态的seq查询task
     *
     * @param seq seq发来的中间/完成状态的seq
     * @return 查询到的task
     */
    Task getBySeq(Long seq);

    /**
     * 根据seq更新task已有的数据
     *
     * @param task task
     * @return 更新的条数
     */
    int updateStatusBySeq(Task task);

    /**
     * 错误关闭任务
     *
     * @param task       错误关闭任务
     * @param cancelMain 是否同时关闭主任务
     */
    void errorCloseTask(Task task, Boolean cancelMain);

    /**
     * 创建子任务并完成之前步骤的子任务
     *
     * @param task      子任务
     * @param finishOld 是否完成之前步骤的任务
     * @return 插入的条数
     */
    int createAndFinish(Task task, Boolean finishOld);

    /**
     * 任务开始
     *
     * @param task
     */
    void start(Task task);

    /**
     * 任务结束
     *
     * @param task
     */
    void finish(Task task);

    /**
     * 立库任务小车取货
     * @param task 对应的oes子任务
     */
    void pickup(Task task);

    /**
     * 查找某种partNo, 某些types类型的 某些status状态的子任务
     *
     * @param partNo 物料号
     * @param types 子任务类型
     * @param status 子任务状态
     * @return 该种物料在每层有多少个状态位status, types
     */
    Map<Integer, Integer> mapLevelCountByPartNo(String partNo, List<Integer> types, List<Integer> status);
}
