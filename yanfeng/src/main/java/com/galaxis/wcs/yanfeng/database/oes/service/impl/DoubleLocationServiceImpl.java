package com.galaxis.wcs.yanfeng.database.oes.service.impl;

import com.galaxis.wcs.yanfeng.database.oes.domain.Box;
import com.galaxis.wcs.yanfeng.database.oes.domain.Location;
import com.galaxis.wcs.yanfeng.database.oes.domain.LocationKey;
import com.galaxis.wcs.yanfeng.database.oes.mapper.LocationMapper;
import com.galaxis.wcs.yanfeng.database.oes.service.BoxService;
import com.galaxis.wcs.yanfeng.util.Config;
import com.galaxis.wcs.yanfeng.util.Constance;
import com.galaxis.wcs.yanfeng.work.manager.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 双深货位
 */
@Service
public class DoubleLocationServiceImpl extends LocationServiceImpl {
    private static final Logger log = LoggerFactory.getLogger(DoubleLocationServiceImpl.class);

    @Autowired
    private ConfigManager configManager;
    @Autowired
    private LocationMapper locationMapper;
    @Autowired
    private BoxService boxService;

    @Override
    public Integer getBackLocation(Integer location) {
        int i = location % 10;
        if (i < 5) {
            // 1,2靠7,8
            return location + 6;
        } else {
            // 7,8靠1,2
            return location - 6;
        }
    }

    /**
     * 做单元测试时请注意, 双深货位, 使用该方法时, 可能会锁定box
     */
    @Override
    public Location getFreeLocation(Integer level, Integer location, Boolean isLong, Box box) {
        if (location == null) {
            location = configManager.getNumber(Config.KEY_CENTER_LOCATION);
        }
        List<Location> locations = locationMapper.selectFreeLocation(level, location);
        if (locations.isEmpty()) {
            return null;
        }

        if (!isLong) {
            // 不是长箱
            // 1. 同批次, 同物料, 且7,8被占的1,2货位
            // select * from t_box b
            // where reclot >= ... and parton = ... and plcseq = 0 and plctaskid = 0
            // and level = ... and location % 10 in (7,8)
            List<Box> boxes = boxService.listFreeBox(box, level, Arrays.asList(7, 8))
                    // 过滤掉7,8货位的长箱
                    .stream().filter(b -> !boxService.isLength(b)).collect(Collectors.toList());
            if (!boxes.isEmpty()) {
                // 如果没有满足条件的箱子, 肯定就不能找1,2货位
                // 遍历box, 拿到location, 判断这些location -6是否空闲
                // select * from t_location where level = ... and location in (locations - 6) and status = 0 and type =21
                List<Integer> boxLocations = boxes.stream().map(Box::getLocation).collect(Collectors.toList());
                List<Integer> boxLocationMinus6 = boxLocations.stream().map(l -> l - 6).collect(Collectors.toList());
                List<Location> freeLocationsAndHasBoxInIn = locations.stream().filter(l -> boxLocationMinus6.contains(l.getLocation())).collect(Collectors.toList());

                if (!freeLocationsAndHasBoxInIn.isEmpty()) {
                    // 空闲的location即可作为入库, 同时设置该location + 6 对应的box为锁定
                    Location l = freeLocationsAndHasBoxInIn.get(0);
                    // 设置对应7, 8的box为锁定
                    int lc = l.getLocation() + 6;
                    Optional<Box> lockBox = boxes.stream().filter(b -> b.getLocation() == lc).findFirst();
                    if (lockBox.isPresent()) {
                        Box bx = new Box();
                        Box b = lockBox.get();
                        bx.setId(b.getId());
                        bx.setPlcTaskId(Constance.BOX_TASK_LOCK);
                        bx.setPlcSeq(box.getId());
                        boxService.updateByPrimaryKeySelective(bx);
                        log.info("计算双深货位, level: {}, location: {}, 并锁定box: {}", level, l, b.getCartonNo());
                    }

                    return l;
                }

            }

            // 2. 空闲的7,8货位, 同时1,2也得空闲
            // select * from t_location l where level = ... and location % 10 in (7,8) and type = 21
            // and status = 0 and 0 = (select status from t_location where location = l.location and level = ...)
            return chooseByWss(locations, Arrays.asList(7, 8));

            // 3. 混放(需要设置避让点)

        } else {
            // 长箱, 选择1,7 或者2,8 同时空闲的, 找空闲7,8货位时会自动判定1,2空闲, 所以直接找空闲7,8货位即可
            return chooseByWss(locations, Arrays.asList(7, 8));
        }
    }

    // 暂只适合尾数7,8
    private Location chooseByWss(List<Location> locations, List<Integer> wss) {
        if (!locations.isEmpty()) {
            List<Integer> freeLocationValue = locations.stream().map(LocationKey::getLocation).collect(Collectors.toList());
            Predicate<Location> endWith = l -> {
                Integer lction = l.getLocation();
                int ws = lction % 10;
                return wss.contains(ws) && freeLocationValue.contains(lction - 6);
            };

            List<Location> free = locations.stream().filter(endWith).collect(Collectors.toList());
            if (!free.isEmpty()) {
                Location location = free.get(0);
                log.info("计算双深货位, level: {}, location: {}", location.getLevel(), location.getLocation());
                return location;
            }
        }
        return null;
    }

}
