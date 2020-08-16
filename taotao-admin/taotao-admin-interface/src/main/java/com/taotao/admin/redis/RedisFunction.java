package com.taotao.admin.redis;

import redis.clients.jedis.Jedis;

/**
 * @program: taotao-admin-interface
 * @description:
 * @author: lhy
 * @create: 2020-07-23 23:51
 **/
public interface RedisFunction<T> {
    T callback(Jedis jedis);
}
