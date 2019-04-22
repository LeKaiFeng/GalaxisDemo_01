package com.galaxis.wcs.yanfeng.device.line.handler;

import com.galaxis.wcs.yanfeng.database.oes.domain.Box;
import com.galaxis.wcs.yanfeng.database.oes.domain.MainTask;
import com.galaxis.wcs.yanfeng.database.oes.domain.Task;
import com.galaxis.wcs.yanfeng.database.oes.service.BoxService;
import com.galaxis.wcs.yanfeng.database.oes.service.MainTaskService;
import com.galaxis.wcs.yanfeng.database.oes.service.TaskService;
import com.galaxis.wcs.yanfeng.device.line.LineHandler;
import com.galaxis.wcs.yanfeng.exception.OesLineExcetion;
import com.galaxis.wcs.yanfeng.util.Constance;
import com.galaxis.wcs.yanfeng.util.LocationUtil;
import com.galaxis.wcs.yanfeng.util.template.LineRequest;
import com.galaxis.wcs.yanfeng.util.template.LineResponse;
import com.galaxis.wcs.yanfeng.work.manager.TaskManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 完成状态处理器
 * 处理输送线PLC发来的完成新号, 更新box的location, 更新task状态, 判断是否需要更新maintask为完成, 反馈plc终点为task的终点
 */
@Component
public class FinishHandler implements LineHandler {
    private static final Logger log = LoggerFactory.getLogger(FinishHandler.class);

    @Autowired
    private TaskService taskService;
    @Autowired
    private MainTaskService mainTaskService;
    @Autowired
    private BoxService boxService;

    @Transactional(rollbackFor = Exception.class, noRollbackFor = OesLineExcetion.class)
    @Override
    public void deal(LineRequest request, LineResponse response) {
        Integer seq = request.getSeq();
        log.info("接收结束状态反馈, seq: {}, request: {}", seq, request);
        // 通过seq查task
        Task task = taskService.getBySeq(seq.longValue());
        if (Objects.isNull(task)) {
            // plc发来一个中间/完成状态, 但是根据seq没查到对应的task, 反馈停线
            log.info("plc发来一个中间/完成状态, 但是根据seq没查到对应的task, seq: {}", seq);
            // response.setWarn(1);
            // response.setErrorCode(LinePost.ERROR_CODE_NO_CORRESPONDING_TASK);
            response.setLocation(request.getLocation());
            return;
        }

        response.setLocation(task.geteLocation());
        if (!Constance.TASK_STATUS_RUNNING_LIST.contains(task.getStatus())) {
            // plc发来一个中间/完成状态, 但是该任务不是正在运行状态, 忽略即可
            log.info("plc发来一个完成状态, 但是该task不是正在运行状态, seq: {}, status: {}", task.getSeq(), task.getStatus());
            response.setLocation(request.getLocation());
            return;
        }

        // 更新箱子location, 同时清除box的plcSeq
        String cartonNo = request.getCartonNo();
        Box bx = new Box();
        bx.setCartonNo(cartonNo);
        bx.setLocation(request.getLocation());
        bx.setBoxPosition(LocationUtil.getPosition(request.getLocation()));

        LocalDateTime now = LocalDateTime.now();
        // 更新子任务完成
        Task tsk = new Task();
        tsk.setId(task.getId());
        tsk.setEndTime(now);
        tsk.setStatus(Constance.TASK_STATUS_FINISH);
        taskService.updateByPrimaryKeySelective(tsk);
        log.info("更改task状态为完成, task id: {}, seq: {}, cartNo: {}, status: {}", tsk.getId(), task.getSeq(), task.getCartonNo(), tsk.getStatus());

        // 判断子任务的终点是否与主任务一致, 如果是一致的, 就更新主任务为完成
        MainTask mainTask = mainTaskService.selectByPrimaryKey(task.getPid());
        if (TaskManager.checkFinish(mainTask, task)) {
            // 主任务完成
            MainTask mTask = new MainTask();
            mTask.setId(mainTask.getId());
            mTask.setEndTime(now);
            mTask.setStatus(Constance.TASK_STATUS_FINISH);
            mainTaskService.updateByPrimaryKeySelective(mTask);
            log.info("更新main task状态为完成, maintask id: {}, cartNo: {}, status: {}", mTask.getId(), mainTask.getCartonNo(), mTask.getStatus());
            // 主任务完成时, 清除box的mainTaskId
            boxService.cleanTask(cartonNo);
        }

        boxService.updateByCartonNo(bx);
        log.info("更新box: {} 的 location: {}", cartonNo, request.getLocation());

    }
}
