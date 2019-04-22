package com.galaxis.wcs.yanfeng.exception;

public enum ErrorCode {

    /**
     * 异常枚举
     */
    KT_NO_BOX_TO_KT(201, "KT叫料请求时没有box可出"),
    KT_ERR(1, "");

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
