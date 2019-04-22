package com.galaxis.wcs.yanfeng.database.oes.service;

import com.galaxis.wcs.yanfeng.database.oes.domain.Kt;
import com.galaxis.wcs.yanfeng.database.oes.domain.MainTask;
import com.galaxis.wcs.yanfeng.database.oes.domain.MainTaskExample;

import java.util.List;

public interface MainTaskService extends BaseService<MainTask, Integer, MainTaskExample> {

    /**
     * 根据箱号和状态查询主任务列表
     *
     * @param cartonNo 箱号
     * @param statuses 状态列表, 可为null
     * @param include  包含状态列表还是排除, statuses为null时不判断
     * @return 主任务列表
     */
    List<MainTask> queryByCartonNo(String cartonNo, List<Integer> statuses, Boolean include);

    /**
     * 修改一个正在入库/回库任务为kt补料任务
     *
     * @param kt 补料kt
     * @return 修改的主任务
     */
    MainTask updateFromGoLibToGoKt(Kt kt);

    /**
     * 创建主任务和是否废弃历史任务
     *
     * @param mainTask  主任务, 只插入选择的字段
     * @param giveUpOld 是否放弃同箱号的正在执行的老任务
     * @return insert操作修改的条数
     */
    int createAndGiveUp(MainTask mainTask, Boolean giveUpOld);

    /**
     * 把该箱号cartonNo的对应的历史任务中状态在statuses中的, 状态改为status
     * 涉及批量处理, 不建议将status改为开始, 完成等, 可能会丢失事件
     *
     * @param cartonNo     箱号
     * @param statuses     状态集
     * @param status       状态
     * @param dealChildren 是否处理子任务
     */
    void deal(String cartonNo, List<Integer> statuses, Integer status, Boolean dealChildren);
}
