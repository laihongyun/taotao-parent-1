package com.taotao.admin.service.impl;

import com.taotao.admin.redis.RedisFunction;
import com.taotao.admin.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * @program: taotao-admin-interface
 * @description: jedis接口实现类
 * @author: lhy
 * @create: 2020-07-23 23:42
 **/
public class JedisPoolServiceImpl implements RedisService {

    //注入jedisPool连接池
    @Autowired
    private JedisPool jedisPool;

    //通用执行方法
    private <T> T execute(RedisFunction<T> redisFunction){
            Jedis jedis = null;
            try{
                jedis = jedisPool.getResource();
                return redisFunction.callback(jedis);
            }finally {
                if(jedis!=null) jedis.close();
            }
    }

    @Override
    public String set(final String key, final String value) {
        return this.execute(new RedisFunction<String>() {
            @Override
            public String callback(Jedis jedis) {
                return jedis.set(key,value);
            }
        });
    }

    @Override
    public String setex(final String key, final String value, final int seconds) {
        return execute(new RedisFunction<String>() {
            @Override
            public String callback(Jedis jedis) {
                return jedis.setex(key,seconds,value);
            }
        });
    }

    @Override
    public String get(final String key) {
        return execute(new RedisFunction<String>() {
            @Override
            public String callback(Jedis jedis) {
                return jedis.get(key);
            }
        });
    }

    @Override
    public Long expire(final String key,final int seconds) {
        return execute(new RedisFunction<Long>() {
            @Override
            public Long callback(Jedis jedis) {
                return jedis.expire(key,seconds);
            }
        });
    }

    @Override
    public Long del(final String key) {
        return execute(new RedisFunction<Long>() {
            @Override
            public Long callback(Jedis jedis) {
                return jedis.del(key);
            }
        });
    }

    @Override
    public Long incr(final String key) {
        return execute(new RedisFunction<Long>() {
            @Override
            public Long callback(Jedis jedis) {
                return jedis.incr(key);
            }
        });
    }
}
