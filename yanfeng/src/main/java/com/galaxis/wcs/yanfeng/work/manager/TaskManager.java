package com.galaxis.wcs.yanfeng.work.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.galaxis.wcs.yanfeng.database.oes.domain.MainTask;
import com.galaxis.wcs.yanfeng.database.oes.domain.Task;
import com.galaxis.wcs.yanfeng.database.oes.service.MessageService;
import com.galaxis.wcs.yanfeng.database.oes.service.TaskService;
import com.galaxis.wcs.yanfeng.event.TaskErrorCloseEvent;
import com.galaxis.wcs.yanfeng.event.TaskFinishEvent;
import com.galaxis.wcs.yanfeng.event.TaskStartEvent;
import com.galaxis.wcs.yanfeng.util.Constance;
import com.galaxis.wcs.yanfeng.util.template.LineRequest;
import com.galaxis.wcs.yanfeng.util.template.LineResponse;
import com.galaxis.wcs.yanfeng.util.template.TaskEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Component
public class TaskManager {
    private static final Logger log = LoggerFactory.getLogger(TaskManager.class);

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private TaskService taskService;
    @Autowired
    private MessageService messageService;

    /**
     * 分解主任务, 产生并持久化第一子任务
     *
     * @param mainTask 主任务
     * @param seq      输送线请求的序号或者下发任务给立库时的wmsid
     * @param bound    出/入库口位置
     */
    public Task doMainTask(MainTask mainTask, Long seq, Integer bound) {
        // 根据主任务的sLevel, sLocation, type, 生成第一个子任务
        Task task = new Task();
        task.setSeq(seq);

        task.setPid(mainTask.getId());
        task.setPartNo(mainTask.getPartNo());
        task.setCartonNo(mainTask.getCartonNo());
        task.setsLevel(mainTask.getsLevel());
        task.setsLocation(mainTask.getsLocation());
        task.setCreateTime(LocalDateTime.now());

        // 输送线任务类型 -> 1: 入库, 2: 去质检点, 3: 去异常口, 11: 回库
        Integer mainType = mainTask.getType();

        // 设置type, e_*, status
        // 输送线
        task.setType(Constance.TASK_TYPE_RUNNING_LINE);
        task.seteLevel(Constance.LINE_LEVEL);

        switch (mainType) {
            case Constance.MAIN_TASK_TYPE_TO_LIB:
            case Constance.MAIN_TASK_TYPE_BACK_LIB:
                // 入库, 回库
                task.seteLocation(bound);
                task.setStatus(Constance.TASK_STATUS_TO_LIB);
                break;
            case Constance.MAIN_TASK_TYPE_TO_CHECK:
                // 去质检点
                task.seteLocation(Constance.LINE_CHECK);
                task.setStatus(Constance.TASK_STATUS_TO_CHECK);
                break;
            case Constance.MAIN_TASK_TYPE_TO_ERROR:
                // 去异常口
                task.seteLocation(Constance.LINE_ERROR);
                task.setStatus(Constance.TASK_STATUS_TO_ERROR);
                break;
            default:
                log.info("未知类型的主任务type: {}, mainTask: {}", mainType, mainTask);
                return null;
        }

        taskService.createAndFinish(task, Boolean.TRUE);

        log.info("生成子任务, task id: {}, pid: {} seq: {}, cartonNo: {}, sLevel: {}, sLocation:{}, eLevel: {}, eLocation: {}",
                task.getId(), task.getPid(), task.getSeq(), task.getCartonNo(), task.getsLevel(), task.getsLocation(), task.geteLevel(), task.geteLocation());
        return task;
    }

    /**
     * 针对输送线, 根据主任务与请求响应, 生成子任务
     *
     * @param mainTask 主任务
     * @param request  请求
     * @param response 响应
     * @return 生产的子任务
     */
    public Task doNextLineTask(MainTask mainTask, LineRequest request, LineResponse response) {
        Task task = new Task(null, mainTask.getId(), request.getSeq().longValue(), Constance.TASK_TYPE_RUNNING_LINE
                , mainTask.getPartNo(), mainTask.getCartonNo()
                , Constance.LINE_LEVEL, request.getLocation()
                , Constance.LINE_LEVEL, response.getLocation()
                , null, null, Constance.TASK_STATUS_RUNNING_LINE, LocalDateTime.now(), null);

        taskService.createAndFinish(task, Boolean.TRUE);
        log.info("生成子任务, task id: {}, pid: {}, seq: {}, cartonNo: {}, sLevel: {}, sLocation: {}, eLevel: {}, eLocation: {}",
                task.getId(), task.getPid(), task.getSeq(), task.getCartonNo(), task.getsLevel(), task.getsLocation(), task.geteLevel(), task.geteLocation());

        return task;
    }

