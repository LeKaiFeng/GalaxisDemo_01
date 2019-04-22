package com.galaxis.wcs.yanfeng.device.line.handler;

import com.galaxis.wcs.yanfeng.database.oes.domain.Task;
import com.galaxis.wcs.yanfeng.database.oes.service.TaskService;
import com.galaxis.wcs.yanfeng.device.line.LineHandler;
import com.galaxis.wcs.yanfeng.util.template.LineRequest;
import com.galaxis.wcs.yanfeng.util.template.LineResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 箱子在输送线上丢失
 */
@Component
public class BoxCleanHandler implements LineHandler {
    private static final Logger log = LoggerFactory.getLogger(BoxCleanHandler.class);

    @Autowired
    private TaskService taskService;

    @Override
    public void deal(LineRequest request, LineResponse response) {
        Integer seq = request.getSeq();
        String cartonNo = request.getCartonNo();
        log.info("收到箱体信息清除请求, seq:{}, cartonNo: {}, request: {}", seq, cartonNo, request);

        Task task = taskService.getBySeq(seq.longValue());
        if (Objects.isNull(task)) {
            log.info("没有该task, seq: {}, cartonNo: {}", seq, cartonNo);
            return;
        }
        // 异常关闭对应的任务
        taskService.errorCloseTask(task, Boolean.TRUE);

    }
}
