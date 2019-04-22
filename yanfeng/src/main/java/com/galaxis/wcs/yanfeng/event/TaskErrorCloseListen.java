package com.galaxis.wcs.yanfeng.event;

import com.alibaba.fastjson.JSONObject;
import com.galaxis.wcs.yanfeng.database.oes.domain.Task;
import com.galaxis.wcs.yanfeng.database.oes.service.TaskService;
import com.galaxis.wcs.yanfeng.util.template.TaskEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TaskErrorCloseListen implements ApplicationListener<TaskErrorCloseEvent> {
    private static final Logger log = LoggerFactory.getLogger(TaskErrorCloseListen.class);

    @Autowired
    private TaskService taskService;

    @Override
    public void onApplicationEvent(TaskErrorCloseEvent event) {
        log.info("收到异常关闭事件: {}", event);
        TaskEvent taskEvent = event.getTaskEvent();
        if (!Objects.equals(TaskEvent.EVENT_ERROR_CLOSE, taskEvent.getEvent())) {
            return;
        }

        log.info("处理task的异常关闭事件, event: {}", taskEvent);
        if (TaskEvent.WHERE_OES_TASK.equals(taskEvent.getWhere())) {
            if (TaskEvent.TYPE_CLOSE.equals(taskEvent.getType())) {
                JSONObject data = (JSONObject) taskEvent.getData();
                Task closeTask = data.toJavaObject(Task.class);
                // 继续关闭关联的task
                taskService.errorCloseTask(closeTask, Boolean.TRUE);
            }
        }

    }
}
