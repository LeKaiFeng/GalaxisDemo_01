package com.galaxis.wcs.yanfeng.device.library;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.galaxis.wcs.yanfeng.database.oes.domain.Box;
import com.galaxis.wcs.yanfeng.database.oes.domain.Location;
import com.galaxis.wcs.yanfeng.database.oes.domain.MainTask;
import com.galaxis.wcs.yanfeng.database.oes.domain.Task;
import com.galaxis.wcs.yanfeng.database.oes.service.*;
import com.galaxis.wcs.yanfeng.device.library.service.InboundService;
import com.galaxis.wcs.yanfeng.exception.OesException;
import com.galaxis.wcs.yanfeng.util.Config;
import com.galaxis.wcs.yanfeng.util.Constance;
import com.galaxis.wcs.yanfeng.util.WcsMapping;
import com.galaxis.wcs.yanfeng.util.template.WcsResult;
import com.galaxis.wcs.yanfeng.work.manager.ConfigManager;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
@Scope("prototype")
@ChannelHandler.Sharable
public class YanFengInboundHandler extends SimpleChannelInboundHandler<String> {

    private static final Logger log = LoggerFactory.getLogger(YanFengInboundHandler.class);

    @Autowired
    private ConfigManager configManager;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private InboundService inboundService;
    @Autowired
    private BoxService boxService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private MainTaskService mainTaskService;
    @Autowired
    private KtService ktService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private WarehousingService warehousingService;
    @Autowired
    private OroderService oroderService;

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = OesException.class)
    protected void channelRead0(ChannelHandlerContext context, String message) throws Exception {
        JSONObject receive;
        try {
            receive = JSON.parseObject(message);
        } catch (JSONException e) {
            log.error("json解析异常, message: {}, e: {}", message, e.getMessage(), e);
            return;
        }
        String messageName = receive.getString("messageName");
        Integer id = receive.getInteger("id");
        log.info("收到远端client发来的消息, id: {}, name: {}, message: {}"
                , id, messageName, message);
        WcsResult cacheResult = (WcsResult) redisTemplate.opsForValue().get(Constance.KEY_PRE_CACHE_WCS_REQUEST + id);
        if (Objects.nonNull(cacheResult)) {
            log.warn("远端client发来的消息重复, id: {}, msg: {}", id, message);
            send(context, cacheResult);
            return;
        }

        switch (messageName) {
            case "appointBoxAnnounce":
                appointBoxAnnounce(context, receive);
                break;
            case "taskAdjust":
                taskAdjust(context, receive);
                break;
            case "updateMovement":
                updateMovement(context, receive);
                break;
            default:
                // 发送反馈, 避免对方长时间阻塞
                WcsResult wcsResult = new WcsResult();
                wcsResult.setId(receive.getInteger("id"));
                wcsResult.setMessageName(messageName);
                wcsResult.setResult(null);
                send(context, wcsResult);
                log.warn("未知的消息类型, messageName: {}, message: {}", messageName, message);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.warn("处理{}的消息出现了异常, {}", ctx.channel().remoteAddress(), cause.getMessage(), cause);
    }

    private void updateMovement(ChannelHandlerContext context, JSONObject receive) {
        // 组装反馈
        WcsResult wcsResult = new WcsResult();
        wcsResult.setId(receive.getInteger("id"));
        wcsResult.setMessageName(receive.getString("messageName"));
        wcsResult.setResult(0);
        send(context, wcsResult);

        // 判断state 0,为创建，1为分配，2为执行中，3为完成，4为取消，12为因空取货而取消
        int state = receive.getIntValue("state");
        Long wmsId = receive.getLong("wmsId");
        switch (state) {
            case 1: {
                log.info("子任务task对应的立库任务已分配, seq: {}", wmsId);
                Task task = taskService.getBySeq(wmsId);
                if (Objects.isNull(task)) {
                    log.info("立库反馈的seq没有对应的任务, seq: {}, location: {}", wmsId, receive.getJSONObject("location"));
                    return;
                }
                if (Arrays.asList(Constance.TASK_TYPE_INBOUND).contains(task.getType())) {
                    taskService.start(task);
                }
                break;
            }
            case 6: {
                log.info("子任务task对应的立库任务中小车已取货, seq: {}", wmsId);
                Task task = taskService.getBySeq(wmsId);
                if (Objects.isNull(task)) {
                    log.info("立库反馈的seq没有对应的任务, seq: {}, location: {}", wmsId, receive.getJSONObject("location"));
                    return;
                }

                taskService.pickup(task);
                break;
            }
            case 3: {
                log.info("子任务task对应的立库任务已完成, seq: {}", wmsId);
                Task task = taskService.getBySeq(wmsId);
                if (Objects.isNull(task)) {
                    log.info("立库反馈的seq没有对应的任务, seq: {}", wmsId);
                    return;
                }

                // 检查任务类型为入库且eLocation是否是1
                Integer eLocation = receive.getJSONObject("location").getInteger("e_location");
                if (Constance.TASK_TYPE_INBOUND.equals(task.getType()) && Constance.LIB_LOCATION_KICKOUT.equals(eLocation)) {
                    // 入库的eLocation是1说明是踢出任务, 直接异常关闭该task即可
                    log.info("task被执行为踢出, 关闭该task, task pid: {}, seq: {}, eLevel: {}, eLocation: {}"
                            , task.getPid(), task.getSeq(), task.geteLevel(), task.geteLocation());
                    taskService.errorCloseTask(task, Boolean.TRUE);
                    return;
                }

                taskService.finish(task);
                break;
            }
            case 4: {
                log.info("子任务task对应的立库任务已取消, seq: {}", wmsId);
                Task task = taskService.getBySeq(wmsId);
                if (Objects.isNull(task)) {
                    log.info("立库反馈的seq没有对应的任务, seq: {}", wmsId);
                    return;
                }
                if (!Constance.TASK_STATUS_RUNNING_LIST.contains(task.getStatus())) {
                    log.info("对应子任务不是运行状态, id: {}, seq: {}, status: {}"
                            , task.getId(), task.getSeq(), task.getStatus());
                    return;
                }
                taskService.errorCloseTask(task, Boolean.TRUE);
                break;
            }
            case 12: {
                log.info("子任务task对应的立库任务因空取货而取消, seq: {}", wmsId);
                Task task = taskService.getBySeq(wmsId);
                if (Objects.isNull(task)) {
                    log.info("立库反馈的seq没有对应的任务, seq: {}", wmsId);
                    return;
                }
                if (!Constance.TASK_STATUS_RUNNING_LIST.contains(task.getStatus())) {
                    log.info("对应子任务不是运行状态, id: {}, seq: {}, status: {}"
                            , task.getId(), task.getSeq(), task.getStatus());
                    return;
                }
                // 空取货而取消, 设置box为nc
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
                }
                taskService.errorCloseTask(task, Boolean.TRUE);
                break;
            }
            case 0:
                log.info("子任务task对应的立库任务已创建, seq: {}", wmsId);
                break;
            case 2:
                log.info("子任务task对应的立库任务正在执行中, seq: {}", wmsId);
                break;
            default:
                log.info("未知的状态, seq: {}, state: {}", wmsId, state);
        }

    }

    private void taskAdjust(ChannelHandlerContext context, JSONObject receive) {
        String boxNumber = receive.getString("boxId");
        // 组装响应
        WcsResult wcsResult = new WcsResult();
        wcsResult.setId(receive.getInteger("id"));
        wcsResult.setMessageName(receive.getString("messageName"));
        // 任务调整
        Long wmsId = receive.getLong("wmsId");
        List<Integer> allowLevel = receive.getJSONArray("allowLevel").toJavaList(Integer.class);

        Box box = boxService.getBoxByCartonNo(boxNumber);
        Boolean boxIsLength = boxService.isLength(box);

        // 组装响应的result
        Map<String, Object> reResult = new HashMap<>(8);
        Integer rLevel;
        Integer rLocation;

        Location location = null;
        if (Objects.nonNull(box)) {
            location = inboundService.computeLocation(boxNumber, allowLevel);
            if (Objects.isNull(location)) {
                log.warn("没有合适的库位可入, 做踢出处理, cartonNo: {}", boxNumber);
                rLevel = locationService.listFreeLevel().get(0);
                rLocation = Constance.LIB_LOCATION_KICKOUT;
            } else {
                rLevel = location.getLevel();
                rLocation = location.getLocation();
                log.info("计算出终点, cartonNo: {}, level: {}, location: {}", boxNumber, location.getLevel(), location.getLocation());

                // 更新货位为预占用
                locationService.updateStatus(location.getLevel(), location.getLocation(), Constance.LOCATION_STATUS_OCCUPY_ADVANCE, boxNumber, boxIsLength);
            }
        } else {
            log.warn("系统里没有这个box的信息, 做踢出处理, cartonNo: {}", boxNumber);
            rLevel = locationService.listFreeLevel().get(0);
            rLocation = Constance.LIB_LOCATION_KICKOUT;
        }

        reResult.put("level", rLevel);
        if (Config.VALUE_MODE_LOCATION_DOUBLE.equalsIgnoreCase(configManager.getConfigure(Config.KEY_MODE_LOCATION))) {
            // 双深货位, 长箱 放12, 取78
            reResult.put("location", boxIsLength ? rLocation - 6 : rLocation);
        } else {
            // 普通货位, 只会存在12, 长箱用大行程, 所以+6, 长箱会占用对面货位
            reResult.put("location", boxIsLength ? rLocation + 6 : rLocation);
        }
        reResult.put("boxId", boxNumber);

        wcsResult.setResult(reResult);
        // 发送响应
        send(context, wcsResult);

        if (Objects.isNull(box)) {
            return;
        }

        Task task = taskService.getBySeq(wmsId);
        // 更新原终点为未知占用
        locationService.updateStatus(task.geteLevel(), task.geteLocation(), Constance.LOCATION_STATUS_OCCUPY_UNKNOWN, boxNumber, boxIsLength);

        // 修改当前任务的r.*
        Integer id = task.getId();
        Task tsk = new Task();
        tsk.setId(id);
        tsk.seteLevel(rLevel);
        tsk.seteLocation(rLocation);
        taskService.updateByPrimaryKeySelective(tsk);
        log.info("入库调整, 更新当前子任务, id: {}, eLevel: {}, eLocation: {}, 原task: {}"
                , tsk.getId(), tsk.getrLevel(), tsk.getrLocation(), task);

        // 更新主任务
        MainTask mainTask = new MainTask();
        mainTask.setId(task.getPid());
        mainTask.setrLevel(location.getLevel());
        mainTask.setrLocation(location.getLocation());
        mainTaskService.updateByPrimaryKeySelective(mainTask);
        log.info("入库调整, 更新主任务, id: {}, rLevel: {}, rLocation: {}"
                , mainTask.getId(), mainTask.getrLevel(), mainTask.getrLocation());
    }

    private void appointBoxAnnounce(ChannelHandlerContext context, JSONObject receive) {
        String boxNumber = receive.getString("boxId");
        Box box = boxService.getBoxByCartonNo(boxNumber);
        boolean boxIsNull = Objects.isNull(box);
        boolean boxIsLength = false;
        if (!boxIsNull) {
            boxIsLength = boxService.isLength(box);
        }

        // 清除该box的历史占用记录
        boxService.clearOccupy(box);

        // 组装响应
        WcsResult wcsResult = new WcsResult();
        wcsResult.setId(receive.getInteger("id"));
        wcsResult.setMessageName(receive.getString("messageName"));
        // 入库请求
        Long seq = messageService.getSeq(Constance.KEY_OES_SEQ);

        // 组装响应的result
        Map<String, Object> result = new HashMap<>();
        wcsResult.setResult(result);

        result.put("boxId", boxNumber);
        result.put("wmsId", seq);
        result.put("weight", boxIsNull ? 0 : box.getWeight());

        // 查询状态为正在进行的的主任务即可
        List<MainTask> mainTasks = mainTaskService.queryByCartonNo(boxNumber
                , Constance.TASK_STATUS_RUNNING_LIST
                , Boolean.TRUE);
        if (mainTasks.size() == 0) {
            log.warn("该箱子[{}]以前未维护过主任务, 做踢出处理, 请通过正常途径入库", boxNumber);
            result.put("level", locationService.listFreeLevel().get(0));
            result.put("location", boxIsLength ? Constance.LIB_LOCATION_KICKOUT + 6 : Constance.LIB_LOCATION_KICKOUT);
            // 发送响应
            send(context, wcsResult);
            return;
        }

        MainTask mainTask = mainTasks.get(0);
        // 子任务(正常响应)的level, location
        Integer tLevel;
        Integer tLocation;
        if (Objects.nonNull(mainTask.getKtId())) {
            // 入KT的, 直接取mainTask的r.*
            tLevel = mainTask.getrLevel();
            tLocation = mainTask.getrLocation();
            if (Objects.isNull(tLevel)) {
                tLevel = mainTask.geteLevel();
                tLocation = mainTask.geteLocation();
            }
            log.info("该箱直接入KT, level: {}, location: {}", tLevel, tLocation);
        } else if (!Arrays.asList(Constance.MAIN_TASK_TYPE_TO_LIB, Constance.MAIN_TASK_TYPE_BACK_LIB, Constance.MAIN_TASK_TYPE_KT, Constance.MAIN_TASK_TYPE_MOVE).contains(mainTask.getType())) {
            log.info("主任务类型为 {}的box出现在了入库口, 执行踢出处理, mainTask id: {}, cartonNo: {}", mainTask.getType(), mainTask.getId(), mainTask.getCartonNo());
            tLevel = locationService.listFreeLevel().get(0);
            tLocation = Constance.LIB_LOCATION_KICKOUT;
        } else {
            // 不入KT的, 根据箱号计算出应该去的层和货位
            Location location = inboundService.computeLocation(boxNumber, null);
            if (Objects.isNull(location)) {
                log.error("没有合适的库位可入, 做踢出处理, cartonNo: {}", boxNumber);
                tLevel = locationService.listFreeLevel().get(0);
                tLocation = Constance.LIB_LOCATION_KICKOUT;
            } else {
                tLevel = location.getLevel();
                tLocation = location.getLocation();
                log.info("该箱子[{}]分配入库货位, level: {}, location: {}", boxNumber, location.getLevel(), location.getLocation());

                // 更新货位为预占用
                locationService.updateStatus(location.getLevel(), location.getLocation(), Constance.LOCATION_STATUS_OCCUPY_ADVANCE, boxNumber, boxIsLength);

            }

            // 不进KT, 则更新主任务的e.*
            MainTask mTask = new MainTask();
            mTask.setId(mainTask.getId());
            mTask.seteLevel(tLevel);
            mTask.seteLocation(tLocation);
            // 更新主任务状态为在途(库内)避免计算kt出库时被修改
            mTask.setStatus(Constance.TASK_STATUS_RUNNING_LIB);
            mainTaskService.updateByPrimaryKeySelective(mTask);
            log.info("更新主任务, id: {}, eLevel: {}, eLocation: {}"
                    , mTask.getId(), mTask.geteLevel(), mTask.geteLocation());
        }

        result.put("level", tLevel);
        if (Config.VALUE_MODE_LOCATION_DOUBLE.equalsIgnoreCase(configManager.getConfigure(Config.KEY_MODE_LOCATION))) {
            // 双深货位, 长箱 放12, 取78
            int ws = tLocation % 10;
            if (!boxIsLength || Arrays.asList(1, 2).contains(ws)) {
                result.put("location", tLocation);
            } else {
                result.put("location", tLocation - 6);
            }
        } else {
            // 普通货位, 只会存在12, 长箱用大行程, 所以+6, 长箱会占用对面货位
            result.put("location", boxIsLength ? tLocation + 6 : tLocation);
        }

        // 发送响应
        send(context, wcsResult);

        if (boxIsNull) {
            log.warn("box is null, box boxNumber: {}", boxNumber);
            return;
        }
        // 入库口ID
        Integer requestLocation = receive.getInteger("requestLocation");
        // 创建子任务
        Task task = new Task(null, mainTask.getId(), seq, Constance.TASK_TYPE_INBOUND
                , mainTask.getPartNo(), mainTask.getCartonNo()
                , Constance.LINE_LEVEL, Integer.parseInt(WcsMapping.mapping(WcsMapping.KEY_INBOUND_ + requestLocation).toString())
                , tLevel, tLocation, null, null
                , Constance.TASK_STATUS_INIT, LocalDateTime.now(), null);
        // 生成子任务
        taskService.createAndFinish(task, Boolean.TRUE);
        log.info("生成入库子任务, task id: {}, pid: {}, seq: {}, cartonNo: {}, sLevel: {}, sLocation: {}, eLevel: {}, eLocation: {}",
                task.getId(), task.getPid(), task.getSeq(), task.getCartonNo(), task.getsLevel(), task.getsLocation(), task.geteLevel(), task.geteLocation());

        // 更新box
        Box bx = new Box();
        bx.setId(box.getId());
        bx.setBoxPosition(Constance.BOX_POSITION_RUNNING_LIB);
        bx.setPlcSeq(task.getSeq().intValue());
        boxService.updateByPrimaryKeySelective(bx);
        log.info("更新box, cartonNo: {}, position: {}, plcSeq: {}"
                , box.getCartonNo(), bx.getBoxPosition(), bx.getPlcSeq());
    }

    private void send(ChannelHandlerContext context, WcsResult wcsResult) {
        redisTemplate.opsForValue().set(Constance.KEY_PRE_CACHE_WCS_REQUEST + wcsResult.getId(), wcsResult
                , Constance.CACHE_TIME, TimeUnit.MILLISECONDS);

        String send = JSON.toJSONString(wcsResult);
        ChannelFuture future = context.channel().writeAndFlush(send);
        future.addListener(f -> {
            if (f.isSuccess()) {
                log.info("发送响应到远端client, id: {}, name: {}, message: {}"
                        , wcsResult.getId(), wcsResult.getMessageName(), send);
            } else {
                log.error("发送消息到远端client失败, id: {}, name: {}, message: {}"
                        , wcsResult.getId(), wcsResult.getMessageName(), send);
            }
        });
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.info("远端Client:" + ctx.channel().remoteAddress() + "连接上服务器");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        log.info("远端Client:" + ctx.channel().remoteAddress() + "断开上服务器");
    }
}
