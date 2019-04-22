package com.galaxis.wcs.yanfeng.device.line.service.impl;

import com.galaxis.wcs.yanfeng.database.oes.domain.Box;
import com.galaxis.wcs.yanfeng.database.oes.domain.MainTask;
import com.galaxis.wcs.yanfeng.database.oes.service.BoxService;
import com.galaxis.wcs.yanfeng.database.oes.service.LocationService;
import com.galaxis.wcs.yanfeng.database.oes.service.MainTaskService;
import com.galaxis.wcs.yanfeng.database.wms.domain.ItStock;
import com.galaxis.wcs.yanfeng.database.wms.domain.ItStockExample;
import com.galaxis.wcs.yanfeng.database.wms.service.ItStockService;
import com.galaxis.wcs.yanfeng.device.line.service.BackLibService;
import com.galaxis.wcs.yanfeng.util.Constance;
import com.galaxis.wcs.yanfeng.util.LocationUtil;
import com.galaxis.wcs.yanfeng.util.template.LinePost;
import com.galaxis.wcs.yanfeng.util.template.LineRequest;
import com.galaxis.wcs.yanfeng.util.template.LineResponse;
import com.galaxis.wcs.yanfeng.work.manager.TaskManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class BackLibServiceImpl implements BackLibService {
    private static final Logger log = LoggerFactory.getLogger(BackLibServiceImpl.class);

    @Autowired
    private TaskManager taskManager;
    @Autowired
    private BoxService boxService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private ItStockService itStockService;
    @Autowired
    private MainTaskService mainTaskService;

    @Override
    public void backToLib(LineRequest request, LineResponse response) {
        String cartonNo = request.getCartonNo();

        // noread停线报警
        if (Constance.CODE_NO_READ.equalsIgnoreCase(cartonNo)) {
            log.warn("回库的box条码为noread, 反馈停线");
            response.setWarn(1);
            response.setErrorCode(LinePost.ERROR_CODE_NO_READ_ON_CHECK);
            return;
        }

        // 有码可入库
        Box box = boxService.getBoxByCartonNo(cartonNo);
        String description;
        if (Objects.nonNull(box)) {
            // 之前维护过的box, 就做查验回库
            description = "查验回库";

            boxService.clearOccupy(box);

        } else {
            // 非排程入库前, 检查输送线有没有扫到箱子的长度信息
            BigDecimal length = request.getLength();
            if (BigDecimal.ZERO.equals(length)) {
                log.warn("非排程入库的box长度未知, 反馈停线, cartonNo: {}", cartonNo);
                response.setWarn(1);
                response.setErrorCode(LinePost.ERROR_CODE_UNKNOWN_LENGTH);
                return;
            }

            // 之前没有维护过的box, 说明是非排程入库
            description = "非排程入库";

            // 通过箱号查询视图, 存在于视图才可以入库
            ItStockExample itStockExample = new ItStockExample();
            itStockExample.createCriteria().andCartonNoEqualTo(cartonNo);
            List<ItStock> itStocks = itStockService.selectByExample(itStockExample);
            ItStock itStock;
            if (itStocks.size() > 0) {
                // 视图可以查到, 允许入库
                itStock = itStocks.get(0);
                // 新创建一个箱子, 并跟踪
                box = new Box();
                BeanUtils.copyProperties(itStock, box, "id");
                box.setNc(0);
                box.setPlcSeq(request.getSeq());
                box.setWeight(request.getWeight());
                box.setLength(request.getLength());
                box.setLevel(Constance.LINE_LEVEL);
                box.setLocation(request.getLocation());
                boxService.insertSelective(box);
                log.info("创建box跟踪, box id: {}, cartonNo: {}, level: {}, location: {}"
                        , box.getId(), box.getCartonNo(), box.getLevel(), box.getLocation());

            }
        }

        if (Objects.isNull(box)) {
            // 对于不存在与视图的非排程任务, 和不在我们跟踪范围内的箱子想要入库, 定为停线
            log.warn("{}异常, 箱号: {}, 原因: {}", description, cartonNo, "想要入库的箱子, 不在oes跟踪范围内且不存在于视图");
            response.setWarn(1);
            response.setErrorCode(LinePost.ERROR_CODE_NOT_ALLOWED_INBOUND);
            return;
        }

        // 入库
        lineInbound(box, description, request, response);
    }

    @Override
    public void lineInbound(Box box, String description, LineRequest request, LineResponse response) {
        String cartonNo = request.getCartonNo();
        // 计算入库口
        List<Integer> occupy = request.getOccupy();
        int inbound = LocationUtil.getInbound(occupy);
        response.setLocation(inbound);
        log.info("box: {}, 送去入库口{}, 任务类型: {}", cartonNo, response.getLocation() % 10, description);

        // 创建主任务跟踪
        MainTask mainTask = new MainTask(null, Constance.MAIN_TASK_TYPE_BACK_LIB, description
                , box.getOrderNo(), box.getPartNo(), box.getCartonNo()
                , Constance.LINE_LEVEL, request.getLocation(), null, null, null, null
                , Constance.TASK_STATUS_RUNNING_LINE, LocalDateTime.now(), null, null);

        mainTaskService.createAndGiveUp(mainTask, Boolean.TRUE);
        log.info("生成{}主任务, maintask id: {}", mainTask.getDescription(), mainTask.getId());
        taskManager.doMainTask(mainTask, request.getSeq().longValue(), response.getLocation());

        // 更新box状态
        Box bx = new Box();
        bx.setId(box.getId());
        bx.setLength(request.getLength());
        bx.setLevel(Constance.LINE_LEVEL);
        bx.setLocation(request.getLocation());
        bx.setPlcSeq(request.getSeq());
        bx.setPlcTaskId(mainTask.getId());
        bx.setBoxPosition(LocationUtil.getPosition(request.getLocation()));
        boxService.updateByPrimaryKeySelective(bx);
        log.info("更新box, cartonNo: {}, length: {}, location: {}, plcSeq: {}, mainTaskId: {}"
                , box.getCartonNo(), bx.getLength(), bx.getLocation(), bx.getPlcSeq(), bx.getPlcTaskId());
    }
}