    /**
     * 处理任务的事件
     *
     * @param task      任务
     * @param eventType 事件
     */
    public void dealEvent(Task task, String eventType) {
        // 校验事件类型
        if (!Arrays.asList(
                TaskEvent.EVENT_START, TaskEvent.EVENT_FINISH, TaskEvent.EVENT_ERROR_CLOSE
        ).contains(eventType)) {
            log.info("未知的事件类型, eventType: {}, task : {}", eventType, task);
            return;
        }

        // 校验是否有事件
        List<TaskEvent> events = getEvents(task);
        if (events.isEmpty()) {
            return;
        }

        // 过滤并处理事件
        events.stream()
                .filter(new TaskEventPredicate(eventType))
                .forEach(taskEvent -> {
                    switch (eventType) {
                        case TaskEvent.EVENT_START:
                            applicationContext.publishEvent(new TaskStartEvent(this, taskEvent));
                            break;
                        case TaskEvent.EVENT_FINISH:
                            applicationContext.publishEvent(new TaskFinishEvent(this, taskEvent));
                            break;
                        case TaskEvent.EVENT_ERROR_CLOSE:
                            applicationContext.publishEvent(new TaskErrorCloseEvent(this, taskEvent));
                            break;
                        default:
                    }
                });
    }

    private class TaskEventPredicate implements Predicate<TaskEvent> {
        private String eventType;

        TaskEventPredicate(String eventType) {
            this.eventType = eventType;
        }

        @Override
        public boolean test(TaskEvent taskEvent) {
            return Objects.equals(eventType, taskEvent.getEvent());
        }
    }

    /**
     * 检查主任务是否完成
     *
     * @param mainTask 主任务
     * @param task     子任务
     * @return Boolean
     */
    public static boolean checkFinish(MainTask mainTask, Task task) {
        boolean flag;
        if (Objects.nonNull(task.getrLevel())) {
            // 如果task有r.*, task被调整过, 比较task的r.*与mainTask的r.*
            flag = Objects.equals(task.getrLevel(), mainTask.getrLevel())
                    && Objects.equals(task.getrLocation(), mainTask.getrLocation());
        } else {
            // task没有被调整过
            if (Objects.isNull(mainTask.getrLevel())) {
                // 主任务没有被调整过, 比较task的e.*与mainTask的e.*
                flag = Objects.equals(task.geteLevel(), mainTask.geteLevel())
                        && Objects.equals(task.geteLocation(), mainTask.geteLocation());
            } else {
                // 主任务被调整过, 比较task的e.*与mainTask的r.*
                flag = Objects.equals(task.geteLevel(), mainTask.getrLevel())
                        && Objects.equals(task.geteLocation(), mainTask.getrLocation());
            }
        }
        return flag;
    }

    /**
     * 给task添加事件
     *
     * @param task      任务
     * @param taskEvent 事件
     */
    public static void addEvent(Task task, TaskEvent taskEvent) {
        String remark = getEventString(task);
        JSONArray jsonArray = JSON.parseArray(remark);
        jsonArray.add(taskEvent);

        task.setRemark(jsonArray.toJSONString());
    }

    /**
     * 获取task的事件
     *
     * @param task 任务
     * @return 事件列表
     */
    public static List<TaskEvent> getEvents(Task task) {
        String remark = getEventString(task);
        return JSON.parseArray(remark, TaskEvent.class);
    }

    private static String getEventString(Task task) {
        String r = task.getRemark();
        if (Objects.isNull(r) || Constance.EMPTY.equals(r.trim())) {
            task.setRemark("[]");
        }
        return task.getRemark();
    }

}
