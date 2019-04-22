package com.galaxis.wcs.yanfeng.device.line;

import com.galaxis.wcs.yanfeng.device.line.handler.*;
import com.galaxis.wcs.yanfeng.exception.OesException;
import com.galaxis.wcs.yanfeng.exception.OesLineExcetion;
import com.galaxis.wcs.yanfeng.util.Constance;
import com.galaxis.wcs.yanfeng.util.template.LinePost;
import com.galaxis.wcs.yanfeng.util.template.LineRequest;
import com.galaxis.wcs.yanfeng.util.template.LineResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 分发器, 接收请求, 寻找处理器, 反馈结果
 */
@Component
public class LineDispatcher {
    private static final Logger log = LoggerFactory.getLogger(LineDispatcher.class);

    @Autowired
    private WeightHandler weightHandler;
    @Autowired
    private CheckHandler checkHandler;
    @Autowired
    private OutboundHandler outboundHandler;
    @Autowired
    private MiddleHandler middleHandler;
    @Autowired
    private FinishHandler finishHandler;
    @Autowired
    private BoxCleanHandler boxCleanHandler;
    @Autowired
    private StopHandler stopHandler;
    @Autowired
    private StopRecoverHandler stopRecoverHandler;
    @Autowired
    private HeartHandler heartHandler;

    public void doGet(LineRequest request, LineResponse response) {
        // 通过location获取不同的处理器
        LineHandler handler = handlerMapping(request);
        response.setSeq(request.getSeq());
        response.setCartonNo(request.getCartonNo());
        response.setType(request.getType());
        response.setVerify(request.getVerify());
        response.setLocation(Constance.LINE_NONE);

        if (Objects.isNull(handler)) {
            log.info("该位置没有对应的处理器!, location: {}, request: {}", request.getLocation(), request);
            return;
        }

        // 处理器处理请求, 并发聩结果
        try {
            handler.deal(request, response);

        } catch (OesLineExcetion e) {
            // 捕获输送线异常
            log.warn(e.getMessage());
            response.setLocation(e.getLocation());
            log.info("catch OesLineException, and set response's location {}", e.getLocation());

        } catch (OesException e) {
            // 捕获oes系统异常
            log.warn(e.getMessage(), e);
        }
    }

    /**
     * 处理器映射器, 寻找处理器适配器
     *
     * @param request 请求
     * @return 处理器
     */
    private LineHandler handlerMapping(LineRequest request) {
        LineHandler handler = null;

        // 判断状态
        Integer type = request.getType();
        if (LinePost.TYPE_HEART.equals(type)) {
            // 心跳
            return heartHandler;
        } else if (LinePost.TYPE_MIDDLE.equals(type)) {
            // 中间状态反馈
            // 通过seq查task, 拿到箱号, 更新箱子location, 拿到task的终点, 反馈给plc
            return middleHandler;
        } else if (LinePost.TYPE_OVER.equals(type)) {
            // 到达终点反馈
            // 通过seq查task, 拿到箱号, 更新箱子location, 更新task为完成, 判断主任务是否完成, 拿到task的终点, 反馈给plc
            return finishHandler;
        } else if (LinePost.TYPE_BOX_CLEAN.equals(type)) {
            // 箱子在输送线上丢失
            return boxCleanHandler;
        } else if (LinePost.TYPE_STOP.equals(type)) {
            // 停线
            return stopHandler;
        } else if (LinePost.TYPE_STOP_RECOVER.equals(type)) {
            // 停线恢复
            return stopRecoverHandler;
        }

        // type是如果是请求终点
        Integer location = request.getLocation();
        switch (location) {
            case Constance.LINE_WEIGHT_SCANNER:
                handler = weightHandler;
                break;
            case Constance.LINE_CHECK:
                handler = checkHandler;
                break;
            case Constance.LINE_OUTBOUND_1:
            case Constance.LINE_OUTBOUND_2:
                handler = outboundHandler;
                break;
            default:
                log.info("该location({})没有对应的handler", location);
                break;
        }
        return handler;
    }

}
