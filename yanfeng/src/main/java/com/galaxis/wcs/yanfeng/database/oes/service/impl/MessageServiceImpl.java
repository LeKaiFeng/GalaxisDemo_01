package com.galaxis.wcs.yanfeng.database.oes.service.impl;

import com.galaxis.wcs.yanfeng.database.oes.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Long leftPush(String key, Object value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    @Override
    public Long rightPush(String key, Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    @Override
    public <T> T leftPop(String key, Class<T> clazz) {
        return (T) redisTemplate.opsForList().leftPop(key);
    }

    @Override
    public <T> T leftGet(String key, Class<T> clazz) {
        return (T) redisTemplate.opsForList().index(key, 0);
    }

    @Override
    public Long getSeq(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    @Override
    public List<Integer> getList(String key, boolean clear) {
        List execute;
        if (clear) {
            // 调用lua脚本保证操作的原子性
            RedisScript script = RedisScript.of(
                    "local lst = redis.call('LRANGE',KEYS[1],ARGV[1],ARGV[2]) redis.call('DEL',KEYS[1]) return lst"
                    , List.class);
            List<String> keys = Collections.singletonList(key);
            execute = (List) redisTemplate.execute(
                    script
                    // , new StringRedisSerializer(), new Jackson2JsonRedisSerializer<>(Object.class)
                    , keys, 0, -1);
        } else {
            execute = redisTemplate.opsForList().range(key, 0, -1);
        }

        return (List<Integer>) execute.stream().map(o -> Integer.valueOf(o.toString()))
                .collect(Collectors.toList());
    }
}
