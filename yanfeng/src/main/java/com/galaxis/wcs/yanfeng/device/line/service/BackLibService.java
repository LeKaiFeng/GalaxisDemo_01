package com.galaxis.wcs.yanfeng.device.line.service;

import com.galaxis.wcs.yanfeng.database.oes.domain.Box;
import com.galaxis.wcs.yanfeng.util.template.LineRequest;
import com.galaxis.wcs.yanfeng.util.template.LineResponse;

public interface BackLibService {

    void backToLib(LineRequest request, LineResponse response);

    void lineInbound(Box box, String description, LineRequest request, LineResponse response);
}
