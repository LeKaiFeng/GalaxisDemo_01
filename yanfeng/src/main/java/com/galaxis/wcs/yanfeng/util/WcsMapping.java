package com.galaxis.wcs.yanfeng.util;

import java.util.HashMap;
import java.util.Map;

public class WcsMapping {
    private static final Map<String, Object> MAP = new HashMap<>();

    private WcsMapping() {
    }

    /**
     * 入库口映射
     */
    public static final String KEY_INBOUND_ = "inbound_come_from_";

    static {
        MAP.put(KEY_INBOUND_ + 1, Constance.LINE_INBOUND_1);
        MAP.put(KEY_INBOUND_ + 2, Constance.LINE_INBOUND_2);
    }

    public static Object mapping(String key) {
        return MAP.getOrDefault(key, 1);
    }
}
