package com.galaxis.wcs.yanfeng.device.library;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.galaxis.wcs.yanfeng.config.ScheduleConfig;
import com.galaxis.wcs.yanfeng.connection.socket.SocketClient;
import com.galaxis.wcs.yanfeng.database.oes.domain.*;
import com.galaxis.wcs.yanfeng.database.oes.service.*;
import com.galaxis.wcs.yanfeng.database.wcs.domain.GaTask;
import com.galaxis.wcs.yanfeng.database.wcs.service.GaTaskService;
import com.galaxis.wcs.yanfeng.device.line.YanFengKtInboundHandler;
import com.galaxis.wcs.yanfeng.exception.OesException;
import com.galaxis.wcs.yanfeng.util.Config;
import com.galaxis.wcs.yanfeng.util.Constance;
import com.galaxis.wcs.yanfeng.util.ThreadUtil;
import com.galaxis.wcs.yanfeng.util.template.OesRequest;
import com.galaxis.wcs.yanfeng.util.template.TaskEvent;
import com.galaxis.wcs.yanfeng.util.template.WcsResult;
import com.galaxis.wcs.yanfeng.util.template.WcsSend;
import com.galaxis.wcs.yanfeng.work.manager.ConfigManager;
import com.galaxis.wcs.yanfeng.work.manager.TaskManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 下发同层搬运任务, 下发出库任务
 */
@Component
public class YanFengTask {
    private static final Logger log = LoggerFactory.getLogger(YanFengTask.class);

    @Autowired
    private SocketClient client;
    @Autowired
    private ConfigManager configManager;
    @Autowired
    private MessageService messageService;
    @Autowired
    private KtService ktService;
    @Autowired
    private BoxService boxService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private MainTaskService mainTaskService;
    @Autowired
    private GaTaskService gaTaskService;
    @Autowired
    private LocationService locationService;

    /**
     * KT叫料时触发
     *
     * @param ktId 　叫料ktId
     * @param type 叫料类型, 1: 传感器自动, 2: 现场主动
     */
    public String kt(Integer ktId, int type) {
        Kt kt = ktService.selectByPrimaryKey(ktId);
        String s = checkKt(kt);
        if (!Constance.SUCCESS.equals(s)) {
            log.warn("{}, ktId: {}", s, ktId);
            return s;
        }

        Integer sensor = kt.getSensor();
        if (type == Constance.KT_TYPE_AUTO) {
            // 自动叫料时, 如果传感器状态无效, 则忽略
            if (Objects.equals(Constance.KT_SENSOR_INVALID, sensor)) {
                return String.format("kt传感器无效, kt id: %s", kt.getId());
            }
        } else if (type == Constance.KT_TYPE_MANUAL) {
            // 主动叫料时, 如果传感器有效, 则修改kt任务优先级, 继续执行kt
            if (Objects.equals(Constance.KT_SENSOR_VALID, sensor)) {
                MainTaskExample mainTaskExample = new MainTaskExample();
                mainTaskExample.createCriteria()
                        .andKtIdEqualTo(kt.getId())
                        .andTypeEqualTo(Constance.MAIN_TASK_TYPE_KT)
                        .andStatusIn(Constance.TASK_STATUS_RUNNING_LIST);
                List<MainTask> mainTasks = mainTaskService.selectByExample(mainTaskExample);
                if (!mainTasks.isEmpty()) {
                    // 有正在执行的kt主任务就修改任务优先级
                    int i = -1;
                    Long seq = -1L;

                    MainTask mainTask = mainTasks.get(0);
                    TaskExample taskExample = new TaskExample();
                    taskExample.createCriteria()
                            .andPidEqualTo(mainTask.getId())
                            .andTypeEqualTo(Constance.TASK_TYPE_OUTBOUND)
                            .andStatusIn(Constance.TASK_STATUS_RUNNING_LIST);
                    List<Task> tasks = taskService.selectByExample(taskExample);
                    if (!tasks.isEmpty()) {
                        Task task = tasks.get(0);
                        seq = task.getSeq();
                        GaTask gaTask = new GaTask();
                        gaTask.setWmsid(seq.toString());
                        gaTask.setBoxNumber(task.getCartonNo());
                        gaTask.setPriority(kt.getPriority());
                        i = gaTaskService.addPriority(gaTask);
                    }
                    return String.format("kt传感器有效, 修改wcs任务优先级, kt id: %s, seq: %s, i: %s", kt.getId(), seq, i);
                }
            } else {
                return executeKt(kt, Constance.KT_MANUAL);
            }
        }

        return executeKt(kt);
    }

