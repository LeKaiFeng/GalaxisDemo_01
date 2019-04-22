package com.galaxis.wcs.yanfeng.device.line.handler;

import com.galaxis.wcs.yanfeng.aop.PlcSeqAnnotation;
import com.galaxis.wcs.yanfeng.database.oes.domain.Box;
import com.galaxis.wcs.yanfeng.database.oes.domain.MainTask;
import com.galaxis.wcs.yanfeng.database.oes.service.BoxService;
import com.galaxis.wcs.yanfeng.database.oes.service.MainTaskService;
import com.galaxis.wcs.yanfeng.database.oes.service.TaskService;
import com.galaxis.wcs.yanfeng.device.line.LineHandler;
import com.galaxis.wcs.yanfeng.device.line.service.BackLibService;
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

import java.util.List;
import java.util.Objects;

/**
 * 出库口
 * 主要执行将箱子送去入库口或抽检点
 */
@Component
public class OutboundHandler implements LineHandler {
    private static final Logger log = LoggerFactory.getLogger(OutboundHandler.class);

    @Autowired
    private BackLibService backLibService;
    @Autowired
    private TaskManager taskManager;
    @Autowired
    private TaskService taskService;
    @Autowired
    private MainTaskService mainTaskService;
    @Autowired
    private BoxService boxService;

    @Transactional(rollbackFor = Exception.class, noRollbackFor = OesLineExcetion.class)
    @PlcSeqAnnotation
    @Override
    public void deal(LineRequest request, LineResponse response) {
        Integer seq = request.getSeq();
        String cartonNo = request.getCartonNo();
        log.info("从出库口收到请求, seq: {}, cartonNo: {}, request: {}", seq, cartonNo, request);

        // 根据box里的主任务id查, type=12: 质检/盘点或21: KT补料, status in (1,2,3), create_time最近的
        Box box = boxService.getBoxByCartonNo(cartonNo);
        if (Objects.isNull(box)) {
            log.info("根据箱号无法查到跟踪过的料箱, 走回库操作, cartonNo: {}", cartonNo);
            backLibService.backToLib(request, response);
            return;
        } else if (Objects.isNull(box.getPlcTaskId())) {
            log.info("该料箱没有绑定任务, 走回库操作, cartonNo: {}", cartonNo);
            backLibService.backToLib(request, response);
            return;
        }

        // 通过box绑定的主任务, 查询mainTask
        Integer plcTaskId = box.getPlcTaskId();
        MainTask mainTask = mainTaskService.selectByPrimaryKey(plcTaskId);
        if (Objects.isNull(mainTask)) {
            log.info("出库口发来的箱号没有对应正在执行的任务, 走回库操作, 箱号: {}", cartonNo);
            backLibService.backToLib(request, response);
            return;
        }

        Integer type = mainTask.getType();

        int bound;
        switch (type) {
            case Constance.MAIN_TASK_TYPE_QUALITY:
                // 质检/盘点
                response.setLocation(Constance.LINE_CHECK);
                log.info("box: {}, mainTask type: {}, 送去质检点", cartonNo, type);
                break;
            case Constance.MAIN_TASK_TYPE_KT:
            case Constance.MAIN_TASK_TYPE_MOVE:
                // KT补货或移库
                List<Integer> occupy = request.getOccupy();
                bound = LocationUtil.getInbound(occupy);
                response.setLocation(bound);
                log.info("box: {}, mainTask type: {}, 送去入库口", cartonNo, type);
                break;
            default:
                log.warn("不正确的主任务类型, 走回库操作, type: {}", type);
                backLibService.backToLib(request, response);
                return;
        }
        // 维护任务
        taskManager.doNextLineTask(mainTask, request, response);

        Box bx = new Box();
        bx.setId(box.getId());
        bx.setPlcSeq(seq);
        bx.setLevel(Constance.LINE_LEVEL);
        bx.setLocation(request.getLocation());
        bx.setBoxPosition(LocationUtil.getPosition(request.getLocation()));
        boxService.updateByPrimaryKeySelective(bx);
        log.info("box到达出库口, 更新box cartonNo: {}的 seq: {}, location: {}", cartonNo, seq, request.getLocation());
    }
}
