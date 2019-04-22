package com.galaxis.wcs.yanfeng.database.oes.service.impl;

import com.galaxis.wcs.yanfeng.database.oes.domain.Location;
import com.galaxis.wcs.yanfeng.database.oes.domain.LocationExample;
import com.galaxis.wcs.yanfeng.database.oes.domain.LocationKey;
import com.galaxis.wcs.yanfeng.database.oes.mapper.LocationMapper;
import com.galaxis.wcs.yanfeng.database.oes.mapper.MyBatisBaseDao;
import com.galaxis.wcs.yanfeng.database.oes.service.LocationService;
import com.galaxis.wcs.yanfeng.database.wcs.domain.GaFlashstate;
import com.galaxis.wcs.yanfeng.database.wcs.domain.GaFlashstateExample;
import com.galaxis.wcs.yanfeng.database.wcs.service.GaFlashStateService;
import com.galaxis.wcs.yanfeng.util.Config;
import com.galaxis.wcs.yanfeng.util.Constance;
import com.galaxis.wcs.yanfeng.util.OesUtil;
import com.galaxis.wcs.yanfeng.work.manager.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

// @Service
public class LocationServiceImpl extends AbstractServiceImpl<Location, LocationKey, LocationExample> implements LocationService {
    private static final Logger log = LoggerFactory.getLogger(LocationServiceImpl.class);

    @Autowired
    private ConfigManager configManager;
    @Autowired
    private LocationMapper locationMapper;
    @Autowired
    private GaFlashStateService gaFlashStateService;
    @Autowired
    private TaskServiceImpl taskService;

    @Override
    protected MyBatisBaseDao getMapper() {
        return locationMapper;
    }

    @Override
    public List<Integer> getLevels() {
        return locationMapper.selectLevels()
                .stream().filter(l -> l > 0 && l < 100).collect(Collectors.toList());
    }

    @Override
    public List<Integer> listFreeLevel() {
        // 立库所有的层
        List<Integer> levels = getLevels();

        // 查询立体库内的所有小车
        GaFlashstateExample gaFlashstateExample = new GaFlashstateExample();
        gaFlashstateExample.createCriteria().andCarLevelIn(levels);
        List<GaFlashstate> gaFlashstates = gaFlashStateService.selectByExample(gaFlashstateExample);

        // 小车分布层
        List<Integer> flashLevels = gaFlashstates.stream()
                .map(GaFlashstate::getCarLevel)
                .collect(Collectors.toList());

        // 异常小车所在层
        List<Integer> errorFlashLevels = gaFlashstates.stream()
                .filter(f -> Arrays.asList(10, 12, 22).contains(f.getFlashState()))
                .map(GaFlashstate::getCarLevel)
                .collect(Collectors.toList());

        // 查出各层入库任务数量level -> count map, 以数量count排序, 任务数少的level在前面
        Map<Integer, Integer> levelTaskCountMap = taskService.mapLevelCountByPartNo(null, Arrays.asList(Constance.TASK_TYPE_INBOUND), Constance.TASK_STATUS_RUNNING_LIST);
        List<Integer> taskLessLevel = OesUtil.sortMapByValue(levelTaskCountMap, false);

        // 最优层, 任务数少且有车的层
        List<Integer> bestLevels = new ArrayList<>();
        for (Integer l : taskLessLevel) {
            if (flashLevels.contains(l)) {
                // 任务数少, 且有车的层
                bestLevels.add(l);
            }
        }

        // LinkedHashSet 有序, 且自动滤重
        Set<Integer> levelSet = new LinkedHashSet<>();
        // 先加入最优层
        levelSet.addAll(bestLevels);
        // 加入小车分布层
        levelSet.addAll(flashLevels);
        // 加入任务最少的层
        levelSet.addAll(taskLessLevel);
        // 加入所有层
        levelSet.addAll(levels);

        return levelSet.stream()
                // 滤掉异常小车分布层
                .filter(l -> !errorFlashLevels.contains(l))
                .collect(Collectors.toList());
    }

    @Override
    public Integer getBackLocation(Integer location) {
        // 计算对面的货位号
        int dir;
        String libDir = configManager.getConfigure(Config.KEY_LIB_DIR);
        if (Objects.isNull(libDir)) {
            dir = 0;
        } else {
            dir = Integer.parseInt(libDir);
        }

        int i = location % 2;
        if (dir == 1) {
            // 110112背后的210111
            if (i == 1) {
                return location - 100_000 + 1;
            } else {
                return location + 100_000 - 1;
            }
        } else {
            // 110111背后是210112
            if (i == 1) {
                return location + 100_000 + 1;
            } else {
                return location - 100_000 - 1;
            }
        }
    }

    @Override
    public Location getFreeLocation(Integer level, Integer location, Boolean isLong) {
        if (location == null) {
            location = configManager.getNumber(Config.KEY_CENTER_LOCATION);
        }
        List<Location> locations = locationMapper.selectFreeLocation(level, location);
        if (locations.size() == 0) {
            return null;
        }
        if (!isLong) {
            // 不是长箱, 返回第一个即可
            return locations.get(0);
        }

        // 是长箱, 把locations转为map, 遍历, key为货位号, value为location对象
        Map<Integer, Location> locationMap = locations.stream()
                .collect(Collectors.toMap(LocationKey::getLocation, l -> l));
        Set<Integer> keySet = locationMap.keySet();
        for (Map.Entry<Integer, Location> entry : locationMap.entrySet()) {
            Integer l = entry.getKey();
            // 获取对面的货位
            Integer back = getBackLocation(l);
            // 如果map的keySet包含对面的货位号, 说明对面的也是空货位, 可以返回该货位
            if (keySet.contains(back)) {
                return entry.getValue();
            }
        }

        return null;
    }

    @Override
    public Integer getNearestLocation(Integer level, Integer location, Integer status) {
        LocationExample locationExample = new LocationExample();
        LocationExample.Criteria criteria = locationExample.createCriteria();
        criteria.andLevelEqualTo(level);
        if (Objects.nonNull(status)) {
            criteria.andStatusEqualTo(status);
        }
        locationExample.setOrderByClause("ABS(location - " + location + ")");
        locationExample.setLimit(1);

        List<Location> locations = selectByExample(locationExample);
        if (locations.size() > 0) {
            return locations.get(0).getLocation();
        }
        return null;
    }

    @Override
    public int updateStatus(Integer level, Integer location, Integer status, String boxNumber, Boolean isLong) {
        Location lction = new Location();
        lction.setLevel(level);
        lction.setLocation(location);
        lction.setStatus(status);
        lction.setBoxNumber(Objects.isNull(boxNumber) ? Constance.EMPTY : boxNumber);
        int i = locationMapper.updateStatusByPrimaryKey(lction);
        log.info("更新货位信息, location level: {}, location: {}, status: {}, boxNumber: {}"
                , lction.getLevel(), lction.getLocation(), lction.getStatus(), lction.getBoxNumber());
        if (isLong) {
            Integer backLocation = getBackLocation(location);
            lction.setLocation(backLocation);
            i += locationMapper.updateStatusByPrimaryKey(lction);
            log.info("更新对面货位信息, location level: {}, location: {}, status: {}, boxNumber: {}"
                    , lction.getLevel(), lction.getLocation(), lction.getStatus(), lction.getBoxNumber());
        }
        return i;
    }
}