    /**
     * 执行KT补料
     * kt的number +1
     * 根据ktId对应的物料, 找到合适的入库/回库任务, 修改终点为KT
     * 如果入库/回库任务无法满足, 则找合适的箱子进行出库 -> 输送线移动 -> 入库操作
     * kt的run_number +1
     *
     * @param kt        叫料KT
     * @param cartonNos 排除的箱号, 同时可作为 手动kt/缓存kt的标志量
     */
    // @Transactional(rollbackFor = Exception.class, noRollbackFor = OesException.class)
    public String executeKt(Kt kt, String... cartonNos) {
        String s = checkKt(kt);
        if (!Constance.SUCCESS.equals(s)) {
            if (testType(Constance.KT_CACHE, cartonNos)) {
                closeCacheKt(kt);
            }
            return s;
        }
        String allow = ktService.checkAllow();
        if (!Constance.SUCCESS.equals(allow)) {
            if (testType(Constance.KT_CACHE, cartonNos)) {
                closeCacheKt(kt);
            }
            return allow;
        }

        // 执行kt补料前先检查kt的状态
        Integer ktId = kt.getId();
        Integer status = kt.getStatus();
        Integer sensor = kt.getSensor();

        if (!Constance.KT_STATUS_ALLOW.equals(status)) {
            if (testType(Constance.KT_CACHE, cartonNos)) {
                closeCacheKt(kt);
            }
            String msg = String.format("叫料KT状态不是允许补货, ktId: %s, status: %s", ktId, status);
            log.warn(msg);
            return msg;
        }

        // 如果传感器有效, 执行前判断该KT区是否空闲, 不空闲的时候不允许继续执行
        if (Constance.KT_SENSOR_VALID == sensor) {
            byte ktStatus = YanFengKtInboundHandler.ktStatuses[ktId];
            if (ktStatus == Constance.KT_STATUS_FULL) {
                if (testType(Constance.KT_CACHE, cartonNos)) {
                    closeCacheKt(kt);
                }
                String msg = String.format("该KT传感器有效, 且信号为不缺货, 丢弃该请求, ktId: %s", ktId);
                log.info(msg);
                return msg;
            }
        }

        // 以物料类型为锁, 减小锁的粒度
        synchronized (kt.getPartNo().intern()) {
            // 在锁里开启事务
            return doKt(kt, cartonNos);
        }

    }

    @Transactional(rollbackFor = Exception.class, noRollbackFor = OesException.class)
    public String doKt(Kt kt, String[] cartonNos) {
        Integer ktId = kt.getId();

        // 执行前判断是否有同KT的正在执行的主任务, 有的话不允许继续执行
        MainTaskExample ktMTskExample = new MainTaskExample();
        ktMTskExample.createCriteria().andKtIdEqualTo(ktId).andStatusIn(Constance.TASK_STATUS_RUNNING_LIST);
        List<MainTask> ktMainTasks = mainTaskService.selectByExample(ktMTskExample);
        if (!ktMainTasks.isEmpty()) {
            // 判断是否是手动叫料
            if (testType(Constance.KT_MANUAL, cartonNos)) {
                // 手动叫料推入缓存, 并反馈SUCCESS
                messageService.rightPush(Constance.KEY_OES_KT_CACHE, ktId);
                return Constance.SUCCESS;
            } else if (testType(Constance.KT_CACHE, cartonNos)) {
                // 缓存的kt补料, 要关闭
                closeCacheKt(kt);
            }
            String msg = String.format("已有该KT对应的主任务正在进行, ktId: %s, mainTasks: %s"
                    , ktId, ktMainTasks);
            log.info(msg);
            // ktService.incrementNumber(ktId, -1, "number");
            // log.info("修改KT number -1, kt id: {}", ktId);
            return msg;
        }

        // 如果有满足条件的主任务, 直接修改主任务即可
        MainTask mTask = mainTaskService.updateFromGoLibToGoKt(kt);
        if (Objects.nonNull(mTask)) {
            log.info("已修改主任务 {}去KT补料", mTask.getId());
            // 修改该KT的run_number数 + 1
            ktService.incrementNumber(ktId, 1, "run_number");
            log.info("修改KT run_number +1, kt id: {}", ktId);
            return Constance.SUCCESS;
        }
        // 没有可以修改主任务, 查找料箱出KT
        String result = boxToKt(kt, cartonNos);

        return result;
    }

