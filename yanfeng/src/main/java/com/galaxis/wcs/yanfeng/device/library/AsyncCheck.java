package com.galaxis.wcs.yanfeng.device.library;

import com.galaxis.wcs.yanfeng.database.oes.domain.*;
import com.galaxis.wcs.yanfeng.database.oes.service.BoxService;
import com.galaxis.wcs.yanfeng.database.oes.service.LocationService;
import com.galaxis.wcs.yanfeng.database.oes.service.TaskService;
import com.galaxis.wcs.yanfeng.util.Constance;
import com.galaxis.wcs.yanfeng.util.SpringBeanFactoryTool;
import com.galaxis.wcs.yanfeng.util.ThreadUtil;
import com.galaxis.wcs.yanfeng.util.template.OesRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * 出7/8的时候, 如果1/2是预占用, 则
 * 监控1/2货位的入库任务, 等待其完成并且1/2变为占用之后再调用 质检/盘点
 * 如果任务异常, 也可以调用
 */
public class AsyncCheck implements Callable<String> {
    private static final Logger log = LoggerFactory.getLogger(AsyncCheck.class);

    private OesRequest request;
    private BoxService boxService = SpringBeanFactoryTool.getBean(BoxService.class);
    private TaskService taskService = SpringBeanFactoryTool.getBean(TaskService.class);
    private LocationService locationService = SpringBeanFactoryTool.getBean(LocationService.class);
    private YanFengTask yanFengTask = SpringBeanFactoryTool.getBean(YanFengTask.class);

    public AsyncCheck(OesRequest request) {
        this.request = request;
    }

    @Override
    public String call() {
        String cartonNo = request.getCartonNo();
        Box box = boxService.getBoxByCartonNo(cartonNo);

        Integer level = box.getLevel();
        Integer location = box.getLocation();

        if (location % 10 > 2) {
            Location l = locationService.selectByPrimaryKey(new LocationKey(level, location - 6));
            if (Constance.LOCATION_STATUS_OCCUPY_ADVANCE.equals(l.getStatus())) {
                // 预占用
                TaskExample taskExample = new TaskExample();
                taskExample.createCriteria()
                        .andELevelEqualTo(l.getLevel())
                        .andELocationEqualTo(l.getLocation())
                        .andStatusIn(Constance.TASK_STATUS_RUNNING_LIST);
                List<Task> tasks = taskService.selectByExample(taskExample);
                if (tasks.isEmpty()) {
                    return "任务异常";
                }

                if (tasks.size() > 1) {
                    log.warn("终点level: {}, location: {}对应多个正在执行的任务, tasks: {}"
                            , l.getLevel(), l.getLocation(), tasks);
                }

                Task task = tasks.get(0);
                Integer tid = task.getId();

                while (true) {
                    ThreadUtil.sleep(ThreadUtil.SLEEP_SHORT);
                    Task t = taskService.selectByPrimaryKey(tid);
                    if (Constance.TASK_STATUS_RUNNING_LIST.contains(t.getStatus())) {
                        continue;
                    }

                    // 外侧的任务不是运行时状态, 就可以调用质检/盘点了
                    // 外侧任务完成, 则1/2空闲, 正常质检/盘点, 外侧任务异常, 则1/2空闲, 质检/盘点会直接执行
                    return yanFengTask.qualityCheck(request);
                }

            }
        }
        return "任务异常";
    }
}
