package com.galaxis.wcs.yanfeng.device.line.handler;

import com.galaxis.wcs.yanfeng.database.oes.domain.Box;
import com.galaxis.wcs.yanfeng.database.oes.domain.Task;
import com.galaxis.wcs.yanfeng.database.oes.service.BoxService;
import com.galaxis.wcs.yanfeng.database.oes.service.TaskService;
import com.galaxis.wcs.yanfeng.device.line.LineHandler;
import com.galaxis.wcs.yanfeng.exception.OesLineExcetion;
import com.galaxis.wcs.yanfeng.util.Constance;
import com.galaxis.wcs.yanfeng.util.LocationUtil;
import com.galaxis.wcs.yanfeng.util.template.LinePost;
import com.galaxis.wcs.yanfeng.util.template.LineRequest;
import com.galaxis.wcs.yanfeng.util.template.LineResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 中间状态处理器
 * 通过seq查task, 拿到箱号, 更新箱子location, 拿到task的终点, 反馈给plc
 */
@Component
public class MiddleHandler implements LineHandler {
    private static final Logger log = LoggerFactory.getLogger(MiddleHandler.class);

    @Autowired
    private TaskService taskService;
    @Autowired
    private BoxService boxService;

    @Transactional(rollbackFor = Exception.class, noRollbackFor = OesLineExcetion.class)
    @Override
    public void deal(LineRequest request, LineResponse response) {
        // 中间状态的请求, 必须有seq, 但是可以没有箱号
        Integer seq = request.getSeq();
        log.info("接收中间状态反馈, seq: {}, request: {}", seq, request);

        Task task = taskService.getBySeq(seq.longValue());
        if (Objects.isNull(task)) {
            // plc发来一个中间/完成状态, 但是根据seq没查到对应的task
            log.warn("PLC发来中间状态反馈的seq没有对应的任务...");
            response.setWarn(1);
            response.setErrorCode(LinePost.ERROR_CODE_NO_CORRESPONDING_TASK);
            return;
        }

        // 把seq对应任务终点设置到response中
        response.setLocation(task.geteLocation());
        if (!Constance.TASK_STATUS_RUNNING_LIST.contains(task.getStatus())) {
            // plc发来一个中间/完成状态, 但是该任务不是正在运行状态, 忽略即可
            log.info("plc发来一个中间状态, 但是该task不是正在运行状态, seq: {}, status: {}", task.getSeq(), task.getStatus());
            return;
        }

        // 更新箱子location
        String cartonNo = task.getCartonNo();
        Box bx = new Box();
        bx.setCartonNo(cartonNo);
        bx.setLocation(request.getLocation());
        bx.setBoxPosition(LocationUtil.getPosition(request.getLocation()));
        boxService.updateByCartonNo(bx);
        log.info("更新box: {} 的 location: {}", cartonNo, request.getLocation());

    }
}
