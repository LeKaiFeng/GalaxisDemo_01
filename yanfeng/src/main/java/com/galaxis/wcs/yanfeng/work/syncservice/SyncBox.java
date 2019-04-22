package com.galaxis.wcs.yanfeng.work.syncservice;

import com.galaxis.wcs.yanfeng.config.ScheduleConfig;
import com.galaxis.wcs.yanfeng.database.oes.domain.Box;
import com.galaxis.wcs.yanfeng.database.oes.domain.BoxExample;
import com.galaxis.wcs.yanfeng.database.oes.service.BoxService;
import com.galaxis.wcs.yanfeng.database.wms.domain.ItStock;
import com.galaxis.wcs.yanfeng.database.wms.domain.ItStockExample;
import com.galaxis.wcs.yanfeng.database.wms.service.ItStockService;
import com.galaxis.wcs.yanfeng.util.Config;
import com.galaxis.wcs.yanfeng.util.Constance;
import com.galaxis.wcs.yanfeng.util.OesUtil;
import com.galaxis.wcs.yanfeng.work.manager.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class SyncBox {
    private static final Logger log = LoggerFactory.getLogger(SyncBox.class);

    @Autowired
    private ConfigManager configManager;
    @Autowired
    private BoxService boxService;
    @Autowired
    private ItStockService itStockService;

    /**
     * 自动删除出kt的box
     */
    @Scheduled(initialDelayString = ScheduleConfig.SCHEDULE_DELAY_INIT, fixedDelayString = ScheduleConfig.SCHEDULE_DELAY_MIDDLE)
    public void deleteOutKtBox() {
        String outDelete = configManager.getConfigure(Config.KEY_ENABLE_AUTO_DELETE_OUT_KT_BOX);
        if (!Config.Y.equals(outDelete)) {
            return;
        }

        BoxExample boxExample = new BoxExample();
        boxExample.createCriteria()
                .andStatusEqualTo(Constance.BOX_STATUS_OUTBOUND_KT);
        int i = boxService.deleteByExample(boxExample);
        log.debug("删除了 {}个出kt的box", i);
    }

    /**
     * 自动将box数量与视图同步
     */
    @Scheduled(initialDelayString = ScheduleConfig.SCHEDULE_DELAY_INIT, fixedDelayString = ScheduleConfig.SCHEDULE_DELAY_SYNC_BOX)
    public void syncBox() {
        if (Config.Y.equalsIgnoreCase(configManager.getConfigure(Config.KEY_ENABLE_SYNC_BOX_NUMBER))) {
            // 做同步
            log.debug("开始同步box中数量...");

            List<Box> boxes = boxService.selectByExample(new BoxExample());

            List<List<Box>> lists = OesUtil.subList(boxes, 512);
            lists.parallelStream().forEach(this::syncBox);

            log.debug("box中数量同步完成...");
        } else {
            log.debug("同步箱子中数量的服务暂未开启...");
        }

    }

    /**
     * 将传入box集合中的数量与视图同步
     * 同步时使用 in 查询, 所以数量不要过多
     *
     * @param boxes 传入的box
     */
    private void syncBox(List<Box> boxes) {
        int i = 0;

        // 箱号集合
        List<String> cartonNos = boxes.stream().map(Box::getCartonNo).collect(Collectors.toList());
        // 查询视图
        ItStockExample itStockExample = new ItStockExample();
        itStockExample.createCriteria()
                .andCartonNoIn(cartonNos);
        List<ItStock> itStocks = itStockService.selectByExample(itStockExample);
        // 箱号 -> 视图
        Map<String, ItStock> stockMap = itStocks.stream().collect(Collectors.toMap(ItStock::getCartonNo, itStock -> itStock));
        // 更新box
        for (Box box : boxes) {
            String cartonNo = box.getCartonNo();
            ItStock stock = stockMap.get(cartonNo);
            if (stock == null) {
                log.info("视图中查不到该箱号的数据, cartonNo: {}", cartonNo);
                continue;
            }

            // 比较数量和单位
            if (!(box.getQty().equals(stock.getQty()) && box.getUnit().equals(stock.getUnit()))) {
                Box update = new Box();
                update.setId(box.getId());
                update.setQty(stock.getQty());
                update.setUnit(stock.getUnit());
                boxService.updateByPrimaryKeySelective(update);
                log.info("更新box数量和单位, box id: {}, cartonNo: {}" +
                                ", old qty: {}, old unit: {}" +
                                ", new qty: {}, new unit: {}"
                        , box.getId(), box.getCartonNo(), box.getQty(), box.getUnit(), update.getQty(), update.getUnit());
                i++;
            }
        }

        log.debug("本次同步了 {}条数据", i);

    }

}
