package com.shijith.sms.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import static com.shijith.sms.Constants.*;

@Service
public class JedisService implements ICacheService {

    private Jedis jedis;

    @Autowired
    public JedisService(@Value("${spring.redis.host}") String jedisHost) {
        jedis = new Jedis(jedisHost);
        System.out.println("Server is running: "+jedis.ping());
    }

    @Override
    public void add(String key, String value) {
        jedis.set(key,value);
        jedis.expire(key,KEY_EXPIRE_TIME);
    }

    @Override
    public String get(String key) {
        return jedis.get(key);
    }

    @Override
    public Integer increment(String key) {
        key = key + COUNTER_SUFFIX;
        String strValue = jedis.get(key);
        if(strValue == null) {
            add(key,"0");
            jedis.expire(key,COUNTER_EXPIRE_TIME);
        }
        else {
            jedis.incr(key);
        }
        Integer value = Integer.parseInt(strValue);
        return value;
    }

}
