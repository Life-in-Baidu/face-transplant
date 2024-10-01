package com.ruoyi.biz.api.util;

/**
 * RedisKeyUtils用于Hash存储,避免产生多个key
 * redisTemplate.opsForHash().put(RedisKeyUtils.REDIS_LOGIN_KEY,"","");
 */
public class RedisKeyUtils {
    public static final String REDIS_LOGIN_KEY = "REDIS_LOGIN_KEY";
    public static final String REDIS_LOGIN_OUT_KEY = "REDIS_LOGIN_OUT_KEY";
}
