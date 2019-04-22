package com.galaxis.wcs.yanfeng.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public interface Constance {
    Charset CHARSET = StandardCharsets.UTF_8;

    String Y = "Y";
    String N = "N";
    String EMPTY = "";
    String OK = "OK";
    String SUCCESS = "success";
    String ERROR = "ERROR";

    int LEN_BOX = 2;

    int KT_STATUS_FULL = 0;
    int KT_STATUS_FREE = 1;

    int KT_TYPE_AUTO = 1;
    int KT_TYPE_MANUAL = 2;
    String KT_MANUAL = "kt_manual";
    String KT_CACHE = "kt_cache";

    /**
     * KT传感器无效
     */
    int KT_SENSOR_INVALID = 0;

    /**
     * KT传感器有效
     */
    int KT_SENSOR_VALID = 1;

    int TASK_STATUS_FINISH = -1;
    int TASK_STATUS_CANCEL = -2;
    int TASK_STATUS_PAUSE = -3;
    int TASK_STATUS_ERROR_CLOSE = -11;
    int TASK_STATUS_ERROR_PROCESS = -12;
    int TASK_STATUS_DISCARD = -21;
    int TASK_STATUS_INIT = 0;
    int TASK_STATUS_RUNNING = 1;
    int TASK_STATUS_RUNNING_LINE = 2;
    int TASK_STATUS_RUNNING_LIB = 3;
    int TASK_STATUS_TO_LIB = 5;
    int TASK_STATUS_TO_CHECK = 6;
    int TASK_STATUS_TO_ERROR = 7;
    List<Integer> TASK_STATUS_RUNNING_LIST = Arrays.asList(
            TASK_STATUS_INIT, TASK_STATUS_PAUSE
            , TASK_STATUS_RUNNING, TASK_STATUS_RUNNING_LINE, TASK_STATUS_RUNNING_LIB
            , TASK_STATUS_TO_LIB, TASK_STATUS_TO_CHECK, TASK_STATUS_TO_ERROR
    );

    Integer LIB_OUTBOUND_LEVEL = 100;
    Integer LIB_OUTBOUND_LOCATION = 1;

    Integer LINE_LEVEL = -1;
    int LINE_WEIGHT_SCANNER = 11;
    int LINE_ERROR = 21;
    int LINE_CHECK = 31;
    int LINE_INBOUND_1 = 41;
    int LINE_INBOUND_2 = 42;
    int LINE_OUTBOUND_1 = 51;
    int LINE_OUTBOUND_2 = 52;
    int LINE_NONE = 0;

    Integer LIB_LEVEL_KICKOUT = 100;
    Integer LIB_LOCATION_KICKOUT = 1;
    Integer LIB_LEVEL_LOST = -100;
    Integer LIB_LOCATION_LOST = -100;

    /**
     * 给box设置task_id锁定box, 通常为双深货位时锁定靠里面的货位
     */
    Integer BOX_TASK_LOCK = -101;

    Integer BOX_POSITION_WEIGHT = 1;
    Integer BOX_POSITION_RUNNING_LIEN = 2;
    Integer BOX_POSITION_RUNNING_LIB = 3;
    Integer BOX_POSITION_LIB = 11;
    Integer BOX_POSITION_CHECK = 12;
    Integer BOX_POSITION_ERROR = 13;
    Integer BOX_POSITION_OUTBOUND = 99;

    Integer BOX_STATUS_LOST = -1;
    Integer BOX_STATUS_NOT_RECEIVE = 0;
    Integer BOX_STATUS_RECEIVE = 1;
    Integer BOX_STATUS_INBOUND = 90;
    Integer BOX_STATUS_OUTBOUND = 91;
    Integer BOX_STATUS_OUTBOUND_KT = 92;

    Integer LOCATION_TYPE_LINE = 11;
    Integer LOCATION_TYPE_LIB = 21;
    Integer LOCATION_TYPE_KT = 31;

    Integer LOCATION_STATUS_OCCUPY_ADVANCE = -1;
    Integer LOCATION_STATUS_OCCUPY_TEMPORARY = -2;
    Integer LOCATION_STATUS_OCCUPY_UNKNOWN = -3;
    Integer LOCATION_STATUS_FREE = 0;
    Integer LOCATION_STATUS_OCCUPY = 1;
    Integer LOCATION_STATUS_OCCUPY_BY_BACK = 11;

    int MAIN_TASK_TYPE_TO_LIB = 1;
    int MAIN_TASK_TYPE_TO_CHECK = 2;
    int MAIN_TASK_TYPE_TO_ERROR = 3;
    int MAIN_TASK_TYPE_BACK_LIB = 11;
    int MAIN_TASK_TYPE_QUALITY = 12;
    int MAIN_TASK_TYPE_KT = 21;
    int MAIN_TASK_TYPE_MOVE = 31;

    Integer TASK_TYPE_OUTBOUND = 11;
    Integer TASK_TYPE_INBOUND = 12;
    Integer TASK_TYPE_TRANSPORT = 13;
    Integer TASK_TYPE_RUNNING_LINE = 21;

    Integer KT_STATUS_STOP = 0;
    Integer KT_STATUS_ALLOW = 1;

    String CODE_NO_READ = "noread";
    String CODE_NULL_PLC_WEB = "diff plc web";

    String KEY_WEB_BOX_BACK_LIB = "check_box_number";
    String KEY_CHECK_CACHE = "check_cache";
    String KEY_OES_SEQ = "oes_seq";
    String KEY_WCS_TASK = "wcs_task_list";
    String KEY_OES_KT_CACHE = "oes_kt_cache_list";

    String KEY_PRE_PLC_REQUEST = "cache_plc_request_";
    String KEY_PRE_BOX_ANNOUNCE = "box_announce_";
    String KEY_PRE_BOX = "box_number_";
    String KEY_PRE_MAIN_TASK = "main_task_";
    String KEY_PER_CHECK_CODE_TYPE = "check_code_type_";
    String KEY_PRE_SKU_CHECK = "sku_check_total_";
    String KEY_PRE_SKU_COUNT = "sku_count_total_";
    String KEY_PRE_CACHE_WCS_REQUEST = "cache_wcs_request_";
    long CACHE_TIME = 2 * 60 * 1000;

}
