package com.mabel;

import com.mabel.utils.RedisKey;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @project: message-gateway
 * @description:
 * @author: Mabel.Chen
 * @create: 2022-02-27
 **/
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void keyPatternTest() {
        String host = "127.0.0.1";
        String port = "8010";
        redisTemplate.opsForValue().set(RedisKey.smsRateKey(host, port, ""), "", 3, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(RedisKey.smsRateKey(host, port, "123"), "", 3, TimeUnit.SECONDS);
        Set keys = redisTemplate.keys(RedisKey.smsRateKeyPattern(host, port));
        System.out.println(keys.size());
    }
}