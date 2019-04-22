package com.galaxis.wcs.yanfeng.event;

import com.alibaba.fastjson.JSON;
import com.galaxis.wcs.yanfeng.database.oes.service.MessageService;
import com.galaxis.wcs.yanfeng.util.Constance;
import com.galaxis.wcs.yanfeng.util.template.TaskEvent;
import com.galaxis.wcs.yanfeng.util.template.WcsSend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TaskStartListen implements ApplicationListener<TaskFinishEvent> {
    private static final Logger log = LoggerFactory.getLogger(TaskStartListen.class);

    @Autowired
    private MessageService messageService;

    @Override
    public void onApplicationEvent(TaskFinishEvent event) {
        log.debug("收到开始事件: {}", event);

        TaskEvent taskEvent = event.getTaskEvent();
        if (!Objects.equals(TaskEvent.EVENT_START, taskEvent.getEvent())) {
            return;
        }

        log.info("处理task的开始事件, event: {}", taskEvent);
        if (TaskEvent.WHERE_WCS_TASK.equals(taskEvent.getWhere())) {
            if (TaskEvent.TYPE_INSERT.equals(taskEvent.getType())) {
                // 添加操作
                // 向WCS的任务列表推送任务
                Object data = taskEvent.getData();
                WcsSend wcsSend = JSON.parseObject(data.toString(), WcsSend.class);
                messageService.rightPush(Constance.KEY_WCS_TASK, wcsSend);
                log.info("向wcs任务队列推送任务, data: {}", wcsSend);
            }
        }
    }

}
