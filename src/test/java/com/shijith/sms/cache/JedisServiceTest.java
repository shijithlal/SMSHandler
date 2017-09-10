package com.shijith.sms.cache;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import redis.clients.jedis.Jedis;

import static com.shijith.sms.Constants.COUNTER_EXPIRE_TIME;
import static com.shijith.sms.Constants.COUNTER_SUFFIX;
import static com.shijith.sms.Constants.KEY_EXPIRE_TIME;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class JedisServiceTest {
    @Test
    public void add() throws Exception {
        String key = "abc";
        String value = "pqr";
        Jedis jedis = mock(Jedis.class);
        JedisService jedisService = new JedisService(jedis);
        jedisService.add(key,value);

        verify(jedis,times(1)).set(key,value);
        verify(jedis,times(1)).expire(key,KEY_EXPIRE_TIME);

    }

    @Test
    public void get() throws Exception {
        String key = "abc";
        Jedis jedis = mock(Jedis.class);
        JedisService jedisService = new JedisService(jedis);

        jedisService.get(key);

        verify(jedis,times(1)).get(key);
    }

    @Test
    public void incrementInitially() throws Exception {
        String key = "abc";
        Jedis jedis = mock(Jedis.class);
        JedisService jedisService = new JedisService(jedis);
        when(jedis.get(key + COUNTER_SUFFIX)).thenReturn(null);
        Integer count = jedisService.increment(key);
        Assert.assertEquals(new Integer(1),count);

        verify(jedis,times(1)).get(key + COUNTER_SUFFIX);
        verify(jedis,times(1)).set(key + COUNTER_SUFFIX,"1");
        verify(jedis,times(1)).expire(key + COUNTER_SUFFIX,COUNTER_EXPIRE_TIME);


    }

    @Test
    public void increment() throws Exception {
        String key = "abc";
        Jedis jedis = mock(Jedis.class);
        JedisService jedisService = new JedisService(jedis);

        when(jedis.get(key + COUNTER_SUFFIX)).thenReturn("10");
        Integer count = jedisService.increment(key);
        Assert.assertEquals(new Integer(11),count);

        verify(jedis,times(1)).incr(key + COUNTER_SUFFIX);
        verify(jedis,times(1)).get(key + COUNTER_SUFFIX);

    }

}