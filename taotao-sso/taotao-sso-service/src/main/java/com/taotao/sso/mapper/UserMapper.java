package com.taotao.sso.mapper;

import com.taotao.sso.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @program: taotao-sso
 * @description:
 * @author: lhy
 * @create: 2020-07-27 22:45
 **/
@Mapper
public interface UserMapper {
    int count(User param);

    @Insert("INSERT " +
            "INTO tb_user " +
            "(username, PASSWORD, phone, email, created,updated) " +
            "VALUES(#{username},#{password},#{phone},#{email},#{created},#{updated})")
    void save(User user);

    @Select("select * from tb_user where username = #{username} and password = #{password}")
    User login(User user);
}