    private boolean testType(String type, String[] cartonNos) {
        log.debug("testType, type: {}, cartonNos: {}", type, cartonNos);
        if (Objects.isNull(cartonNos) || cartonNos.length != 1) {
            return false;
        }
        return Objects.equals(type, cartonNos[0]);
    }

    private void closeCacheKt(Kt kt) {
        ktService.incrementNumber(kt.getId(), -1, "number");
        log.info("缓存kt补料任务丢弃, 修改KT number -1, kt id: {}", kt.getId());
    }

    public String checkKt(Kt kt) {
        if (Objects.isNull(kt)) {
            return "叫料KT未在系统中配置";
        }
        return Constance.SUCCESS;
    }

    /**
     * 计算料箱出到KT
     *
     * @param kt        　缺料KT
     * @param cartonNos 　排除的料箱
     */
    private String boxToKt(Kt kt, String... cartonNos) {
        String partNo = kt.getPartNo();
        Integer level = kt.getLevel();

        Box box = boxService.getBoxToKt(partNo, level, cartonNos);
        if (Objects.isNull(box)) {
            String msg = String.format("没有查到可出的料箱, partNo: %s, ktId: %s", partNo, kt.getId());
            messageService.rightPush(Constance.KEY_OES_KT_CACHE, kt.getId());
            log.info(msg);
            return Constance.SUCCESS;
        }
        log.info("分配box {}去kt, box id: {}, kt id: {}", box.getCartonNo(), box.getId(), kt.getId());

        // 将box出到KT, 并生成主任务, 子任务维护
        MainTask mainTask = new MainTask(null, Constance.MAIN_TASK_TYPE_KT, "KT"
                , box.getOrderNo(), box.getPartNo(), box.getCartonNo()
                , box.getLevel(), box.getLocation(), kt.getLevel(), kt.getLocation(), null, null
                , Constance.TASK_STATUS_INIT, LocalDateTime.now(), null, kt.getId());
        mainTaskService.createAndGiveUp(mainTask, Boolean.TRUE);
        log.info("生成去KT的主任务, mainTask id: {}", mainTask.getId());

        // 生成子任务
        Long seq = messageService.getSeq(Constance.KEY_OES_SEQ);
        Task task = new Task(null, mainTask.getId(), seq, null
                , mainTask.getPartNo(), mainTask.getCartonNo()
                , mainTask.getsLevel(), mainTask.getsLocation(), null, null, null, null
                , Constance.TASK_STATUS_INIT, LocalDateTime.now(), null);

        // 如果kt与箱子同层, 直接做搬运即可, 否则做出库
        if (kt.getLevel().equals(box.getLevel())) {
            task.setType(Constance.TASK_TYPE_TRANSPORT);
            task.seteLevel(kt.getLevel());
            task.seteLocation(kt.getLocation());
        } else {
            task.setType(Constance.TASK_TYPE_OUTBOUND);
            task.seteLevel(Constance.LIB_OUTBOUND_LEVEL);
            task.seteLocation(Constance.LIB_OUTBOUND_LOCATION);
        }
        taskService.createAndFinish(task, Boolean.TRUE);
        log.info("生成子任务, task id: {}, type: {}, sLevel: {}, sLocation: {}, eLevel: {}, eLocation: {}"
                , task.getId(), task.getType(), task.getsLevel(), task.getsLocation(), task.geteLevel(), task.geteLocation());

        // 修改box
        Box bx = new Box();
        bx.setId(box.getId());
        bx.setPlcSeq(task.getSeq().intValue());
        bx.setPlcTaskId(mainTask.getId());
        boxService.updateByPrimaryKeySelective(bx);

        // 发送任务给立库
        Boolean boxIsLength = boxService.isLength(box);

        WcsSend wcsSend = new WcsSend();
        wcsSend.setId(task.getId());
        wcsSend.setBoxId(task.getCartonNo());
        wcsSend.setSLevel(task.getsLevel());
        wcsSend.setSLocation(task.getsLocation());
        wcsSend.setWmsId(task.getSeq());
        wcsSend.setPriority(kt.getPriority());

        Map<String, Integer> data = new HashMap<>(4);
        wcsSend.setData(data);
        if (Constance.TASK_TYPE_TRANSPORT.equals(task.getType())) {
            // 同层搬运
            wcsSend.setMessageName("levelTransport");
            data.put("eLevel", kt.getLevel());
            data.put("eLocation", boxIsLength ? kt.getLocation() + 6 : kt.getLocation());
        } else {
            // 出库
            wcsSend.setMessageName("appointStockOut");
            data.put("outbound", configManager.getNumber(Config.KEY_LIB_OUTBOUND_ID, 0));
        }

        // 任务写入任务队列尾部
        messageService.rightPush(Constance.KEY_WCS_TASK, wcsSend);
        return Constance.SUCCESS;
    }

