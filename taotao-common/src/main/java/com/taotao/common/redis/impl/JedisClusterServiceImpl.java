package com.taotao.common.redis.impl;

import javax.annotation.Resource;

import redis.clients.jedis.JedisCluster;

import com.taotao.common.redis.RedisService;

/**
 * 操作Redis集群版实现类
 * @author LEE.SIU.WAH
 * @email lixiaohua7@163.com
 * @date 2017年11月29日 下午2:37:32
 * @version 1.0
 */
public class JedisClusterServiceImpl implements RedisService {
	
	/** 注入JedisCluster */
	@Resource
	private JedisCluster jedisCluster; 
	
	/**
	 * 操作键与值
	 * @param key 键
	 * @param value 值
	 * @return 状态码
	 */
	public String set(String key, String value){
		return jedisCluster.set(key, value);
	}
	/**
	 * 操作键与值
	 * @param key 键
	 * @param value 值
	 * @param seconds 该键的有效时间(按秒计算)
	 * @return 状态码
	 */
	public String setex(String key, String value, int seconds){
		return jedisCluster.setex(key, seconds, value);
	}
	/**
	 * 根据键获取值
	 * @param key 键
	 * @return 值
	 */
	public String get(String key){
		return jedisCluster.get(key);
	}
	/**
	 * 设置键的有效时间
	 * @param key 键
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
	 * 获取一个键的自增长的值
	 * @param key 键
	 * @return 值
	 */
	public Long incr(String key){
		return jedisCluster.incr(key);
	}
}