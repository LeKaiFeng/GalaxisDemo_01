package com.galaxis.wcs.yanfeng.exception;

/**
 * 自定义异常基本类, 本项目所有自定义异常必须继承该类
 */
public class OesException extends RuntimeException {
    protected int code;
    protected String msg;

    public OesException(int code, String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
        this.msg = String.format(msgFormat, args);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