    /**
     * 质检/盘点任务
     * 对指定box下达质检/盘点任务, 先出库 -> 输送线移动
     *
     * @param request 请求信息
     */
    @Transactional(rollbackFor = Exception.class)
    public String qualityCheck(OesRequest request) {
        String description = request.getDescription();
        String cartonNo = request.getCartonNo();
        Box box = boxService.getBoxByCartonNo(cartonNo);
        if (Objects.isNull(box)) {
            log.info("没有这个箱子可进行{}, cartonNo: {}", description, cartonNo);
            return "没有这个箱子";
        }

        if (box.getLevel() <= 0) {
            log.info("该箱不在库内, cartonNo: {}, level: {} ,location: {}", box.getCartonNo(), box.getLevel(), box.getLocation());
            return "该箱不在库内";
        }

        Task outboundTask = null;
        WcsSend outboundSend = null;
        if (Objects.nonNull(box.getPlcTaskId())) {
            if (!Constance.BOX_TASK_LOCK.equals(box.getPlcTaskId())) {
                MainTask mainTask = mainTaskService.selectByPrimaryKey(box.getPlcTaskId());
                if (Objects.equals(Constance.MAIN_TASK_TYPE_MOVE, mainTask.getType())) {
                    // 如果是移库任务, 且没有子任务, 或者子任务还是出库任务时, 可以将该主任务改为质检/盘点
                    TaskExample taskExample = new TaskExample();
                    taskExample.createCriteria()
                            .andPidEqualTo(mainTask.getId());
                    taskExample.setOrderByClause("seq desc");
                    List<Task> tasks = taskService.selectByExample(taskExample);
                    if (tasks.size() <= 1) {
                        // 移库, 只有一个子任务, 那么肯定是出库
                        MainTask mTask = new MainTask();
                        mTask.setId(mainTask.getId());
                        mTask.setType(Constance.MAIN_TASK_TYPE_QUALITY);
                        mTask.setDescription(mainTask.getDescription() + " --> " + request.getDescription());
                        mainTaskService.updateByPrimaryKeySelective(mainTask);
                        log.info("已修改mainTask任务类型为质检/盘点, mainTask id: {}, cartonNo: {}"
                                , mainTask.getId(), mainTask.getCartonNo());
                        return Constance.SUCCESS;
                    }
                }

                // 该箱子在执行其他任务, 不可被质检/盘点
                log.info("该箱正在进行其他任务, cartonNo: {}, mainTaskId: {}", cartonNo, box.getPlcTaskId());
                return "该箱正在进行其他任务";
            }

            // 只会在双深货位才会存在该状态
            Location backLocation = locationService.selectByPrimaryKey(new LocationKey(box.getLevel(), locationService.getBackLocation(box.getLocation())));
            Integer backLocationStatus = backLocation.getStatus();

            // 如果是被外面的箱子锁定, 就对外面的箱子进行移库
            //todo 移库之前, 判断前面箱子是否空闲
            //  如果已有任务, 找出前面箱子的任务, 给它绑定事件
            if (Constance.LOCATION_STATUS_OCCUPY.equals(backLocationStatus)) {
                // 如果是占用状态, 对对应的box执行出库
                String bNumber = backLocation.getBoxNumber();
                Box backBox = boxService.getBoxByCartonNo(bNumber);

                if (Objects.nonNull(backBox)) {
                    // 校验location的管理与box的管理是否一致
                    if (!Objects.equals(backLocation.getLevel(), backBox.getLevel()) || !Objects.equals(backLocation.getLocation(), backBox.getLocation())) {
                        log.info("location与box管理不匹配, 请联系管理员编辑修复, location: {}, box: {}", backLocation, backBox);
                        return "该箱暂无法执行质检/盘点";
                    } else {
                        Integer level = backBox.getLevel();
                        if (level <= 0) {
                            log.info("box不在库内, 无法移库, box: {}, level: {}, location: {}", backBox.getCartonNo(), backBox.getLevel(), backBox.getLocation());
                            return "box不在库内, 无法移库";
                        }

                        LocalDateTime now = LocalDateTime.now();

                        // 创建主任务
                        MainTask mainTask = new MainTask(null, Constance.MAIN_TASK_TYPE_MOVE, "移库"
                                , backBox.getOrderNo(), backBox.getPartNo(), backBox.getCartonNo()
                                , backBox.getLevel(), backBox.getLocation(), null, null, null, null
                                , Constance.TASK_STATUS_INIT, now, null, null);
                        mainTaskService.createAndGiveUp(mainTask, Boolean.TRUE);
                        log.info("创建移库主任务, mainTask id: {}, sLevel: {}, sLocation: {}, eLevel: {}, eLocation: {}, createUser: {}"
                                , mainTask.getId(), mainTask.getsLevel(), mainTask.getsLocation(), mainTask.geteLevel(), mainTask.geteLocation(), mainTask.getName());

                        // 创建子任务
                        Long seq = messageService.getSeq(Constance.KEY_OES_SEQ);
                        outboundTask = new Task(null, mainTask.getId(), seq, Constance.TASK_TYPE_OUTBOUND
                                , mainTask.getPartNo(), mainTask.getCartonNo()
                                , mainTask.getsLevel(), mainTask.getsLocation(), Constance.LIB_OUTBOUND_LEVEL, Constance.LIB_OUTBOUND_LOCATION, null, null
                                , Constance.TASK_STATUS_INIT, now, null);
                        taskService.createAndFinish(outboundTask, Boolean.TRUE);
                        log.info("创建子任务, id: {}, seq: {}", outboundTask.getId(), outboundTask.getSeq());

                        outboundSend = createOutbound(backBox, outboundTask);
                    }
                } else {
                    log.info("level: {}, location: {} 状态为占用, 但是查无此box: {}"
                            , backLocation.getLevel(), backLocation.getLocation(), backLocation.getBoxNumber());
                    return "料箱管理异常";
                }

            } else if (Constance.LOCATION_STATUS_OCCUPY_ADVANCE.equals(backLocationStatus)) {
                log.info("外侧货位level: {}, location: {}预占用, 异步等待质检/盘点执行", backLocation.getLevel(), backLocation.getLocation());
                AsyncCheck asyncCheck = new AsyncCheck(request);
                ThreadUtil.submit(asyncCheck);
                return Constance.SUCCESS;

            } else /*if (Constance.LOCATION_STATUS_OCCUPY_UNKNOWN.equals(locationStatus))*/ {
                log.info("外侧level: {}, location: {}, 状态异常 status: {}, 不允许对 {}执行质检/盘点"
                        , backLocation.getLevel(), backLocation.getLocation(), backLocation.getStatus(), request.getCartonNo());
                return "外侧货位状态异常, 不允许质检/盘点";
            }
        }

        // 创建主任务
        LocalDateTime now = LocalDateTime.now();
        // 如果有前置的移库任务, 那么该任务的状态就是暂停
        int status = Objects.isNull(outboundTask) ? Constance.TASK_STATUS_INIT : Constance.TASK_STATUS_PAUSE;
        MainTask mainTask = new MainTask(null, Constance.MAIN_TASK_TYPE_QUALITY, request.getDescription()
                , box.getOrderNo(), box.getPartNo(), box.getCartonNo()
                , box.getLevel(), box.getLocation(), Constance.LINE_LEVEL, Constance.LINE_CHECK, null, null
                , status, now, null, null);
        mainTask.setName(request.getCreateUser());
        mainTaskService.createAndGiveUp(mainTask, Boolean.TRUE);
        log.info("创建{}主任务, mainTask id: {}, sLevel: {}, sLocation: {}, eLevel: {}, eLocation: {}, createUser: {}"
                , mainTask.getDescription(), mainTask.getId(), mainTask.getsLevel(), mainTask.getsLocation(), mainTask.geteLevel(), mainTask.geteLocation(), mainTask.getName());

        // 创建子任务
        Long seq = messageService.getSeq(Constance.KEY_OES_SEQ);
        Task task = new Task(null, mainTask.getId(), seq, Constance.TASK_TYPE_OUTBOUND
                , mainTask.getPartNo(), mainTask.getCartonNo()
                , mainTask.getsLevel(), mainTask.getsLocation(), Constance.LIB_OUTBOUND_LEVEL, Constance.LIB_OUTBOUND_LOCATION, null, null
                , status, now, null);
        taskService.createAndFinish(task, Boolean.TRUE);
        log.info("创建子任务, id: {}, seq: {}", task.getId(), task.getSeq());

        // 创建出库任务
        WcsSend wcsSend = createOutbound(box, task);

        // 根据是否有前置的移库任务判断是否直接下发出库给gis
        if (Objects.isNull(outboundTask)) {
            // 没有前置的移库任务, 直接下发出库即可
            messageService.rightPush(Constance.KEY_WCS_TASK, wcsSend);
        } else {
            // 建立任务维护的时候, 增加 remark字段, 任务 完成/异常 时触发对应操作
            // 在下发该任务之前, 有一个前置的移库任务
            // 前置移库任务取货完成的时候, 推送该出库任务给gis
            // TaskEvent finishEvent = new TaskEvent(TaskEvent.EVENT_FINISH, TaskEvent.WHERE_WCS_TASK, TaskEvent.TYPE_INSERT, wcsSend);
            TaskEvent startEvent = new TaskEvent(TaskEvent.EVENT_START, TaskEvent.WHERE_WCS_TASK, TaskEvent.TYPE_INSERT, wcsSend);

            // 移库完成时, 外面箱子出立库完成时, 解锁该box
            // OesUtil.addEvent(outboundTask, finishEvent);
            TaskManager.addEvent(outboundTask, startEvent);
            // log.info("给task添加完成事件, task id: {}, event: {}", outboundTask.getId(), finishEvent);
            log.info("给task添加开始事件, task id: {}, event: {}", outboundTask.getId(), startEvent);

            // 异常事件, 移库任务的出库子任务异常时, 同时关闭该质检/盘点任务
            TaskEvent errorEvent = new TaskEvent(TaskEvent.EVENT_ERROR_CLOSE, TaskEvent.WHERE_OES_TASK, TaskEvent.TYPE_CLOSE, task);
            TaskManager.addEvent(outboundTask, errorEvent);
            log.info("给task添加异常事件, task id: {}, event: {}", outboundTask.getId(), errorEvent);

            taskService.updateByPrimaryKeySelective(outboundTask);
            messageService.rightPush(Constance.KEY_WCS_TASK, outboundSend);
        }

        return Constance.SUCCESS;
    }

