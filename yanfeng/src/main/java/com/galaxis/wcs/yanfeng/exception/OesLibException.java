package com.galaxis.wcs.yanfeng.exception;

/**
 * oes系统立库自定义异常
 */
public class OesLibException extends OesException {
    public OesLibException(ErrorCode code, String msgFormat, Integer args) {
        super(code.getCode(), msgFormat, args);
    }
}
