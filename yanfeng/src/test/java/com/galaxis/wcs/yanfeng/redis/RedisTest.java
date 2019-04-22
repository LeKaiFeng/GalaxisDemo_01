package com.galaxis.wcs.yanfeng.redis;

import com.galaxis.wcs.yanfeng.database.oes.domain.Box;
import com.galaxis.wcs.yanfeng.database.oes.service.MessageService;
import com.galaxis.wcs.yanfeng.util.Constance;
import com.galaxis.wcs.yanfeng.util.template.WcsSend;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    private static final Logger log = LoggerFactory.getLogger(RedisTest.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private MessageService messageService;

    @Test
    public void testAdd() {
        Box box = new Box();
        box.setCartonNo("123");

        redisTemplate.opsForValue().set(Constance.KEY_PRE_BOX + box.getCartonNo(), box);
    }

    @Test
    public void testGet() {
        Object o = redisTemplate.opsForValue().get(Constance.KEY_PRE_BOX + "123");
        Box box = (Box) o;
        System.out.println(box.getCartonNo());
    }

    public static final String WCS_TASK = "wcs_task";

    @Test
    public void testListPut() {
        WcsSend wcsSend = new WcsSend();
        for (int i = 0; i < 2; i++) {
            Long seq = messageService.getSeq(Constance.KEY_OES_SEQ);
            wcsSend.setId(i);
            wcsSend.setWmsId(seq);
            wcsSend.setMessageName("fff" + i);

            redisTemplate.opsForList().rightPush(WCS_TASK, wcsSend);
        }
    }

    @Test
    public void testListGet() throws InterruptedException {
        WcsSend wcsSend = (WcsSend) redisTemplate.opsForList().leftPop(WCS_TASK);
        Thread.sleep(3000);
        try {
            throw new RuntimeException();
        } catch (Exception e) {
            redisTemplate.opsForList().leftPush(WCS_TASK, wcsSend);
        }
    }

    @Test
    public void testSeq() {
        Long seq = messageService.getSeq(Constance.KEY_OES_SEQ);
        System.out.println("seq = " + seq);
    }

    @Test
    public void testSetPlcSeq() {
        redisTemplate.opsForValue().set(Constance.KEY_OES_SEQ, 1_0000_0000);
    }

    @Test
    public void testLst() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        long start = System.currentTimeMillis();
        List<Object> ls = redisTemplate.opsForList().range("ls23", 0, -1);
        List<Integer> kts = ls.stream().map(o -> Integer.valueOf(o.toString())).collect(Collectors.toList());
        for (Integer itg : list) {
            long s = System.currentTimeMillis();
            System.out.println(kts.contains(itg));
            long e = System.currentTimeMillis();
            log.info("---{}---", e - s);
        }
        long end = System.currentTimeMillis();
        log.info("总耗时-> {}", end - start);

    }

    @Test
    public void testlsi() {
        // Long ls = redisTemplate.opsForList().leftPushAll("ls", 1, 2, 3, 4, 5, 4, 3, 2);
        // System.out.println("ls = " + ls);
        Long remove = redisTemplate.opsForList().remove("ls", 0, 2);
        System.out.println("remove = " + remove);
    }

}
