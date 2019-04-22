package com.galaxis.wcs.yanfeng.util;

/**
 * 数据库配置对应的Key, Value
 *
 * @author cwj
 */
public interface Config {

    String Y = Constance.Y;
    String N = Constance.N;
    String SIMPLE = "simple";

    /**
     * value是Y/N的配置项
     */
    String KEY_ALLOW_DATE_OF_PRODUCTION_NOREAD = "allow_date_of_production_noread";
    String KEY_ALLOW_AUTO_CREATE_PROD_LOT = "allow_auto_create_prod_lot";

    String KEY_ENABLE_AUTO_DELETE_OUT_KT_BOX = "enable_auto_delete_out_kt_box";
    String KEY_ENABLE_KT = "enable_kt";
    String KEY_ENABLE_SYNC_BOX_NUMBER = "enable_sync_box_number";

    /**
     * value是数字的配置项
     */
    String KEY_CENTER_LOCATION = "center_location";
    String KEY_ERROR_RANGE_WEIGHT = "error_range_weight";
    String KEY_LIB_DIR = "lib_dir";
    String KEY_LIB_OUTBOUND_ID = "lib_outbound_id";
    String KEY_REC_LOT_OFFSET = "rec_lot_offset";
    String KEY_OFFSET_PROD_LOT = "offset_prod_lot";
    String KEY_MAX_CACHE_INBOUND = "max_cache_inbound";

    String KEY_TIME_OUT_KT_READ = "time_out_kt_read";
    String KEY_TIME_OUT_KT_WRITE = "time_out_kt_write";
    String KEY_TIME_OUT_KT_ALL = "time_out_kt_all";
    String KEY_TIME_OUT_LINE_READ = "time_out_line_read";
    String KEY_TIME_OUT_LINE_WRITE = "time_out_line_write";
    String KEY_TIME_OUT_LINE_ALL = "time_out_line_all";

    /**
     * value是字符串的配置项
     */
    String KEY_CHECK_BARCODE_PRIORITY = "check_barcode_priority";
    String VALUE_CHECK_BARCODE_PRIORITY_PLC = "plc";
    String VALUE_CHECK_BARCODE_PRIORITY_WEB = "web";

    String KEY_MODE_LOCATION = "mode_location";
    String VALUE_MODE_LOCATION_DOUBLE = "double";

    String KEY_MODE_COMPUTER_LEVER = "mode_computer_lever";
    String VALUE_MODE_COMPUTER_LEVER_INBOUND = "inbound";

}
