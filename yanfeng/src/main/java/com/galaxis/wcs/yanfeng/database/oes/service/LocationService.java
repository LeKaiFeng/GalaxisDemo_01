package com.galaxis.wcs.yanfeng.database.oes.service;

import com.galaxis.wcs.yanfeng.database.oes.domain.Box;
import com.galaxis.wcs.yanfeng.database.oes.domain.Location;
import com.galaxis.wcs.yanfeng.database.oes.domain.LocationExample;
import com.galaxis.wcs.yanfeng.database.oes.domain.LocationKey;

import java.util.List;

public interface LocationService extends BaseService<Location, LocationKey, LocationExample> {

    List<Integer> getLevels();

    /**
     * 查询层, 按空闲程度排序
     *
     * @return
     */
    List<Integer> listFreeLevel();

    /**
     * 拿到对面的货位号
     *
     * @param location 当前货位号
     * @return 对面的货位号
     */
    Integer getBackLocation(Integer location);

    /**
     * 查找该层的空闲货位以入库
     *
     * @param level    层
     * @param location 靠近的货位
     * @param isLong   是否长箱
     * @return 满足条件的货位
     */
    Location getFreeLocation(Integer level, Integer location, Boolean isLong);

    default Location getFreeLocation(Integer level, Integer location, Boolean isLong, Box box) {
        return getFreeLocation(level, location, isLong);
    }

    /**
     * 查找当前层离该货位最近的满足状态货位
     *
     * @param level    层
     * @param location 比较的货位
     * @param status   状态
     * @return 货位
     */
    Integer getNearestLocation(Integer level, Integer location, Integer status);

    /**
     * 根据level, location更新状态
     *
     * @param level     level
     * @param location  location
     * @param status    status
     * @param boxNumber 箱号
     * @param isLong    是否长箱
     * @return 更新的条数
     */
    int updateStatus(Integer level, Integer location, Integer status, String boxNumber, Boolean isLong);
}
