package com.galaxis.wcs.yanfeng.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class OesUtil {

    private static final Logger log = LoggerFactory.getLogger(OesUtil.class);

    private OesUtil() {
    }

    /**
     * 分解List为多个子List
     *
     * @param list    大list
     * @param subSize 子list的大小
     * @param <T>     泛型
     * @return 子list集合
     */
    public static <T> List<List<T>> subList(List<T> list, int subSize) {
        int size = list.size();
        List<List<T>> lists;

        if (size <= subSize) {
            lists = new ArrayList<>(1);
            lists.add(list);
            return lists;
        }

        int num;
        if (size % subSize == 0) {
            num = size / subSize;
        } else {
            num = size / subSize + 1;
        }
        lists = new ArrayList<>(num);
        for (int i = 1; i <= num; i++) {

            int fromIndex = subSize * (i - 1);
            int forIndex;

            if (i == num) {
                forIndex = size;
            } else {
                forIndex = subSize * i;
            }

            List<T> ts = list.subList(fromIndex, forIndex);
            lists.add(ts);
        }
        log.debug("num = {}", num);

        return lists;
    }

    /**
     * 根据value对map的key进行排序
     *
     * @param map      map
     * @param reversed 是否降序
     * @return 排序后的keys
     */
    public static <T> List<T> sortMapByValue(Map<T, Integer> map, boolean reversed) {
        List<T> keys = map.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry<T, Integer>::getValue))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        if (reversed) {
            Collections.reverse(keys);
        }
        return keys;
    }

    public static byte[] toBits(byte num) {
        return toBits(num, Byte.SIZE);
    }

    public static byte[] toBits(long num, int size) {
        byte[] bits = new byte[size];
        for (int i = 0; i < bits.length; i++) {
            bits[bits.length - i - 1] = (byte) (((num & (0x01 << i)) == 0) ? 0 : 1);
        }
        return bits;
    }

    public static String decodeBytes(byte[] bytes) {
        // 解码成字符串
        if (Objects.isNull(bytes) || bytes.length == 0) {
            log.info("字节数组解码失败, 传入的是一个空数组, bytes: {}", Arrays.toString(bytes));
            return Constance.EMPTY;
        }

        final int def = -1;
        int endIndex = def;
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] < 0x20) {
                endIndex = i;
                break;
            }
        }
        if (endIndex == def) {
            // 说明bytes里没有无效的字符, 全是有效的
            endIndex = bytes.length;
        }

        byte[] bs = Arrays.copyOf(bytes, endIndex);

        return new String(bs, Constance.CHARSET);
    }
}
