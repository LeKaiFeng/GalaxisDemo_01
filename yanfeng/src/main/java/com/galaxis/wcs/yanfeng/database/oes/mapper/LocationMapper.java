package com.galaxis.wcs.yanfeng.database.oes.mapper;

import com.galaxis.wcs.yanfeng.database.oes.domain.Location;
import com.galaxis.wcs.yanfeng.database.oes.domain.LocationExample;
import com.galaxis.wcs.yanfeng.database.oes.domain.LocationKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * LocationMapper继承基类
 */
@Repository
public interface LocationMapper extends MyBatisBaseDao<Location, LocationKey, LocationExample> {
    int updateStatusByPrimaryKey(Location location);

    List<Integer> selectLevels();

    List<Location> selectFreeLocation(@Param("level") Integer level, @Param("location") Integer location);
}