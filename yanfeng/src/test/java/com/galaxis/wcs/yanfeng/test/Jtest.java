package com.galaxis.wcs.yanfeng.test;

import com.galaxis.wcs.yanfeng.database.oes.domain.Box;
import com.galaxis.wcs.yanfeng.database.oes.domain.Oroder;
import com.galaxis.wcs.yanfeng.util.OesUtil;
import com.galaxis.wcs.yanfeng.util.ThreadUtil;
import com.galaxis.wcs.yanfeng.util.template.LineResponse;
import com.galaxis.wcs.yanfeng.util.template.WcsSend;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Jtest {
    private static final Logger log = LoggerFactory.getLogger(Jtest.class);

    @Test
    public void testLog() {
        log.info("OK! {}", log.getName());
    }

    @Test
    public void testBean() {
        Oroder oroder = new Oroder();
        oroder.setId(123);
        oroder.setCartonNo("ctn_123");
        oroder.setPartNo("ptn_123");

        Box box = new Box();
        BeanUtils.copyProperties(oroder, box, "id");

        log.info(oroder.toString());
        log.info(box.toString());

    }

    @Test
    public void testSubList() {
        List<Integer> collect = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());

        List<List<Integer>> subs = OesUtil.subList(collect, 15);
        subs.forEach(System.out::println);
    }

    @Test
    public void testLbk() {
        LineResponse response = new LineResponse();
        response.setSeq(111);
        response.setWarn(1);

        log.info(response.toString());
    }

    @Test
    public void testInt() throws IOException {
        byte[] bytes = new byte[]{(byte) 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        int i = dis.readInt();

        System.out.println("i = " + i);

        int max = Integer.MAX_VALUE;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(max);
        byte[] bs = baos.toByteArray();
        System.out.println("Arrays.toString(bs) = " + Arrays.toString(bs));

    }

    @Test
    public void testEvent() {
        byte[] old = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        byte[] rec = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0};
        List<Integer> change = new ArrayList<>();
        for (int i = 1; i < rec.length; i++) {
            if (old[i] != rec[i]) {
                change.add(i);
            }
        }
        for (Integer index : change) {
            byte value = rec[index];
            if (value == 1) {
                // KT从有货变为缺料了
                System.out.println(index + " 号KT缺货...");
            }
        }
    }

    @Test
    public void testBgd() {
        BigDecimal bigDecimal = BigDecimal.valueOf(1.500);
        System.out.println("bigDecimal = " + bigDecimal);
        BigDecimal divide = bigDecimal.divide(BigDecimal.valueOf(1), RoundingMode.HALF_UP);
        System.out.println("divide = " + divide);
        System.out.println("divide.intValue() = " + divide.setScale(0, BigDecimal.ROUND_HALF_UP).intValue());
    }

    static volatile byte[] flags = new byte[1];

    @Test
    public void testVolatile() {
        flags[0] = 1;
        ThreadUtil.execute(() -> {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            flags[0] = 0;
            System.out.println("set flag = false");
        });

        while (flags[0] == 1) {

        }

    }

    @Test
    public void testSmt() {
        String a = String.format("%s, %s, %s, %s, %s", "a", 1, 1.1, new LineResponse(), new WcsSend());
        System.out.println(a);
    }

    @Test
    public void testSort() {
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 0);
        map.put("m", 0);
        map.put("b", 1);
        map.put("c", -1);
        map.put("v", 6);
        map.put("n", -3);

        System.out.println(map);

        List<String> list = OesUtil.sortMapByValue(map, true);
        System.out.println("list = " + list);

        List<String> list1 = OesUtil.sortMapByValue(map, false);
        System.out.println("list1 = " + list1);

    }
}
