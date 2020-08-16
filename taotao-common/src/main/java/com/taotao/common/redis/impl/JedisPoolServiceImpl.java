package com.taotao.common.redis.impl;

import javax.annotation.Resource;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.taotao.common.redis.RedisFunction;
import com.taotao.common.redis.RedisService;

/**
 * 操作Redis单机版实现类
 * @author LEE.SIU.WAH
 * @email lixiaohua7@163.com
 * @date 2017年11月29日 下午2:36:34
 * @version 1.0
 */
public class JedisPoolServiceImpl implements RedisService {
	
	/** 注入JedisPool */
	@Resource
	private JedisPool jedisPool;
	
	private <T> T execute(RedisFunction<T> redisFunction){
		/** 定义Jedis */
		Jedis jedis = null;
		try{
			/** 获取Jedis */
			jedis = jedisPool.getResource(); 
			return redisFunction.callback(jedis);
		}finally{
			if (jedis != null) jedis.close();
		}
	}
	/**
	 * 操作键与值
	 * @param key 键
	 * @param value 值
	 * @return 状态码
	 */
	public String set(final String key,final String value){
		return execute(new RedisFunction<String>() {
			@Override
			public String callback(Jedis jedis) {
				return jedis.set(key, value);
			}
		});
	}
	/**
	 * 操作键与值
	 * @param key 键
	 * @param value 值
	 * @param seconds 该键的有效时间(按秒计算)
	 * @return 状态码
	 */
	public String setex(final String key, final String value,final int seconds){
		return execute(new RedisFunction<String>() {
			@Override
			public String callback(Jedis jedis) {
				return jedis.setex(key, seconds, value);
			}
		});
	}
	/**
	 * 根据键获取值
	 * @param key 键
	 * @return 值
	 */
	public String get(final String key){
		return execute(new RedisFunction<String>() {
			@Override
			public String callback(Jedis jedis) {
				return jedis.get(key);
			}
		});
	}
	/**
	 * 设置键的有效时间
	 * @param key 键
	 * @return 状态码
	 */
	public Long expire(final String key, final int seconds){
		return execute(new RedisFunction<Long>() {
			@Override
			public Long callback(Jedis jedis) {
				return jedis.expire(key, seconds);
			}
		});
	}
	/**
	 * 删除指定的键
	 * @param key 键
	 * @return 状态码
	 */
	public Long del(final String key){
		return execute(new RedisFunction<Long>() {
			@Override
			public Long callback(Jedis jedis) {
				return jedis.del(key);
			}
		});
	}
	/**
	 * 获取一个键的自增长的值
	 * @param key 键
	 * @return 值
	 */
	public Long incr(final String key){
		return execute(new RedisFunction<Long>() {
			@Override
			public Long callback(Jedis jedis) {
				return jedis.incr(key);
			}
		});
	}
}