package com.galaxis.wcs.yanfeng.database.wcs.service;

import com.galaxis.wcs.yanfeng.database.oes.service.BaseService;
import com.galaxis.wcs.yanfeng.database.wcs.domain.GaTask;
import com.galaxis.wcs.yanfeng.database.wcs.domain.GaTaskExample;

public interface GaTaskService extends BaseService<GaTask, Integer, GaTaskExample> {

    /**
     * 增加gis任务的优先级
     *
     * @param gaTask gis任务, 需要有wmsid, boxNumber, priority
     * @return 更新的条数
     */
    int addPriority(GaTask gaTask);

}
