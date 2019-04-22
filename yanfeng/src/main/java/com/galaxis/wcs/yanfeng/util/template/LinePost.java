package com.galaxis.wcs.yanfeng.util.template;

import lombok.Data;

@Data
public class LinePost {
    public static final Integer TYPE_GET = 0;
    public static final Integer TYPE_MIDDLE = 1;
    public static final Integer TYPE_OVER = 2;
    public static final Integer TYPE_BOX_CLEAN = 21;
    public static final Integer TYPE_STOP = 10;
    public static final Integer TYPE_STOP_RECOVER = 11;
    public static final Integer TYPE_HEART = 100;

    // 停线异常码, 与PLC交互使用
    public static final Integer ERROR_CODE_NO_CHECK_RULE = 110;
    public static final Integer ERROR_CODE_NO_BARCODE_ON_CHECK = 111;
    public static final Integer ERROR_CODE_NOT_ALLOWED_INBOUND = 112;
    public static final Integer ERROR_CODE_NO_CORRESPONDING_TASK = 113;
    public static final Integer ERROR_CODE_NO_READ_ON_CHECK = 114;
    public static final Integer ERROR_CODE_UNKNOWN_LENGTH = 115;

    /**
     * PLC请求的序号
     */
    protected Integer seq;

    /**
     * PLC请求的箱号
     */
    protected String cartonNo;

    /**
     * 请求类型
     * 0: 请求终点
     * 1: 中间状态反馈
     * 2: 到达终点反馈
     * 10: 停线
     * 11: 停线恢复
     */
    protected Integer type;

    /**
     * 位置, plc发起请求时箱子所在的位置, 或wcs反馈终点的位置
     */
    protected Integer location;

    /**
     * 错误码
     * 和PLC约定好的状态码
     */
    protected Integer errorCode = 0;

    /**
     * 校验位, 自增量, wcs收到什么就反馈plc什么
     * PLC根据校验位可判断消息的即时性
     */
    protected Integer verify = 0;

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getCartonNo() {
		return cartonNo;
	}

	public void setCartonNo(String cartonNo) {
		this.cartonNo = cartonNo;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getLocation() {
		return location;
	}

	public void setLocation(Integer location) {
		this.location = location;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public Integer getVerify() {
		return verify;
	}

	public void setVerify(Integer verify) {
		this.verify = verify;
	}

	public static Integer getTypeGet() {
		return TYPE_GET;
	}

	public static Integer getTypeMiddle() {
		return TYPE_MIDDLE;
	}

	public static Integer getTypeOver() {
		return TYPE_OVER;
	}

	public static Integer getTypeBoxClean() {
		return TYPE_BOX_CLEAN;
	}

	public static Integer getTypeStop() {
		return TYPE_STOP;
	}

	public static Integer getTypeStopRecover() {
		return TYPE_STOP_RECOVER;
	}

	public static Integer getTypeHeart() {
		return TYPE_HEART;
	}

	public static Integer getErrorCodeNoCheckRule() {
		return ERROR_CODE_NO_CHECK_RULE;
	}

	public static Integer getErrorCodeNoBarcodeOnCheck() {
		return ERROR_CODE_NO_BARCODE_ON_CHECK;
	}

	public static Integer getErrorCodeNotAllowedInbound() {
		return ERROR_CODE_NOT_ALLOWED_INBOUND;
	}

	public static Integer getErrorCodeNoCorrespondingTask() {
		return ERROR_CODE_NO_CORRESPONDING_TASK;
	}

	public static Integer getErrorCodeNoReadOnCheck() {
		return ERROR_CODE_NO_READ_ON_CHECK;
	}

	public static Integer getErrorCodeUnknownLength() {
		return ERROR_CODE_UNKNOWN_LENGTH;
	}
    
}
