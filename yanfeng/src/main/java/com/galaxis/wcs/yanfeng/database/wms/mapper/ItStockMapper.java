package com.galaxis.wcs.yanfeng.database.wms.mapper;

import com.galaxis.wcs.yanfeng.database.oes.mapper.MyBatisBaseDao;
import com.galaxis.wcs.yanfeng.database.wms.domain.ItStock;
import com.galaxis.wcs.yanfeng.database.wms.domain.ItStockExample;
import org.springframework.stereotype.Repository;

@Repository
public interface ItStockMapper extends MyBatisBaseDao<ItStock,String,ItStockExample> {
}