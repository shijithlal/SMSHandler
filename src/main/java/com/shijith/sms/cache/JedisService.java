package com.shijith.sms.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import static com.shijith.sms.Constants.*;

@Service
public class JedisService implements ICacheService {

    private Jedis jedis;


    /**
     * Constructor for unit test
     */
    protected JedisService(Jedis jedis) {
        this.jedis = jedis;
    }

    /**
     * Constructor gets redis server address and create the object
     * @param jedisHost
     */
    @Autowired
    public JedisService(@Value("${spring.redis.host}") String jedisHost) {
        jedis = new Jedis(jedisHost);
    }

    /**
     * Add a key value pair to cache. Entry expire after expire time.
     * @param key
     * @param value
     */
    @Override
    public void add(String key, String value) {
        jedis.set(key,value);
        jedis.expire(key,KEY_EXPIRE_TIME);
    }

    /**
     * Get the value of the given key from Cache
     * @param key
     * @return
     */
    @Override
    public String get(String key) {
        return jedis.get(key);
    }

    /**
     * Increment key counter. Counter expire after expire time
     * @param key
     * @return
     */
    @Override
    public Integer increment(String key) {
        key = key + COUNTER_SUFFIX;
        String strValue = jedis.get(key);
        Integer value = 0;
        if(strValue == null) {
            jedis.set(key,"1");
            jedis.expire(key,COUNTER_EXPIRE_TIME);
            value = 1;
        }
        else {
            jedis.incr(key);
            value= Integer.parseInt(strValue) + 1;
        }
        return value;
    }

}
