package com.galaxis.wcs.yanfeng.device.line.handler;

import com.galaxis.wcs.yanfeng.device.line.LineHandler;
import com.galaxis.wcs.yanfeng.util.template.LineRequest;
import com.galaxis.wcs.yanfeng.util.template.LineResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StopRecoverHandler implements LineHandler {
    private static final Logger log = LoggerFactory.getLogger(StopRecoverHandler.class);

    @Override
    public void deal(LineRequest request, LineResponse response) {
        Integer type = request.getType();
        Integer location = request.getLocation();
        Integer errorCode = request.getErrorCode();
        log.info("接收到停线恢复信息, 类型: {}, 停线恢复段编号: {}, 异常码: {}", type, location, errorCode);

    }
}
