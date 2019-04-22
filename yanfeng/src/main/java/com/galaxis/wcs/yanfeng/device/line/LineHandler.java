package com.galaxis.wcs.yanfeng.device.line;

import com.galaxis.wcs.yanfeng.util.template.LineRequest;
import com.galaxis.wcs.yanfeng.util.template.LineResponse;

public interface LineHandler {

    void deal(LineRequest request, LineResponse response);

}
