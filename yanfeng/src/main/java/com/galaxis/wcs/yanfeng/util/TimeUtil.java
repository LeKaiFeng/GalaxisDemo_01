package com.galaxis.wcs.yanfeng.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    /**
     * 一般的日期格式 yyyy-MM-dd
     */
    public static final DateTimeFormatter DATE_SIMPLE = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 一般的时间格式 HH-mm-ss
     */
    public static final DateTimeFormatter TIME_SIMPLE = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * 预交日期格式 yyyyMMdd
     */
    public static final DateTimeFormatter DATE_ADVANCE = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * 预交时间格式 HHmmss
     */
    public static final DateTimeFormatter TIME_ADVANCE = DateTimeFormatter.ofPattern("HHmmss");

    public static String formatDate(LocalDateTime dateTime) {
        return format(dateTime, DATE_SIMPLE);
    }

    public static String formatDateAdvance(LocalDateTime dateTime) {
        return format(dateTime, DATE_ADVANCE);
    }

    public static String formatTime(LocalDateTime dateTime) {
        return format(dateTime, TIME_SIMPLE);
    }

    public static String formatTimeAdvance(LocalDateTime dateTime) {
        return format(dateTime, TIME_ADVANCE);
    }

    public static String format(LocalDateTime dateTime, DateTimeFormatter dtf) {
        return dtf.format(dateTime);
    }

    public static LocalDateTime parse(String date, String time) {
        LocalDate d = LocalDate.parse(date, DATE_SIMPLE);
        LocalTime t = LocalTime.parse(time, TIME_SIMPLE);
        return LocalDateTime.of(d, t);
    }

}
