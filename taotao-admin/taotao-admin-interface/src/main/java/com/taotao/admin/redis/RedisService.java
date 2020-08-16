package com.taotao.admin.redis;

/**
 * @program: taotao-admin-interface
 * @description:
 * @author: lhy
 * @create: 2020-07-23 23:41
 **/
public interface RedisService {

    /**
     * 设置键与值(添加)
     * @param key 键
     * @param value 值
     */
    String set(String key, String value);
    /**
     * 设置键、值、生存时间(添加)
     * @param key 键
     * @param value 值
     * @param seconds 生存时间(按秒计算)
     */
    String setex(String key,String value, int seconds);
    /**
     * 获取指定的key对应的值(查询)
     * @param key 键
     * @return 值
     */
    String get(String key);
    /**
     * 设置生存时间
     * @param key 键
     * @param seconds  生存时间(按秒计算)
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
     * 设置自增长的键
     * @param key 键
     * @return 自增长值
     */
    Long incr(String key);


}
