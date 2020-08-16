package com.taotao.common.redis;

import redis.clients.jedis.Jedis;

/**
 * Redis回调函数类
 * @author LEE.SIU.WAH
 * @email lixiaohua7@163.com
 * @date 2017年11月29日 下午2:52:19
 * @version 1.0
 */
public interface RedisFunction<T> {
		
	T callback(Jedis jedis);
}