    private WcsSend createOutbound(Box box, Task task) {
        // 更新box
        Box bx = new Box();
        bx.setId(box.getId());
        bx.setPlcSeq(task.getSeq().intValue());
        bx.setPlcTaskId(task.getPid());
        boxService.updateByPrimaryKeySelective(bx);

        // 创建发送给立库的消息
        WcsSend wcsSend = new WcsSend();
        wcsSend.setId(task.getId());
        wcsSend.setMessageName("appointStockOut");
        wcsSend.setBoxId(task.getCartonNo());
        wcsSend.setSLevel(task.getsLevel());
        /*if (boxService.isLength(box)) {
            // 判断箱子是否是长箱, 如果是长箱, 给立库下达出库的时候, location编码 + 6
            log.debug("出库的料箱是长箱, cartonNo: {}, length: {}", box.getCartonNo(), box.getLength());
            wcsSend.setSLocation(task.getsLocation() + 6);
        } else {
            wcsSend.setSLocation(task.getsLocation());
        }*/
        wcsSend.setSLocation(task.getsLocation());
        wcsSend.setWmsId(task.getSeq());
        HashMap<Object, Object> data = new HashMap<>(2);
        data.put("outbound", configManager.getNumber(Config.KEY_LIB_OUTBOUND_ID, 0));
        wcsSend.setData(data);
        return wcsSend;
    }

