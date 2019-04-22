package com.galaxis.wcs.yanfeng.database.oes.service.impl;

import com.galaxis.wcs.yanfeng.database.oes.domain.*;
import com.galaxis.wcs.yanfeng.database.oes.mapper.MyBatisBaseDao;
import com.galaxis.wcs.yanfeng.database.oes.mapper.TaskMapper;
import com.galaxis.wcs.yanfeng.database.oes.service.*;
import com.galaxis.wcs.yanfeng.device.library.YanFengTask;
import com.galaxis.wcs.yanfeng.util.Config;
import com.galaxis.wcs.yanfeng.util.Constance;
import com.galaxis.wcs.yanfeng.util.template.TaskEvent;
import com.galaxis.wcs.yanfeng.work.manager.ConfigManager;
import com.galaxis.wcs.yanfeng.work.manager.TaskManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl extends AbstractServiceImpl<Task, Integer, TaskExample> implements TaskService {
    private static final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    private ConfigManager configManager;
    @Autowired
    private TaskManager taskManager;
    @Autowired
    private YanFengTask yanFengTask;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private MainTaskService mainTaskService;
    @Autowired
    private KtService ktService;
    @Autowired
    private BoxService boxService;
    @Autowired
    private WarehousingService warehousingService;
    @Autowired
    private OroderService oroderService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private MessageService messageService;

    @Override
    protected MyBatisBaseDao getMapper() {
        return taskMapper;
    }

    @Override
    public Task getBySeq(Long seq) {
        TaskExample taskExample = new TaskExample();
        taskExample.createCriteria().andSeqEqualTo(seq);
        List<Task> tasks = this.selectByExample(taskExample);
        if (tasks.size() > 0) {
            return tasks.get(0);
        }
        return null;
    }

    @Override
    public int updateStatusBySeq(Task task) {

        Long seq = task.getSeq();
        Integer status = task.getStatus();
        TaskExample taskExample = new TaskExample();
        taskExample.createCriteria()
                .andSeqEqualTo(seq);
        int i = updateByExampleSelective(task, taskExample);
        log.info("根据seq更新task的状态, seq: {}, status: {}", seq, status);

        return i;
    }

    @Override
    public void errorCloseTask(Task task, Boolean cancelMain) {
        // 取消主任务, 子任务, 修改料箱状态, 如果是KT补料任务, 还需要重新生产KT补料任务
        LocalDateTime now = LocalDateTime.now();
        Task tsk = new Task();
        tsk.setId(task.getId());
        tsk.setStatus(Constance.TASK_STATUS_ERROR_CLOSE);
        tsk.setEndTime(now);
        updateByPrimaryKeySelective(tsk);
        log.info("异常关闭task, id: {}, status: {}", task.getId(), tsk.getStatus());
        String cartonNo = task.getCartonNo();
        Box box = boxService.getBoxByCartonNo(cartonNo);
        // 如果任务还未开始, 更新box为空闲即可, 否则更新box状态为丢失
        int mTskStatus = Constance.TASK_STATUS_ERROR_CLOSE;
        if (Arrays.asList(Constance.TASK_STATUS_INIT, Constance.TASK_STATUS_PAUSE).contains(task.getStatus())) {
            boxService.cleanTask(cartonNo);
            log.info("清除box的任务, cartonNo: {}", cartonNo);

            if (Constance.TASK_TYPE_INBOUND.equals(task.getType())) {
                // 入库异常关闭, 设置终点为空闲, 并解锁7/8
                locationService.updateStatus(task.geteLevel(), task.geteLocation(), Constance.LOCATION_STATUS_FREE, null, boxService.isLength(box));
                boxService.unlock78(task.geteLevel(), task.geteLocation());
            }

            // 因为任务还没开始, 此时可设置主任务状态位异常已处理
            mTskStatus = Constance.TASK_STATUS_ERROR_PROCESS;
        } else if (Constance.TASK_STATUS_RUNNING_LIST.contains(task.getStatus())) {
            Box bx = new Box();
            bx.setCartonNo(task.getCartonNo());
            bx.setStatus(Constance.BOX_STATUS_LOST);
            bx.setLevel(Constance.LIB_LEVEL_LOST);
            bx.setLocation(Constance.LIB_LOCATION_LOST);
            boxService.updateByCartonNo(bx);
            log.info("更新box状态为丢失, box cartonNo: {}, status: {}, level: {}, location: {}"
                    , bx.getCartonNo(), bx.getStatus(), bx.getLevel(), bx.getLocation());
            // 更新location信息
            Integer type = task.getType();
            if (Arrays.asList(Constance.TASK_TYPE_TRANSPORT, Constance.TASK_TYPE_OUTBOUND).contains(type)) {
                Boolean boxIsLength = boxService.isLength(box);
                LocationKey locationKey = new LocationKey(task.getsLevel(), task.getsLocation());
                Location location = locationService.selectByPrimaryKey(locationKey);
                if (!Constance.LOCATION_STATUS_FREE.equals(location.getStatus())) {
                    // 同层搬运/出库任务, 如果小车取货完成, 则货位状态为free, 不需要更改
                    // 否则, 设置起点为未知占用 -3
                    locationService.updateStatus(task.getsLevel(), task.getsLocation(), Constance.LOCATION_STATUS_OCCUPY_UNKNOWN, box.getCartonNo(), boxIsLength);
                }
            } else if (Constance.TASK_TYPE_INBOUND.equals(type)) {
                Boolean boxIsLength = boxService.isLength(box);
                // 入库任务, 设置终点为未知占用-3
                // locationService.updateStatus(task.geteLevel(), task.geteLocation(), Constance.LOCATION_STATUS_OCCUPY_UNKNOWN, box.getCartonNo(), boxIsLength);
                // 入库任务, 设置终点为空闲
                locationService.updateStatus(task.geteLevel(), task.geteLocation()
                        , Constance.LOCATION_STATUS_FREE, null, boxIsLength);
                log.info("入库任务seq: {}失败, 修改终点货位为空闲, level: {}, location: {}"
                        , task.getSeq(), task.geteLevel(), task.geteLocation());
                boxService.unlock78(task.geteLevel(), task.geteLocation());

            }
        }

        if (cancelMain) {
            MainTask mainTask = mainTaskService.selectByPrimaryKey(task.getPid());

            MainTask mTsk = new MainTask();
            mTsk.setId(mainTask.getId());
            mTsk.setStatus(mTskStatus);
            mTsk.setEndTime(now);
            mainTaskService.updateByPrimaryKeySelective(mTsk);
            log.info("异常关闭main task, id: {}", mTsk.getId());

            TaskExample closeExample = new TaskExample();
            closeExample.createCriteria()
                    .andPidEqualTo(mainTask.getId())
                    .andStatusIn(Constance.TASK_STATUS_RUNNING_LIST);
            Task closeTask = new Task();
            closeTask.setStatus(Constance.TASK_STATUS_ERROR_CLOSE);
            int closeTskCount = updateByExample(closeTask, closeExample);
            if (closeTskCount > 0) {
                log.warn("更新相同pid: {}的task为关闭, 数量: {}", task.getPid(), closeTskCount);
            }

            if (Integer.valueOf(Constance.MAIN_TASK_TYPE_KT).equals(mainTask.getType())) {
                // 如果是KT补料任务, 需要重新生成KT
                Kt kt = ktService.selectByPrimaryKey(mainTask.getKtId());
                if (Objects.isNull(kt)) {
                    log.info("叫料KT未在系统中配置, ktId: {}", mainTask.getKtId());
                    return;
                }
                if (mainTask.getStatus() > 0) {
                    // 如果之前的任务状态是运行中, 就将kt的run_number减一
                    ktService.incrementNumber(kt.getId(), -1, "number", "run_number");
                    log.info("修改KT number, run_number -1, kt id: {}", kt.getId());
                    // log.info("重新计算box去KT补料, ktId: {}", kt.getId());
                    // yanFengTask.executeKt(kt, mainTask.getCartonNo());
                }
            }

        }

        // 处理事件
        taskManager.dealEvent(task, TaskEvent.EVENT_ERROR_CLOSE);
    }

    @Override
    public int createAndFinish(Task task, Boolean finishOld) {
        finishOld = false;
        if (finishOld) {
            // 创建子任务之前, 先设置同mainTask的前置子任务为完成
            TaskExample taskExample = new TaskExample();
            taskExample.createCriteria()
                    .andPidEqualTo(task.getPid())
                    .andStatusIn(Constance.TASK_STATUS_RUNNING_LIST);
            List<Task> tasks = selectByExample(taskExample);
            if (!tasks.isEmpty()) {
                tasks.forEach(this::finish);
                /*List<Integer> tskIds = tasks.stream().map(Task::getId).collect(Collectors.toList());

                Task tsk = new Task();
                tsk.setStatus(Constance.TASK_STATUS_FINISH);
                tsk.setEndTime(LocalDateTime.now());
                TaskExample tExample = new TaskExample();
                taskExample.createCriteria().andIdIn(tskIds);
                int i = updateByExampleSelective(tsk, tExample);
                if (i > 0) {
                    log.warn("更新了之前步骤的子任务为完成状态, 可能是反馈滞后导致, task pid: {}, ids: {}", task.getPid(), tskIds);
                }*/
            }
        }

        return insertSelective(task);
    }

    @Override
    public void start(Task task) {
        // 判断不是初始化则不继续
        if (!Arrays.asList(Constance.TASK_STATUS_INIT, Constance.TASK_STATUS_PAUSE).contains(task.getStatus())) {
            log.warn("对应子任务状态不是初始化, id: {}, seq: {}, status: {}"
                    , task.getId(), task.getSeq(), task.getStatus());
            return;
        }

        Long seq = task.getSeq();
        Integer type = task.getType();
        String cartonNo = task.getCartonNo();
        int mainTaskStatus;
        Integer boxPosition;
        if (Constance.TASK_TYPE_RUNNING_LINE.equals(type)) {
            mainTaskStatus = Constance.TASK_STATUS_RUNNING_LINE;
            boxPosition = Constance.BOX_POSITION_RUNNING_LIEN;
        } else {
            mainTaskStatus = Constance.TASK_STATUS_RUNNING_LIB;
            boxPosition = Constance.BOX_POSITION_RUNNING_LIB;
        }

        // 更新子任务为开始
        Task tsk = new Task();
        tsk.setStatus(Constance.TASK_STATUS_RUNNING_LIB);
        tsk.setSeq(seq);
        updateStatusBySeq(tsk);

        // 状态变为 运行时, 反查主任务
        MainTask mainTask = mainTaskService.selectByPrimaryKey(task.getPid());
        // 如果task的s.* == maintask的s.*, 则更新主任务为开始
        if (task.getsLevel().equals(mainTask.getsLevel())
                && task.getsLocation().equals(mainTask.getsLocation())) {
            MainTask mTask = new MainTask();
            mTask.setId(task.getPid());
            // 立库
            mTask.setStatus(mainTaskStatus);
            mainTaskService.updateByPrimaryKeySelective(mTask);
            log.info("更新主任务状态为开始, mainTask id: {}, status: {}", mTask.getId(), mTask.getStatus());
            // 如果是KT补料任务开始, 更新KT运行任务数
            if (Integer.valueOf(Constance.MAIN_TASK_TYPE_KT).equals(mainTask.getType())) {
                // 修改该KT的run_number数 + 1
                ktService.incrementNumber(mainTask.getKtId(), 1, "run_number");
                log.info("修改KT run_number +1, kt id: {}", mainTask.getKtId());
            }
        }

        taskManager.dealEvent(task, TaskEvent.EVENT_START);

        /*Box box = boxService.getBoxByCartonNo(cartonNo);
        if (Constance.TASK_TYPE_OUTBOUND.equals(type) || Constance.TASK_TYPE_TRANSPORT.equals(type)) {
            // 如果是出库/同层搬运任务, 更新起点货位为临时预占用-2
            Boolean boxIsLength = boxService.isLength(box);
            locationService.updateStatus(task.getsLevel(), task.getsLocation(), Constance.LOCATION_STATUS_OCCUPY_TEMPORARY, cartonNo, boxIsLength);
        }*/
        // 更新box的position
        Box bx = new Box();
        bx.setCartonNo(cartonNo);
        bx.setBoxPosition(boxPosition);
        boxService.updateByCartonNo(bx);
        log.info("更新boxPosition为运行(在途), cartonNo: {}, position: {}", bx.getCartonNo(), bx.getBoxPosition());
    }

    @Override
    public void finish(Task task) {
        // 判断任务已完成则不继续更新
        if (!Constance.TASK_STATUS_RUNNING_LIST.contains(task.getStatus())) {
            log.info("对应子任务不是运行状态, id: {}, seq: {}, status: {}, endTime: {}"
                    , task.getId(), task.getSeq(), task.getStatus(), task.getEndTime());
            return;
        }

        if (Arrays.asList(Constance.TASK_TYPE_OUTBOUND, Constance.TASK_TYPE_TRANSPORT).contains(task.getType())
                && Arrays.asList(Constance.TASK_STATUS_INIT, Constance.TASK_STATUS_PAUSE).contains(task.getStatus())) {
            // 出库, 同层搬运任务, 如果发送完成的时候还是初始化, 则先执行取货步骤之后再完成该任务
            pickup(task);
        }

        LocalDateTime now = LocalDateTime.now();
        // 对任务的维护, 根据wmsid查询子任务, 更新子任务为完成
        Long seq = task.getSeq();
        Task tsk = new Task();
        tsk.setSeq(seq);
        tsk.setStatus(Constance.TASK_STATUS_FINISH);
        tsk.setEndTime(now);
        int i = updateStatusBySeq(tsk);
        if (i > 0) {
            // 对box和location的维护
            updateBox(task);

            taskManager.dealEvent(task, TaskEvent.EVENT_FINISH);

            // 状态为完成, 反查主任务
            // 如果task的e.* == maintask的e.*/r.*, 则更新主任务为完成
            MainTask mainTask = mainTaskService.selectByPrimaryKey(task.getPid());
            if (!TaskManager.checkFinish(mainTask, task)) {
                return;
            }
            MainTask mTask = new MainTask();
            mTask.setId(task.getPid());
            mTask.setStatus(Constance.TASK_STATUS_FINISH);
            mTask.setEndTime(now);
            mainTaskService.updateByPrimaryKeySelective(mTask);
            log.info("更新主任务状态为完成, mainTask id: {}, status: {}", mTask.getId(), mTask.getStatus());

            TaskExample finishAllExample = new TaskExample();
            finishAllExample.createCriteria()
                    .andPidEqualTo(task.getPid())
                    .andStatusIn(Constance.TASK_STATUS_RUNNING_LIST);
            Task finishTsk = new Task();
            finishTsk.setStatus(Constance.TASK_STATUS_FINISH);
            int finishTskCount = updateByExample(finishTsk, finishAllExample);
            if (finishTskCount != 0) {
                log.warn("更新相同pid: {}的task为完成, 数量: {}", task.getPid(), finishTskCount);
            }

            // 更新掉box的状态
            boxService.cleanTask(task.getCartonNo());

            Integer ktId = mainTask.getKtId();
            if (ktId != null) {
                // 如果mamintask有ktid, 则一次补货完成, 更新kt的running_nuber - 1, number - 1
                ktService.incrementNumber(ktId, -1, "number", "run_number");
                log.info("修改KT number, run_number -1, kt id: {}", ktId);

                // 出KT完成, 还要反馈"入库"信息给上位系统
                Warehousing warehousing = new Warehousing();
                String cartonNo = mainTask.getCartonNo();
                Box box = boxService.getBoxByCartonNo(cartonNo);
                BeanUtils.copyProperties(box, warehousing, "id");
                // 如果是通过订单收货的, 还要更新创建*信息
                Oroder oroder = oroderService.getByCartonNo(cartonNo);
                if (Objects.nonNull(oroder)) {
                    warehousing.setCreateUser(oroder.getCreateUser());
                    warehousing.setCreateYmd(oroder.getCreateYmd());
                    warehousing.setCreateHms(oroder.getCreateHms());
                }
                warehousing.setActflg("N");
                warehousingService.insertSelective(warehousing);
                log.info("给上位系统反馈入库(出KT库), id: {}, warehousing: {}", warehousing.getId(), warehousing);
            }

        }

    }

    @Override
    public void pickup(Task task) {
        // 判断不是初始化则不继续
        if (!Arrays.asList(Constance.TASK_STATUS_INIT, Constance.TASK_STATUS_PAUSE).contains(task.getStatus())) {
            log.warn("对应子任务状态不是初始化, id: {}, seq: {}, status: {}"
                    , task.getId(), task.getSeq(), task.getStatus());
            return;
        }

        Integer type = task.getType();
        if (Arrays.asList(Constance.TASK_TYPE_OUTBOUND, Constance.TASK_TYPE_TRANSPORT).contains(type)) {
            Box box = boxService.getBoxByCartonNo(task.getCartonNo());
            // 如果是出库/同层搬运任务, 小车取货完成, 可以设置货位为空闲
            locationService.updateStatus(task.getsLevel(), task.getsLocation()
                    , Constance.LOCATION_STATUS_FREE, null, boxService.isLength(box));
            log.info("{}任务seq: {}取货完成, 修改起点货位为空闲, level: {}, location: {}"
                    , task.getType(), task.getSeq(), task.getsLevel(), task.getsLocation());

            if (Config.VALUE_MODE_LOCATION_DOUBLE.equalsIgnoreCase(configManager.getConfigure(Config.KEY_MODE_LOCATION))) {
                // 双深货位, 1,2货位出库完成时(长箱会被分配到7,8, 所以1,2只会是短箱), 解锁78货位的箱子
                boxService.unlock78(task.getsLevel(), task.getsLocation());
            }

            start(task);

        }
    }

    @Override
    public Map<Integer, Integer> mapLevelCountByPartNo(String partNo, List<Integer> types, List<Integer> status) {

        List<Task> tasks = taskMapper.selectLevelCountByPartNo(partNo, types, status);
        return tasks.stream().collect(Collectors.toMap(Task::geteLevel, Task::geteLocation));
    }

    /**
     * 该子任务结束后, 对对应的box和location维护
     *
     * @param task task
     */
    private void updateBox(Task task) {
        Box box = boxService.getBoxByCartonNo(task.getCartonNo());
        Boolean boxIsLength = boxService.isLength(box);
        Integer type = task.getType();
        if (Constance.TASK_TYPE_TRANSPORT.equals(type) || Constance.TASK_TYPE_OUTBOUND.equals(type)) {
            // 更新box的level, location, status等
            Box bx = new Box();
            bx.setCartonNo(task.getCartonNo());
            bx.setLevel(task.geteLevel());
            bx.setLocation(task.geteLocation());

            if (Constance.TASK_TYPE_TRANSPORT.equals(type)) {
                // 同层搬运完成, 肯定是出KT
                bx.setBoxPosition(Constance.BOX_POSITION_OUTBOUND);
                bx.setStatus(Constance.BOX_STATUS_OUTBOUND_KT);
            } else {
                bx.setStatus(Constance.BOX_STATUS_OUTBOUND);
            }

            boxService.updateByCartonNo(bx);
            log.info("更新box, cartonNo: {}, level: {}, location: {}, status: {}"
                    , bx.getCartonNo(), bx.getLevel(), bx.getLocation(), bx.getStatus());

            // 同层搬运任务/出库, 更新起点货位为空闲0 -- 优化为取货完成即清除货位占用, 所以这里不用再清除
            // locationService.updateStatus(task.getsLevel(), task.getsLocation(), Constance.LOCATION_STATUS_FREE, Constance.EMPTY, boxIsLength);
        } else if (Constance.TASK_TYPE_INBOUND.equals(type)) {
            // 更新box的level, location, status等
            Box bx = new Box();
            bx.setCartonNo(task.getCartonNo());
            bx.setLevel(task.geteLevel());
            bx.setLocation(task.geteLocation());

            // 入库可能是入KT, 需要根据主任务类型来判断
            MainTask mainTask = mainTaskService.selectByPrimaryKey(task.getPid());
            Integer mainTaskType = mainTask.getType();
            if (Integer.valueOf(Constance.MAIN_TASK_TYPE_KT).equals(mainTaskType)) {
                // 入KT库
                bx.setBoxPosition(Constance.BOX_POSITION_OUTBOUND);
                bx.setStatus(Constance.BOX_STATUS_OUTBOUND_KT);
            } else {
                // 入立库
                bx.setBoxPosition(Constance.BOX_POSITION_LIB);
                bx.setStatus(Constance.BOX_STATUS_INBOUND);
                // 入库任务完成, 更新箱子的位置为占用1
                locationService.updateStatus(bx.getLevel(), bx.getLocation(), Constance.LOCATION_STATUS_OCCUPY, bx.getCartonNo(), boxIsLength);
            }
            boxService.updateByCartonNo(bx);
        }
    }

}
