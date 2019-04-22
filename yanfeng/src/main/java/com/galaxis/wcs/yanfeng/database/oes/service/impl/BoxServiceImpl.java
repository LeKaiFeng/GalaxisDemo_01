package com.galaxis.wcs.yanfeng.database.oes.service.impl;

import com.galaxis.wcs.yanfeng.database.oes.domain.*;
import com.galaxis.wcs.yanfeng.database.oes.mapper.BoxMapper;
import com.galaxis.wcs.yanfeng.database.oes.mapper.MyBatisBaseDao;
import com.galaxis.wcs.yanfeng.database.oes.service.BoxService;
import com.galaxis.wcs.yanfeng.database.oes.service.LocationService;
import com.galaxis.wcs.yanfeng.util.Config;
import com.galaxis.wcs.yanfeng.util.Constance;
import com.galaxis.wcs.yanfeng.work.manager.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class BoxServiceImpl extends AbstractServiceImpl<Box, Integer, BoxExample> implements BoxService {
    private static final Logger log = LoggerFactory.getLogger(BoxServiceImpl.class);

    @Autowired
    private BoxMapper boxMapper;
    @Autowired
    private LocationService locationService;
    @Autowired
    private ConfigManager configManager;

    @Override
    protected MyBatisBaseDao getMapper() {
        return boxMapper;
    }

    @Override
    public Boolean isLength(Box box) {
        if (Objects.isNull(box)) {
            return Boolean.FALSE;
        }

        BigDecimal length = box.getLength();
        if (Objects.isNull(length)) {
            return Boolean.FALSE;
        }
        int i = length.intValue();
        return i >= Constance.LEN_BOX;
    }

    @Override
    public int cleanTask(String cartonNo) {
        // 更新掉box的状态
        Map<String, Object> params = new HashMap<>(4);
        params.put("plc_seq", null);
        params.put("plc_task_id", null);
        return boxMapper.updateByCartonNoOnMap(cartonNo, params);
    }

    @Override
    public Box getBoxByCartonNo(String cartonNo) {
        Box box = null;
        BoxExample boxExample = new BoxExample();
        boxExample.createCriteria().andCartonNoEqualTo(cartonNo);
        List<Box> boxes = this.selectByExample(boxExample);
        if (boxes.size() > 0) {
            box = boxes.get(0);
        }

        return box;
    }

    @Override
    public int updateByCartonNo(Box box) {
        BoxExample boxExample = new BoxExample();
        boxExample.createCriteria().andCartonNoEqualTo(box.getCartonNo());
        return this.updateByExampleSelective(box, boxExample);
    }

    @Override
    public String getOldestRecLot(String partNo) {
        BoxExample boxExample = new BoxExample();
        boxExample.createCriteria()
                .andPartNoEqualTo(partNo)
                .andStatusEqualTo(90);
        boxExample.setOrderByClause("rec_lot");
        boxExample.setLimit(1);

        List<Box> boxes = selectByExample(boxExample);
        if (boxes.size() != 0) {
            return boxes.get(0).getRecLot();
        }
        return null;
    }

    @Override
    public Map<Integer, Integer> mapLevelCountByPartNo(String partNo) {
        List<Box> boxes = boxMapper.selectLevelCountByPartNo(partNo);
        return boxes.stream()
                .filter(box -> box.getLevel() > 0)
                .collect(Collectors.toMap(Box::getLevel, Box::getLocation));
    }

    @Override
    public Box getBoxToKt(String partNo, Integer level, String... cartonNos) {
        String oldestRecLot = getOldestRecLot(partNo);
        // 查询location中, 状态为占用的, 物料种类相同, 批次大于最老(避免最老批次的nc或其他状态), 优先同层的, 作为出KT的box
        // List<Box> boxes = boxMapper.selectBoxWithLocation(Collections.singletonList(1), partNo, oldestRecLot, level);

        // 查询box中, 在立库的, 物料种类相同的, 批次大于最老(避免最老批次的nc或其他状态), 优先同层的, 作为出KT的box
        BoxExample boxExample = new BoxExample();
        BoxExample.Criteria criteria = boxExample.createCriteria()
                .andLevelGreaterThan(0)
                .andPartNoEqualTo(partNo)
                .andPlcSeqIsNull()
                .andPlcTaskIdIsNull()
                .andNcEqualTo(0)
                // 还需要通过box的状态进行过滤
                .andStatusNotEqualTo(Constance.BOX_STATUS_OUTBOUND_KT);
        if (Objects.nonNull(oldestRecLot)) {
            criteria.andRecLotGreaterThanOrEqualTo(oldestRecLot);
        }

        boxExample.setOrderByClause("rec_lot, abs(level - " + level + "), real_rec_time");
        List<Box> boxes = boxMapper.selectByExample(boxExample);

        // 过滤掉nc的箱子, 过滤掉存在于排除列表的, 过滤掉有任务的
        List<String> ctn;
        if (Objects.nonNull(cartonNos)) {
            ctn = Arrays.asList(cartonNos);
        } else {
            ctn = Collections.emptyList();
        }
        Predicate<Box> predicate = box -> !ctn.contains(box.getCartonNo());

        for (Box box : boxes) {
            if (predicate.test(box)) {
                return box;
            }
        }
        return null;
    }

    @Override
    public void clearOccupy(Box box) {
        /*// 解锁被该box锁定的7/8box
        unlock78(box.getLevel(), box.getLocation(), box.getId());
        // 清除location, 要求是被该box占用的
        LocationKey locationKey = new LocationKey(box.getLevel(), box.getLocation());
        Location location = locationService.selectByPrimaryKey(locationKey);
        if (Objects.nonNull(location)
                && !Objects.equals(Constance.LOCATION_STATUS_FREE, location.getStatus())
                && Objects.equals(box.getCartonNo(), location.getBoxNumber())) {
            // 不空闲, 且被该box占用, 则清除占用
            int i = locationService.updateStatus(box.getLevel(), box.getLocation()
                    , Constance.LOCATION_STATUS_FREE, null, isLength(box));
            log.info("清除该box原占用的货位, number: {}, location: {}", i, location);
        }*/

        if (Objects.isNull(box)) {
            return;
        }

        // 查出被该box占用的location
        LocationExample locationExample = new LocationExample();
        locationExample.createCriteria()
                .andBoxNumberEqualTo(box.getCartonNo())
                .andStatusNotEqualTo(Constance.LOCATION_STATUS_FREE);
        List<Location> locations = locationService.selectByExample(locationExample);
        if (locations.isEmpty()) {
            return;
        }

        log.info("将清除以下location的占用, locations: {}", locations);
        Boolean isLength = isLength(box);
        locations.forEach(l -> {
            // 清除该location的占用
            Integer level = l.getLevel();
            Integer location = l.getLocation();
            locationService.updateStatus(level, location, Constance.LOCATION_STATUS_FREE, null, isLength);
            log.info("已设置 level: {}, location: {}为空闲", level, location);
            // 解锁被该box锁定的7/8
            unlock78(level, location, box.getId());
        });

    }

    @Override
    public void unlock78(Integer level, Integer location, Integer... boxIds) {
        if (!Objects.equals(Config.VALUE_MODE_LOCATION_DOUBLE, configManager.getConfigure(Config.KEY_MODE_LOCATION))) {
            return;
        }

        int ws = location % 10;
        if (level > 0 && Arrays.asList(1, 2).contains(ws)) {
            Integer unlockLocation = locationService.getBackLocation(location);
            log.info("将解锁被boxIds: {} 锁定的box, level: {}, location: {}", boxIds, level, unlockLocation);
            BoxExample boxExample = new BoxExample();
            BoxExample.Criteria criteria = boxExample.createCriteria()
                    .andPlcTaskIdEqualTo(Constance.BOX_TASK_LOCK)
                    .andLevelEqualTo(level)
                    .andLocationEqualTo(unlockLocation);
            // 如果传入了boxIds, 则只能解锁被这些ids锁定的7/8box
            if (Objects.nonNull(boxIds) && boxIds.length != 0) {
                criteria.andPlcSeqIn(Arrays.asList(boxIds));
            }

            List<Box> boxes = selectByExample(boxExample);
            int size = boxes.size();
            // 1: 正常, 0或多个: 1,2的背后没有箱子或者有多个箱子(正常情况下不会出现一个货位多个箱子)
            if (size == 0) {
                log.info("level: {}, location: {}处没有被锁定的box...", level, unlockLocation);
            } else {
                // 解锁78货位的箱子
                if (size > 1) {
                    log.warn("level: {}, location: {}处有多个被锁定的box: {}..."
                            , level, unlockLocation, boxes.stream().map(Box::getCartonNo).collect(Collectors.toList()));
                }
                boxes.forEach(b -> {
                    cleanTask(b.getCartonNo());
                    log.info("解锁box id: {}, cartonNo: {}, level: {}, location: {}"
                            , b.getId(), b.getCartonNo(), b.getLevel(), b.getLocation());
                });
            }
        }
    }

    @Override
    public List<Box> listFreeBox(Box box, Integer level, List<Integer> locationEndWith) {
        return boxMapper.selectFreeBox(box.getPartNo(), box.getRecLot(), level, locationEndWith);
    }

    @Override
    public Box getBoxByLevelLocation(Integer level, Integer location) {
        BoxExample boxExample = new BoxExample();
        boxExample.createCriteria()
                .andLevelEqualTo(level)
                .andLocationEqualTo(location);
        List<Box> boxes = selectByExample(boxExample);

        if (boxes.isEmpty()) {
            return null;
        }

        return boxes.get(0);
    }
}
