package com.galaxis.wcs.yanfeng.database.oes.service.impl;

import com.galaxis.wcs.yanfeng.database.oes.domain.*;
import com.galaxis.wcs.yanfeng.database.oes.mapper.MainTaskMapper;
import com.galaxis.wcs.yanfeng.database.oes.mapper.MyBatisBaseDao;
import com.galaxis.wcs.yanfeng.database.oes.service.BoxService;
import com.galaxis.wcs.yanfeng.database.oes.service.KtService;
import com.galaxis.wcs.yanfeng.database.oes.service.MainTaskService;
import com.galaxis.wcs.yanfeng.database.oes.service.TaskService;
import com.galaxis.wcs.yanfeng.util.Constance;
import com.galaxis.wcs.yanfeng.util.template.TaskEvent;
import com.galaxis.wcs.yanfeng.work.manager.TaskManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class MainTaskServiceImpl extends AbstractServiceImpl<MainTask, Integer, MainTaskExample> implements MainTaskService {
    private static final Logger log = LoggerFactory.getLogger(MainTaskServiceImpl.class);

    @Autowired
    private TaskManager taskManager;
    @Autowired
    private MainTaskMapper mainTaskMapper;
    @Autowired
    private BoxService boxService;
    @Autowired
    private KtService ktService;
    @Autowired
    private TaskService taskService;

    @Override
    protected MyBatisBaseDao getMapper() {
        return mainTaskMapper;
    }

    @Override
    public List<MainTask> queryByCartonNo(String cartonNo, List<Integer> statuses, Boolean include) {
        MainTaskExample mainTaskExample = new MainTaskExample();
        MainTaskExample.Criteria criteria = mainTaskExample.createCriteria()
                .andCartonNoEqualTo(cartonNo);

        if (Objects.nonNull(statuses)) {
            if (include) {
                criteria.andStatusIn(statuses);
            } else {
                criteria.andStatusNotIn(statuses);
            }
        }

        return selectByExample(mainTaskExample);
    }

    @Override
    public MainTask updateFromGoLibToGoKt(Kt kt) {
        String partNo = kt.getPartNo();
        String oldestRecLot = boxService.getOldestRecLot(partNo);
        // 查询主任务, 类型为1入库/11回库/31移库, 状态是0待执行, 2在途(输送线), parton_no相同的
        List<MainTask> mainTasks = mainTaskMapper.selectMainTaskWithRecLot(oldestRecLot
                , partNo
                , Arrays.asList(Constance.MAIN_TASK_TYPE_TO_LIB, Constance.MAIN_TASK_TYPE_BACK_LIB, Constance.MAIN_TASK_TYPE_MOVE)
                , Arrays.asList(Constance.TASK_STATUS_INIT, Constance.TASK_STATUS_RUNNING_LINE));
        if (mainTasks.size() == 0) {
            return null;
        }

        // 修改第一个主任务的类型为KT补料任务
        MainTask mainTask = mainTasks.get(0);
        MainTask mTask = new MainTask();
        mTask.setId(mainTask.getId());
        mTask.setType(Constance.MAIN_TASK_TYPE_KT);
        mTask.setDescription(mainTask.getDescription() + " --> KT");
        mTask.setrLevel(kt.getLevel());
        mTask.setrLocation(kt.getLocation());
        mTask.setKtId(kt.getId());
        updateByPrimaryKeySelective(mTask);
        log.info("修改主任务类型为KT补料, mainTask id: {}, type: {}, rLevel: {}, rLocation: {}, ktId: {}"
                , mTask.getId(), mTask.getType(), mTask.getrLevel(), mTask.getrLocation(), mTask.getKtId());
        return mainTask;
    }

    @Override
    public int createAndGiveUp(MainTask mainTask, Boolean giveUpOld) {
        if (giveUpOld) {
            String cartonNo = mainTask.getCartonNo();
            // 废弃历史记录里正在执行的主任务和子任务
            deal(cartonNo, Constance.TASK_STATUS_RUNNING_LIST, Constance.TASK_STATUS_DISCARD, Boolean.TRUE);
            // 把之前异常关闭的任务设置为异常已处理
            deal(cartonNo, Collections.singletonList(Constance.TASK_STATUS_ERROR_CLOSE), Constance.TASK_STATUS_ERROR_PROCESS, Boolean.TRUE);

        }
        return insertSelective(mainTask);
    }

    @Override
    public void deal(String cartonNo, List<Integer> statuses, Integer status, Boolean dealChildren) {
        // 查出满足状态的主任务
        MainTaskExample mainTaskExample = new MainTaskExample();
        mainTaskExample.createCriteria()
                .andCartonNoEqualTo(cartonNo)
                .andStatusIn(statuses);
        List<MainTask> mainTasks = selectByExample(mainTaskExample);
        if (mainTasks.size() > 0) {
            LocalDateTime now = LocalDateTime.now();
            MainTask mTask = new MainTask();
            mTask.setStatus(status);
            mTask.setEndTime(now);

            Task tsk = new Task();
            tsk.setStatus(status);
            tsk.setEndTime(now);

            // 需要被处理的任务的ids
            List<Integer> mids = mainTasks.stream().map(MainTask::getId).collect(Collectors.toList());
            // 更新主任务id在mids的为status
            MainTaskExample mExample = new MainTaskExample();
            mExample.createCriteria().andIdIn(mids);
            int im = updateByExampleSelective(mTask, mainTaskExample);
            // 更新子任务pid在mids的为废弃
            TaskExample tExample = new TaskExample();
            tExample.createCriteria().andPidIn(mids).andStatusIn(statuses);
            List<Task> tasks = taskService.selectByExample(tExample);
            // 需要被处理的子任务的tids
            List<Integer> tids = tasks.stream().map(Task::getId).collect(Collectors.toList());
            int ik = 0;
            if (!tids.isEmpty()) {
                TaskExample tUpdateExample = new TaskExample();
                tUpdateExample.createCriteria().andIdIn(tids);
                ik = taskService.updateByExampleSelective(tsk, tUpdateExample);

            }
            log.info("已更新 {}条历史主任务, {}条对应的正在运行的历史子任务, cartonNo: {}, status: {}, mainTaskIds: {}, taskIds: {}"
                    , im, ik, cartonNo, status, mids, tids);

            // 事件处理
            switch (status) {
                case Constance.TASK_STATUS_DISCARD: {
                    // 如果是废弃任务, 也触发异常关闭事件
                    tasks.forEach(task -> taskManager.dealEvent(task, TaskEvent.EVENT_ERROR_CLOSE));
                    // 如果废弃的是kt任务, 不需要重新生成kt叫料任务
                    // 如果传感器有效, 会自动叫料
                    // 如果传感器无效, 人工再点击一次kt叫料
                    Predicate<MainTask> isKtMTask = mainTask -> Objects.nonNull(mainTask.getKtId());
                    mainTasks.stream().filter(isKtMTask).forEach(mainTask -> {
                        Integer ktId = mainTask.getKtId();
                        if (Constance.TASK_STATUS_INIT == mainTask.getStatus()) {
                            ktService.incrementNumber(ktId, -1, "number");
                            log.info("废弃kt补料主任务, 修改KT number -1, kt id: {}", ktId);
                        } else {
                            ktService.incrementNumber(ktId, -1, "number", "run_number");
                            log.info("废弃kt补料主任务, 修改KT number, run_number -1, kt id: {}", ktId);
                        }
                    });
                    break;
                }
                case Constance.TASK_STATUS_FINISH: {
                    tasks.forEach(task -> taskManager.dealEvent(task, TaskEvent.EVENT_FINISH));
                    break;
                }
                default:
            }

        }

    }
}
