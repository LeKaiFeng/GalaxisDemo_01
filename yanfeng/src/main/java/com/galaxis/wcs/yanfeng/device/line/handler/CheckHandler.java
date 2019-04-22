package com.galaxis.wcs.yanfeng.device.line.handler;

import com.galaxis.wcs.yanfeng.aop.PlcSeqAnnotation;
import com.galaxis.wcs.yanfeng.device.line.LineHandler;
import com.galaxis.wcs.yanfeng.device.line.service.BackLibService;
import com.galaxis.wcs.yanfeng.exception.OesLineExcetion;
import com.galaxis.wcs.yanfeng.util.Config;
import com.galaxis.wcs.yanfeng.util.Constance;
import com.galaxis.wcs.yanfeng.util.template.LinePost;
import com.galaxis.wcs.yanfeng.util.template.LineRequest;
import com.galaxis.wcs.yanfeng.util.template.LineResponse;
import com.galaxis.wcs.yanfeng.work.manager.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 抽检点
 * 主要执行回库, 非排程入库
 */
@Component
public class CheckHandler implements LineHandler {
    private static final Logger log = LoggerFactory.getLogger(CheckHandler.class);

    @Autowired
    private BackLibService backLibService;
    @Autowired
    private ConfigManager configManager;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Transactional(rollbackFor = Exception.class, noRollbackFor = OesLineExcetion.class)
    @PlcSeqAnnotation
    @Override
    public void deal(LineRequest request, LineResponse response) {
        // 获取条码
        String cartonNo = request.getCartonNo();
        Integer seq = request.getSeq();
        log.info("从抽检点工位接收到请求, seq: {}, cartonNo: {}, request: {}", seq, cartonNo, request);
        // 与前端条码比对
        cartonNo = choose(cartonNo);
        log.info("比对后cartonNo: {}", cartonNo);
        // 都没有码
        if (Constance.CODE_NULL_PLC_WEB.equals(cartonNo)) {
            // 对于都没有码的处理, 定为停线
            log.warn("plc和web都没有发送有效的条码过来! cartonNo: {}", cartonNo);
            response.setWarn(1);
            response.setErrorCode(LinePost.ERROR_CODE_NO_BARCODE_ON_CHECK);
            return;
        }

        request.setCartonNo(cartonNo);
        response.setCartonNo(cartonNo);
        // 有码, 则走回库服务
        backLibService.backToLib(request, response);
    }

    /**
     * 检查PLC的条码, 返回校正后的条码
     *
     * @param cartonNo plc传来的码
     * @return 校正后的码
     */
    public String choose(String cartonNo) {
        // 前端传来的条码
        String webCartonNo = stringRedisTemplate.opsForValue().get(Constance.KEY_WEB_BOX_BACK_LIB);
        log.debug("webCartonNo: {}", webCartonNo);
        // 读完就清掉, 避免下次重复读
        stringRedisTemplate.delete(Constance.KEY_WEB_BOX_BACK_LIB);

        int role = 0;

        if (cartonNo == null || Constance.EMPTY.equals(cartonNo) || Constance.CODE_NO_READ.equalsIgnoreCase(cartonNo)) {
            log.info("plc发来的条码为[{}]", cartonNo);
            role += 1;
        }
        if (webCartonNo == null || Constance.EMPTY.equals(webCartonNo) || Constance.CODE_NO_READ.equalsIgnoreCase(webCartonNo)) {
            log.info("web发来的条码为[{}]", webCartonNo);
            role += 2;
        }

        String code = "";
        switch (role) {
            case 0:
                // 都有码且不为null
                if (cartonNo.equals(webCartonNo)) {
                    code = cartonNo;
                } else {
                    // 都有码, 且不相同时根据数据库配置取其一
                    String priority = configManager.getConfigure(Config.KEY_CHECK_BARCODE_PRIORITY);
                    switch (priority) {
                        case Config.VALUE_CHECK_BARCODE_PRIORITY_WEB:
                            code = webCartonNo;
                            break;
                        case Config.VALUE_CHECK_BARCODE_PRIORITY_PLC:
                        default:
                            code = cartonNo;
                            break;
                    }

                }
                break;
            case 1:
                // 只有web有码
                code = webCartonNo;
                break;
            case 2:
                // 只有plc有码
                code = cartonNo;
                break;
            case 3:
                // 都没有码
            default:
                code = Constance.CODE_NULL_PLC_WEB;
                break;
        }

        return code;
    }
}
