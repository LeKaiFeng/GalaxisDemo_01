package com.galaxis.wcs.yanfeng.device.library.service;

import com.galaxis.wcs.yanfeng.database.oes.domain.Location;

import java.util.List;

public interface InboundService {

    Location computeLocation(String cartonNo, List<Integer> allowLevel);

}
