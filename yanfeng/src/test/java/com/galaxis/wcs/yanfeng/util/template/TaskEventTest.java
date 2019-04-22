package com.galaxis.wcs.yanfeng.util.template;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.galaxis.wcs.yanfeng.database.oes.domain.Task;
import com.galaxis.wcs.yanfeng.work.manager.TaskManager;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

public class TaskEventTest {
    @Test
    public void test1() {
        TaskEvent remark = new TaskEvent();
        remark.setEvent(TaskEvent.EVENT_FINISH);
        remark.setType(TaskEvent.TYPE_INSERT);
        remark.setWhere(TaskEvent.WHERE_WCS_TASK);
        remark.setData("json");

        String s = JSON.toJSONString(remark);
        System.out.println("s = " + s);

    }

    @Test
    public void testAdd() {
        Task task = new Task();

        List<TaskEvent> events = TaskManager.getEvents(task);
        events.forEach(taskEvent -> {
            System.out.println("taskEvent = " + taskEvent);
        });
        TaskEvent tr1 = new TaskEvent();
        tr1.setEvent(TaskEvent.EVENT_FINISH);
        TaskManager.addEvent(task, tr1);

        TaskEvent tr2 = new TaskEvent();
        tr2.setType(TaskEvent.TYPE_INSERT);
        System.out.println("task.getRemark() = " + task.getRemark());
        TaskManager.addEvent(task, tr2);
        System.out.println("task.getRemark() = " + task.getRemark());



    }

    @Test
    public void testCase() {
        Task task = new Task();
        Task t2 = new Task();
        t2.setStatus(1);
        t2.setCreateTime(LocalDateTime.now());

        TaskEvent finishEvent = new TaskEvent(TaskEvent.EVENT_FINISH, TaskEvent.WHERE_WCS_TASK, TaskEvent.TYPE_INSERT, "");
        TaskManager.addEvent(task, finishEvent);
        TaskEvent errorEvent = new TaskEvent(TaskEvent.EVENT_ERROR_CLOSE, TaskEvent.WHERE_OES_TASK, TaskEvent.TYPE_CLOSE, task);
        TaskManager.addEvent(task, errorEvent);
        System.out.println("task = " + task);

        List<TaskEvent> events = TaskManager.getEvents(task);
        Consumer<TaskEvent> errorCloseEventConsumer = taskEvent -> {
            if (TaskEvent.WHERE_OES_TASK.equals(taskEvent.getWhere())) {
                JSONObject data = (JSONObject) taskEvent.getData();
                Task closeTask = data.toJavaObject(Task.class);
                System.out.println("closeTask = " + closeTask);
            }
        };

        events.stream()
                .filter(taskEvent -> TaskEvent.EVENT_ERROR_CLOSE.equals(taskEvent.getEvent()))
                .forEach(errorCloseEventConsumer);

    }

}