    /**
     * 阻塞式发送与接收
     */
    @Transactional(rollbackFor = Exception.class)
    @Scheduled(initialDelayString = ScheduleConfig.SCHEDULE_DELAY_INIT, fixedDelayString = ScheduleConfig.SCHEDULE_DELAY_SHORT)
    public void sendTask() {
        // WcsSend wcsSend = messageService.leftPop(Constance.KEY_WCS_TASK, WcsSend.class);
        WcsSend wcsSend = messageService.leftGet(Constance.KEY_WCS_TASK, WcsSend.class);
        if (Objects.isNull(wcsSend)) {
            // log.debug("暂无任务发送给wcs...");
            return;
        }
        Long seq = wcsSend.getWmsId();
        Task task = taskService.getBySeq(seq);
        if (Objects.isNull(task) || !Constance.TASK_STATUS_RUNNING_LIST.contains(task.getStatus())) {
            messageService.leftPop(Constance.KEY_WCS_TASK, WcsSend.class);
            log.warn("任务已删除, 或已取消/完成, 不再继续发送, task id: {}, seq: {}", wcsSend.getId(), wcsSend.getWmsId());
            return;
        }

        String send = null;
        String receive = null;
        try {
            send = JSON.toJSONString(wcsSend);
            client.send(send);
            log.info("向远端server发送消息, id: {}, messageName: {}, message: {}"
                    , wcsSend.getId(), wcsSend.getMessageName(), send);

            receive = client.receive();
            if (Objects.isNull(receive)) {
                log.warn("远端server发来null, message: {}", receive);
                return;
            }

            log.info("接收到远端server的反馈, message: {}", receive);

            WcsResult wcsResult = JSON.parseObject(receive, WcsResult.class);
            if (!Objects.equals(wcsSend.getId(), wcsResult.getId())) {
                // 回复的id不一致, 则把wcsSend放回队列首部, 让下次继续发送
                log.warn("下位系统回复的消息id不匹配, send id: {}, result id: {}"
                        , wcsResult.getId(), wcsResult.getId());
                return;
            }

            // 删除该值避免下次继续发送
            messageService.leftPop(Constance.KEY_WCS_TASK, WcsSend.class);
            // 一切正常, 处理业务
            String messageName = wcsResult.getMessageName();

            switch (messageName) {
                case "appointStockOut":
                    appointStockOut(wcsResult, task);
                    break;
                case "levelTransport":
                    levelTransport(wcsResult, task);
                    break;
                default:
                    log.warn("未知的消息类型, messageName: {}, message: {}", messageName, wcsResult);
            }

        } catch (JSONException e) {
            log.error("JSON解析异常, receive: {}, send: {}, wcsSend: {}", receive, send, wcsSend, e);
        } catch (SocketTimeoutException e) {
            log.info("从远端server读取数据超时!");
        } catch (SocketException se) {
            log.error("和远端server连接异常: {}", se.getMessage());
            client.inited = false;
        } catch (IOException e) {
            log.error("发送信息到远端server异常: {}", e.getMessage(), e);
            client.inited = false;
        }
    }

