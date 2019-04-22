package com.galaxis.wcs.yanfeng.device.line.handler;

import com.galaxis.wcs.yanfeng.aop.PlcSeqAnnotation;
import com.galaxis.wcs.yanfeng.database.oes.domain.*;
import com.galaxis.wcs.yanfeng.database.oes.service.*;
import com.galaxis.wcs.yanfeng.device.line.LineHandler;
import com.galaxis.wcs.yanfeng.exception.OesLineExcetion;
import com.galaxis.wcs.yanfeng.util.Config;
import com.galaxis.wcs.yanfeng.util.Constance;
import com.galaxis.wcs.yanfeng.util.LocationUtil;
import com.galaxis.wcs.yanfeng.util.TimeUtil;
import com.galaxis.wcs.yanfeng.util.template.LinePost;
import com.galaxis.wcs.yanfeng.util.template.LineRequest;
import com.galaxis.wcs.yanfeng.util.template.LineResponse;
import com.galaxis.wcs.yanfeng.work.manager.ConfigManager;
import com.galaxis.wcs.yanfeng.work.manager.TaskManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 称重扫码工位
 * 主要执行入库, 抽检, 抽点, 去异常口
 */
@Component
public class WeightHandler implements LineHandler {
    private static final Logger log = LoggerFactory.getLogger(WeightHandler.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ConfigManager configManager;
    @Autowired
    private MessageService messageService;
    @Autowired
    private TaskManager taskManager;
    @Autowired
    private MainTaskService mainTaskService;
    @Autowired
    private OroderService oroderService;
    @Autowired
    private ErrService errService;
    @Autowired
    private SkuInfoService skuInfoService;
    @Autowired
    private QualityService qualityService;
    @Autowired
    private RezultService rezultService;
    @Autowired
    private BoxService boxService;

    @Transactional(rollbackFor = Exception.class, noRollbackFor = OesLineExcetion.class)
    @PlcSeqAnnotation
    @Override
    public void deal(LineRequest request, LineResponse response) {
        String cartonNo = request.getCartonNo();
        Integer seq = request.getSeq();

        Integer check = request.getCheck();
        if (check == 1) {
            stringRedisTemplate.opsForValue().increment(Constance.KEY_CHECK_CACHE);
        }

        log.info("从称重扫码工位接收到请求, seq: {}, cartonNo: {}, request: {}", seq, cartonNo, request);

        // --------------------异常--------------------------
        // 如果noread直接送去异常口
        if (Constance.CODE_NO_READ.equals(cartonNo)) {
            errService.addErr(cartonNo, "无条码");
            throw new OesLineExcetion(Constance.LINE_ERROR, OesLineExcetion.CODE_NO_READ,
                    "称重扫码工位收到noread, 分配去异常口");
        }

        String prodDate = request.getProductionDate();
        if (Constance.CODE_NO_READ.equals(prodDate)) {
            // 生产日期noread
            if (!Constance.Y.equalsIgnoreCase(configManager.getConfigure(Config.KEY_ALLOW_DATE_OF_PRODUCTION_NOREAD))) {
                // 不允许生产日期noread的入库
                errService.addErr(cartonNo, "无生产日期");
                throw new OesLineExcetion(Constance.LINE_ERROR, OesLineExcetion.CODE_NO_PRODUCTION_DATE,
                        "称重扫码工位没有扫到生产日期, 分配去异常口");
            }
        }

        // 通过条码去订单表查询订单信息
        OroderExample oroderExample = new OroderExample();
        oroderExample.createCriteria().andCartonNoEqualTo(cartonNo);
        List<Oroder> oroders = oroderService.selectByExample(oroderExample);
        if (oroders.size() == 0) {
            errService.addErr(cartonNo, "无订单号");
            throw new OesLineExcetion(Constance.LINE_ERROR, OesLineExcetion.CODE_NO_ORDER,
                    "称重扫码工位收到条码 %s, 但是无对应的订单号, 分配去异常口", cartonNo);
        }

        Oroder oroder = oroders.get(0);
        // 在订单中, 判断是否收过料, 如果收过料, 不允许入
        if ("O".equalsIgnoreCase(oroder.getActflg())) {
            errService.addErr(cartonNo, "重复收货");
            throw new OesLineExcetion(Constance.LINE_ERROR, OesLineExcetion.CODE_RECEIVED,
                    "称重扫码工位收到条码 %s, 有对应的订单号 id: %s, 但是该箱已收过货", cartonNo, oroder.getId());
        }

        // 在订单中, 获取零件号, 判断是否已维护, 如果没有维护, 也送去异常口
        String partNo = oroder.getPartNo();
        SkuInfoExample skuInfoExample = new SkuInfoExample();
        skuInfoExample.createCriteria().andPartNoEqualTo(partNo);
        List<SkuInfo> skuInfos = skuInfoService.selectByExample(skuInfoExample);
        if (skuInfos.size() == 0) {
            errService.addErr(cartonNo, "未维护该零件");
            throw new OesLineExcetion(Constance.LINE_ERROR, OesLineExcetion.CODE_NO_SKU_INFO,
                    "称重扫码工位收到条码 %s, 零件号为 %s, 但是系统并未维护该零件号, 分配去异常口", cartonNo, partNo);
        }

        // 判断长度
        BigDecimal length = request.getLength();
        if (BigDecimal.ZERO.equals(length)) {
            errService.addErr(cartonNo, "长度未知");
            throw new OesLineExcetion(Constance.LINE_ERROR, OesLineExcetion.CODE_UNKNOWN_LENGTH,
                    "称重扫码工位收到条码 %s, 零件号为 %s, 但是未读到长度信息, 分配去异常口", cartonNo, partNo);
        }

        // 判断是否超重
        SkuInfo skuInfo = skuInfos.get(0);
        BigDecimal qty = oroder.getQty();
        BigDecimal weight = skuInfo.getWeight();
        BigDecimal realWeight = request.getWeight();
        String errorRangeWeight = configManager.getConfigure(Config.KEY_ERROR_RANGE_WEIGHT);
        BigDecimal range = new BigDecimal(errorRangeWeight);

        BigDecimal mulWeight = qty.multiply(weight);
        if (!check(realWeight, mulWeight, range)) {
            errService.addErr(cartonNo, "重量异常");

            throw new OesLineExcetion(Constance.LINE_ERROR, OesLineExcetion.CODE_OVER_WEIGHT,
                    "称重扫码工位收到条码 %s, 该箱重量不在允许误差范围内, 分配去异常口, " +
                            "理论重量: %s, 实际重量: %s", cartonNo, mulWeight, realWeight);
        }

        // ------------------------正常收货----------------------
        // 正常收货, 创建主任务
        MainTask mainTask = new MainTask();
        mainTask.setsLevel(Constance.LINE_LEVEL);
        mainTask.setsLocation(Constance.LINE_WEIGHT_SCANNER);
        mainTask.setStatus(Constance.TASK_STATUS_RUNNING_LINE);
        mainTask.setCreateTime(LocalDateTime.now());

        mainTask.setOrderNo(oroder.getOrderNo());
        mainTask.setPartNo(oroder.getPartNo());
        mainTask.setCartonNo(oroder.getCartonNo());

        // 判断抽检, 抽点规则
        int role = checkRole(skuInfo);
        if (role != 0) {
            // -----------------------需要抽检/抽点-----------------------------
            response.setLocation(Constance.LINE_CHECK);

            mainTask.setType(2);
            String desc;
            switch (role) {
                case 1:
                    desc = "抽检";
                    break;
                case 2:
                    desc = "抽点";
                    break;
                case 3:
                    desc = "抽检&抽点";
                    break;
                case 5:
                    // 不免检也没有抽检规则, 则停线
                    response.setWarn(1);
                    response.setErrorCode(LinePost.ERROR_CODE_NO_CHECK_RULE);
                    return;
                default:
                    desc = "去抽检/抽点, 原因未知";
                    break;
            }

            log.info("box: {}, 送去抽检点 -> {}", oroder.getCartonNo(), desc);
            mainTask.setDescription(desc);

            mainTask.seteLevel(Constance.LINE_LEVEL);
            mainTask.seteLocation(Constance.LINE_CHECK);

        } else {
            // -----------------------直接入库---------------------------------
            // 判断入库口占用情况
            List<Integer> occupy = request.getOccupy();
            int inbound = LocationUtil.getInbound(occupy);
            response.setLocation(inbound);
            log.info("box: {}, 送去入库口 {}", cartonNo, response.getLocation() % 10);

            mainTask.setType(1);
            mainTask.setDescription("入库");
        }

        // 持久化mainTask
        mainTaskService.createAndGiveUp(mainTask, Boolean.TRUE);
        log.info("生成{}主任务, maintask id: {}", mainTask.getDescription(), mainTask.getId());
        // 生成第一个子任务
        taskManager.doMainTask(mainTask, seq.longValue(), response.getLocation());
        // 持久化箱子和反馈收货
        // 开始箱子跟踪
        String actYmd = oroder.getActymd();
        String actHms = oroder.getActhms();
        LocalDateTime orderRecTime = TimeUtil.parse(actYmd, actHms);
        // 当前时间减去偏移量, 格式化为指定格式最为收货批次
        LocalDateTime now = LocalDateTime.now();
        long offsetRecLot = configManager.getNumber(Config.KEY_REC_LOT_OFFSET).longValue();
        String recLot = TimeUtil.formatDate(now.minusHours(offsetRecLot));
        // 解析出生成批次
        String prodLot;
        String productionDate = request.getProductionDate();
        log.debug("收到生产日期: {}", productionDate);
        if (Objects.nonNull(productionDate) && productionDate.length() == 8) {
            prodLot = productionDate.replaceAll("^(\\d{4})(\\d{2})(\\d{2})", "$1-$2-$3");
        } else if (Constance.CODE_NO_READ.equalsIgnoreCase(productionDate)) {
            // 生产日期noread
            prodLot = null;
        } else {
            log.info("解析生成批次异常, 生产日期: {}", productionDate);
            prodLot = null;
        }

        if (Objects.isNull(prodLot)
                && Constance.Y.equalsIgnoreCase(configManager.getConfigure(Config.KEY_ALLOW_AUTO_CREATE_PROD_LOT))) {
            // 允许自动生成生产批次
            long offsetProdLot = configManager.getNumber(Config.KEY_OFFSET_PROD_LOT, 1).longValue();
            // 向前偏移 offsetProdLot天, offsetRecLot小时
            prodLot = TimeUtil.formatDate(now.minusDays(offsetProdLot).minusHours(offsetRecLot));
            log.info("自动生成生产批次prodLot: {}", prodLot);
        }

        Box box = new Box(null, oroder.getCartonNo(), oroder.getPartNo(), oroder.getOrderNo()
                , Constance.LINE_LEVEL, Constance.LINE_WEIGHT_SCANNER, null
                , oroder.getQty(), oroder.getUnit(), request.getWeight(), request.getLength()
                , Constance.BOX_POSITION_WEIGHT, Constance.BOX_STATUS_RECEIVE, 0, null
                , request.getSeq(), mainTask.getId(), oroder.getCreateUser(), prodLot, recLot, oroder.getVendorCode()
                , orderRecTime, now, oroder.getAdvanceDate(), oroder.getAdvanceTime());

        // 避免二次收货造成重复跟踪, 在插入前先检查
        Box boxByCartonNo = boxService.getBoxByCartonNo(cartonNo);
        if (Objects.nonNull(boxByCartonNo)) {
            // 不为空, 则只更新
            box.setId(boxByCartonNo.getId());
            boxService.updateByPrimaryKey(box);
            log.warn("称重扫码工位二次收货, 更新box跟踪, box id: {}, cartonNo: {}, level: {}, location: {}\n被覆盖的旧box状态, old box: {}"
                    , box.getId(), box.getCartonNo(), box.getLevel(), box.getLocation(), boxByCartonNo);
            // 二次收货就不反馈收货信息了
            return;
        }

        boxService.insertSelective(box);
        log.info("称重扫码工位创建box跟踪, box id: {}, cartonNo: {}, level: {}, location: {}"
                , box.getId(), box.getCartonNo(), box.getLevel(), box.getLocation());

        // 反馈收货信息
        Rezult rezult = new Rezult();
        BeanUtils.copyProperties(oroder, rezult, "id");

        rezult.setActflg("N");
        rezult.setProdLot(box.getProdLot());
        rezult.setRecLot(box.getRecLot());

        rezultService.insertSelective(rezult);
        log.info("反馈收货信息, rezult id:{}", rezult.getId());
        // 收货之后更新我们的订单表ACTFLG为"O"
        Oroder odr = new Oroder();
        odr.setId(oroder.getId());
        odr.setActflg("O");
        oroderService.updateByPrimaryKeySelective(odr);
        log.info("更新订单表状态为收货, oroder id: {}", odr.getId());

    }

    /**
     * 判断是否抽检, 抽点
     * int role = 0;
     * 抽检 + 1
     * 抽点 + 2
     *
     * @param skuInfo 物料信息
     * @return 是否抽点, 抽检
     * 0: 不抽检, 不抽点
     * 1: 抽检
     * 2: 抽点
     * 3: 抽检 + 抽点
     * 4: 异常停线
     */
    private int checkRole(SkuInfo skuInfo) {
        String partNo = skuInfo.getPartNo();
        int role = 0;

        QualityExample qualityExample = new QualityExample();
        qualityExample.createCriteria().andPartNoEqualTo(partNo);
        List<Quality> qualities = qualityService.selectByExample(qualityExample);
        if (qualities.size() == 0) {
            // 不是免检, 判断是否抽检, 抽检role+1
            // 从redis取缓存的抽检数
            String s = stringRedisTemplate.opsForValue().get(Constance.KEY_CHECK_CACHE);
            if (Objects.nonNull(s)) {
                // 能取到值
                int i = Integer.parseInt(s);
                if (i > 0) {
                    // 且值大于0, 需要抽检, 同时cache -1
                    role += 1;
                    stringRedisTemplate.opsForValue().decrement(Constance.KEY_CHECK_CACHE);
                    log.debug("partNo抽检: {}, role: {}", partNo, role);
                }
            }

        } else {
            log.info("{} 免检", partNo);
        }

        // 判断是否抽点, 抽点你role+2
        Integer countTotal = skuInfo.getCountTotal();
        Integer countRule = skuInfo.getCountRule();
        BigDecimal countTimes = skuInfo.getCountTimes();
        if (Objects.isNull(countTotal) || Objects.isNull(countRule) || Objects.isNull(countTimes)) {
            // 没有抽点规则, 就不计算了
            log.info("该物料没有抽点规则, partNo: {}, 总数: {}, 抽点数量: {}, 抽点倍数: {}"
                    , partNo, countTotal, countRule, countTimes);
            return role;
        }

        int count = BigDecimal.valueOf(countTotal)
                .divide(BigDecimal.valueOf(countRule), 200, RoundingMode.HALF_UP)
                .divide(countTimes, 0, RoundingMode.HALF_UP)
                .setScale(0, BigDecimal.ROUND_HALF_UP)
                .intValue();
        Long countSeq = messageService.getSeq(Constance.KEY_PRE_SKU_COUNT + partNo);
        if (countSeq % count == 1) {
            role += 2;
            log.debug("partNo抽点: {}, role: {}, countTotal: {}, countRule: {}, countTimes: {}, countSeq: {}"
                    , partNo, role, countTotal, countRule, countTimes, countSeq);
        }

        return role;
    }

    /**
     * 判断实际值与理论值是否在误差允许范围内
     *
     * @param real   实际重量
     * @param theory 理论重量
     * @return 是否在误差允许范围内
     */
    private boolean check(BigDecimal real, BigDecimal theory, BigDecimal range) {

        // 如果是误差范围是1, 就不检查重量
        if (range.equals(BigDecimal.ONE)) {
            return true;
        }

        // 超重, 或者重量过轻
        return (real.compareTo(theory.multiply(BigDecimal.ONE.add(range))) <= 0)
                && (real.compareTo(theory.multiply(BigDecimal.ONE.subtract(range))) >= 0);
    }

}
