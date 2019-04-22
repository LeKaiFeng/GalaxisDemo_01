/*
 * Copyright (c) 2018, org.smartboot. All rights reserved.
 * project name: smart-socket
 * file name: SmartDecoder.java
 * Date: 2018-01-28
 * Author: sandao
 */

package com.galaxis.wcs.yanfeng.connection.smartsocket.protocol.decoder;

import java.nio.ByteBuffer;

/**
 * @author 三刀
 * @version V1.0 , 2018/1/28
 */
public interface SmartDecoder {
    boolean decode(ByteBuffer byteBuffer);

    ByteBuffer getBuffer();
}