    private void levelTransport(WcsResult wcsResult, Task task) {
        commonDeal(wcsResult, task);
    }

    private void appointStockOut(WcsResult wcsResult, Task task) {
        commonDeal(wcsResult, task);
    }

    private void commonDeal(WcsResult wcsResult, Task task) {
        String result = wcsResult.getResult().toString();
        switch (result) {
            case "0":
                log.info("立库反馈{}任务创建成功", wcsResult.getMessageName());
                break;
            case "2":
                log.info("立库反馈{}任务已在执行中", wcsResult.getMessageName());
                break;
            case "1":
                log.info("立库反馈{}异常, 货位不存在, 请检查ga_locations配置, cartonNo: {}, result: {}", wcsResult.getMessageName(), task.getCartonNo(), result);
                /*// 货位不存在, 更新box的nc并备注
                String cartonNo = task.getCartonNo();
                Box box = boxService.getBoxByCartonNo(cartonNo);
                if (Objects.nonNull(box)) {
                    String remark = box.getRemark();
                    Box bx = new Box();
                    bx.setCartonNo(cartonNo);
                    bx.setNc(1);
                    bx.setRemark(Objects.isNull(remark) ? "立库反馈该箱出库无货" : "立库反馈该箱出库无货" + System.lineSeparator() + remark);
                    boxService.updateByCartonNo(bx);
                    log.info("更新box为nc, 并修改nc remark, cartonNo: {}, remark: {}", bx.getCartonNo(), bx.getRemark());
                }*/
                // 异常关闭任务
                taskService.errorCloseTask(task, Boolean.TRUE);
                break;
            case "10": {
                log.info("立库反馈{}异常, result: {}", wcsResult.getMessageName(), result);
                // 取消主任务, 子任务, 重新计算料箱出KT
                taskService.errorCloseTask(task, Boolean.TRUE);
                break;
            }
            default:
                log.info("未知的结果反馈, result: {}", result);
        }
    }

}
