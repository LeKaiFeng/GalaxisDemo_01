package com.galaxis.wcs.yanfeng.util.template;

import lombok.Data;

@Data
public class TaskEvent {

    public static final String EVENT_START = "event_start";
    public static final String EVENT_FINISH = "event_finish";
    public static final String EVENT_ERROR_CLOSE = "event_error_close";

    public static final String WHERE_WCS_TASK = "where_wcs_task";
    public static final String WHERE_OES_TASK = "where_oes_task";

    public static final String TYPE_NONE = "type_none";
    public static final String TYPE_INSERT = "type_insert";
    public static final String TYPE_CLOSE = "type_close";

    /**
     * 完成
     * 异常
     * ...
     */
    private String event;

    private String where;

    private String type;

    private Object data;

    public TaskEvent() {
    }

    public TaskEvent(String event, String where, String type, Object data) {
        this.event = event;
        this.where = where;
        this.type = type;
        this.data = data;
    }

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public static String getEventStart() {
		return EVENT_START;
	}

	public static String getEventFinish() {
		return EVENT_FINISH;
	}

	public static String getEventErrorClose() {
		return EVENT_ERROR_CLOSE;
	}

	public static String getWhereWcsTask() {
		return WHERE_WCS_TASK;
	}

	public static String getWhereOesTask() {
		return WHERE_OES_TASK;
	}

	public static String getTypeNone() {
		return TYPE_NONE;
	}

	public static String getTypeInsert() {
		return TYPE_INSERT;
	}

	public static String getTypeClose() {
		return TYPE_CLOSE;
	}
    
}
