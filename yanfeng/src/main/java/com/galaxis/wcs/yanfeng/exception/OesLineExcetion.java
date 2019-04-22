package com.galaxis.wcs.yanfeng.exception;

/**
 * oes系统输送线自定义异常
 */
public class OesLineExcetion extends OesException {
    // 普通异常, 流程中统一异常处理使用
    public static final int CODE_NO_READ = 100;
    public static final int CODE_NO_ORDER = 101;
    public static final int CODE_NO_SKU_INFO = 102;
    public static final int CODE_OVER_WEIGHT = 103;
    public static final int CODE_UNKNOWN_LENGTH = 104;
    public static final int CODE_NO_PRODUCTION_DATE = 105;
    public static final int CODE_RECEIVED = 106;

    protected int location;

    public OesLineExcetion(int location, int code, String msgFormat, Object... args) {
        super(code, msgFormat, args);
        this.location = location;
    }

    public int getLocation() {
        return location;
    }
}
