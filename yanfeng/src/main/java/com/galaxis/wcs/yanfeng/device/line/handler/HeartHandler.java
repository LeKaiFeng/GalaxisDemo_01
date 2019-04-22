package com.galaxis.wcs.yanfeng.device.line.handler;

import com.galaxis.wcs.yanfeng.device.line.LineHandler;
import com.galaxis.wcs.yanfeng.util.template.LineRequest;
import com.galaxis.wcs.yanfeng.util.template.LineResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HeartHandler implements LineHandler {
    private static final Logger log = LoggerFactory.getLogger(HeartHandler.class);

    @Override
    public void deal(LineRequest request, LineResponse response) {
        log.debug("收到心跳, verify: {}, request: {}", request.getVerify(), request);
    }
}
