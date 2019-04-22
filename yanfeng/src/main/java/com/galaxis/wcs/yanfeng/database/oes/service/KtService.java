package com.galaxis.wcs.yanfeng.database.oes.service;

import com.galaxis.wcs.yanfeng.database.oes.domain.Kt;
import com.galaxis.wcs.yanfeng.database.oes.domain.KtExample;

import java.util.List;

public interface KtService extends BaseService<Kt, Integer, KtExample> {

    String checkAllow();

    /**
     * 根据level, location查询KT
     *
     * @param level    层
     * @param location 货位
     * @return KT
     */
    Kt getByLevelLocation(Integer level, Integer location);

    /**
     * 给指定的columns增长inc值
     *
     * @param ktId    ktId
     * @param inc     增加的值
     * @param columns 增长的字段
     * @return 修改的条数
     */
    int incrementNumber(Integer ktId, Integer inc, String... columns);

    List<Kt> listBySensor(Integer sensor);

    List<Kt> listByStatus(Integer status);
}
