package com.taotao.sso.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.sso.mapper.UserMapper;
import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @program: taotao-sso
 * @description:
 * @author: lhy
 * @create: 2020-07-27 22:25
 **/
@Service
@Transactional(readOnly = false)
public class UserServiceImpl implements UserService {

    //注入数据访问接口
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    private static final String REDIS_TICKET_PREFIX="ticket_";

    private ObjectMapper objectMapper =new ObjectMapper();
    @Override
    public Boolean validate(String param, Integer type) {
       try{
           //创建用户对象，用于查询
           User user = new User();
           //根据type类型，查询不同数据
           if(type==1){
               user.setUsername(param);
           }else if(type==2){
               user.setPhone(param);
           }else if(type==3){
               user.setEmail(param);
           }

           return userMapper.count(user)==0;
       }catch (Exception exception){
           throw new RuntimeException(exception);
       }
    }

    @Override
    public void saveUser(User user) {
        user.setCreated(new Date());
        user.setUpdated(user.getCreated());
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        userMapper.save(user);
    }

    @Override
    public String login(User user) {

       try{
           user.setPassword(DigestUtils.md5Hex(user.getPassword()));

           User u = userMapper.login(user);

           if(u!=null){
               //生成票据
               String ticket = DigestUtils.md5Hex(String.valueOf(u.getId())+System.currentTimeMillis());
               //把用户信息存入rdis
               redisTemplate.boundValueOps(REDIS_TICKET_PREFIX+ticket).set(objectMapper.writeValueAsString(u));
               //设置有效时间
               redisTemplate.expire(REDIS_TICKET_PREFIX+ticket,3600, TimeUnit.SECONDS);
                //返回票据
               return ticket;
           }


       }catch (Exception exception){
           throw new RuntimeException(exception);
       }
        return null;
    }

    @Override
    public String findUserByTicket(String ticket) {
        //从redis中查询该票据。
        String userJsonStr = redisTemplate.boundValueOps(REDIS_TICKET_PREFIX + ticket).get();

        //从新设置ker过期时间
        if(StringUtils.isNoneBlank(userJsonStr)){
            redisTemplate.expire(REDIS_TICKET_PREFIX+ticket,3600,TimeUnit.SECONDS);
        }
        return userJsonStr;
    }

    @Override
    public void delTicket(String ticket) {
        redisTemplate.delete(REDIS_TICKET_PREFIX+ticket);
    }
}
