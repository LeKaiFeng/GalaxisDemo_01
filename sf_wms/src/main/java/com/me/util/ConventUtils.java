package com.me.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ConventUtils {
    public static long getLong(byte[] data) {
        long stamp = 0L;
        for (int i = 0; i < data.length; i++) {
            stamp += (data[i] & 0xFF) << (8 * (data.length - i - 1));
        }
        return stamp;
    }

    public static LocalDateTime timestampToDateTime(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * byte[] to hex string
     *
     * @param bytes
     * @return
     */
    public static String bytesToHexStr(byte... bytes) {
        StringBuilder buf = new StringBuilder(bytes.length * 2);
        // 使用String的format方法进行转换
        for (byte b : bytes) {
            buf.append(String.format("%02x", b & 0xff));
        }

        return buf.toString();
    }

    /**
     * 将16进制字符串转换为byte[]
     *
     * @param str
     * @return
     */
    public static byte[] hexStrToBytes(String str) {
        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }

        return bytes;
    }

}
