package com.shijith.sms.cache;

public interface ICacheService {
    void add(String key,String value);
    String get(String key);
    Integer increment(String key);
}
