package com.taotao.common.redis;
/**
 * 操作Redis的接口类
 * @author LEE.SIU.WAH
 * @email lixiaohua7@163.com
 * @date 2017年11月29日 下午2:35:44
 * @version 1.0
 */
public interface RedisService {
	
	/**
	 * 操作键与值
	 * @param key 键
	 * @param value 值
	 * @return 状态码
	 */
	String set(String key, String value);
	/**
	 * 操作键与值
	 * @param key 键
	 * @param value 值
	 * @param seconds 该键的有效时间(按秒计算)
	 * @return 状态码
	 */
	String setex(String key, String value, int seconds);
	/**
	 * 根据键获取值
	 * @param key 键
	 * @return 值
	 */
	String get(String key);
	/**
	 * 设置键的有效时间
	 * @param key 键
	 * @param seconds 该键的有效时间(按秒计算)
	 * @return 状态码
	 */
	Long expire(String key, int seconds);
	/**
	 * 删除指定的键
	 * @param key 键
	 * @return 状态码
	 */
	Long del(String key);
	/**
	 * 获取一个键的自增长的值
	 * @param key 键
	 * @return 值
	 */
	Long incr(String key);
}
