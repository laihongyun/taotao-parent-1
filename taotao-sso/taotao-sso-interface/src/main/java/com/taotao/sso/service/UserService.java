package com.taotao.sso.service;

import com.taotao.sso.pojo.User;

/**
 * @program: taotao-sso
 * @description:
 * @author: lhy
 * @create: 2020-07-27 22:22
 **/
public interface UserService {


    Boolean validate(String param, Integer type);

    void saveUser(User user);

    String login(User user);

    String findUserByTicket(String ticket);

    void delTicket(String ticket);
}
