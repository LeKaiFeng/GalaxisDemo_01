package com.galaxis.wcs.yanfeng.util;

import java.util.Arrays;
import java.util.List;

public class LocationUtil {

    private LocationUtil() {
    }

    public static Integer encodeLevelLocation(Integer level, Integer location) {
        return level * 1000000 + location;
    }

    public static List<Integer> decodeLevelLocation(Integer pos) {
        int level = pos / 1000000;
        int location = pos % 1000000;
        return Arrays.asList(level, location);
    }

    public static int getInbound(List<Integer> occupy) {
        int inbound;
        if (occupy.get(0) == 0) {
            // MK1的没堵
            inbound = Constance.LINE_INBOUND_1;
        } else if (occupy.get(1) == 0) {
            // MK1堵了, lift没堵
            inbound = Constance.LINE_INBOUND_2;
        } else {
            // MK1和lift都堵了
            inbound = Constance.LINE_INBOUND_1;
        }

        return inbound;
    }

    public static Integer getPosition(Integer location) {
        switch (location) {
            case Constance.LINE_CHECK:
                return Constance.BOX_POSITION_CHECK;
            case Constance.LINE_OUTBOUND_1:
            case Constance.LINE_OUTBOUND_2:
                return Constance.BOX_POSITION_OUTBOUND;
            default:
                return Constance.BOX_POSITION_RUNNING_LIEN;
        }
    }

}
