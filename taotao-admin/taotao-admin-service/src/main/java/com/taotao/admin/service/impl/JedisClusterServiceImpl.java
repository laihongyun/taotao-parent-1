package com.taotao.admin.service.impl;

import com.taotao.admin.redis.RedisService;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;

/**
 * @program: taotao-admin-interface
 * @description:
 * @author: lhy
 * @create: 2020-07-24 03:06
 **/
public class JedisClusterServiceImpl implements RedisService {

    /** 注入JedisCluster集群 */
    @Resource
    private JedisCluster jedisCluster;

    /**
     * 设置键与值(添加)
     * @param key 键
     * @param value 值
     */
    public String set(String key, String value){
        return jedisCluster.set(key, value);
    }
    /**
     * 设置键、值、生存时间(添加)
     * @param key 键
     * @param value 值
     * @param seconds 生存时间(按秒计算)
     */
    public String setex(String key,String value, int seconds){
        return jedisCluster.setex(key, seconds, value);
    }
    /**
     * 获取指定的key对应的值(查询)
     * @param key 键
     * @return 值
     */
    public String get(String key){
        return jedisCluster.get(key);
    }
    /**
     * 设置生存时间
     * @param key 键
     * @param seconds  生存时间(按秒计算)
     * @return 状态码
     */
    public Long expire(String key, int seconds){
        return jedisCluster.expire(key, seconds);
    }
    /**
     * 删除指定的键
     * @param key 键
     * @return 状态码
     */
    public Long del(String key){
        return jedisCluster.del(key);
    }
    /**
     * 设置自增长的键
     * @param key 键
     * @return 自增长值
     */
    public Long incr(String key){
        return jedisCluster.incr(key);
    }

}
