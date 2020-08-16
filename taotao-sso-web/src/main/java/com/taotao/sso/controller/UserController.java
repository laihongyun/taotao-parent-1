package com.taotao.sso.controller;

import com.taotao.common.utils.CookieUtils;
import com.taotao.sso.service.UserService;
import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: taotao-sso
 * @description:
 * @author: lhy
 * @create: 2020-07-27 22:50
 **/
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/check/{param}/{type}")
    public ResponseEntity<Boolean> userCheck(@PathVariable("param") String param, @PathVariable("type") Integer type) {

        //基于传入的type类型，进行查询，并验证
        try {

            if (type != null && type <= 3 && type >= 1) {

                return ResponseEntity.ok().body(userService.validate(param, type));
            }
            //情况-参数错误
            return ResponseEntity.badRequest().body(null);
        } catch (Exception ex) {

        }
        //情况-系统错误
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * @Description: 注册
     * @Param:
     * @return:
     */
    @PostMapping("/register")
    @ResponseBody
    public Map<String, String> register(User user) {

        //创建hashmap封装，返回数据
        Map<String, String> map = new HashMap<String, String>();
        map.put("status", "500");
        try {
            userService.saveUser(user);
            map.put("status", "200");
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
        return map;
    }

    //登录
    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> login(User user, HttpServletResponse response, HttpServletRequest request) {

        //创建map集合封装响应数据
        Map<String, Object> data = new HashMap<>();
        try {
            //用户登录成功后返回一个票据
            String ticket = userService.login(user);
            if (StringUtils.isNoneBlank(ticket)) {
                //票据有实用值，则存入cookie
                CookieUtils.setCookie(request, response, CookieUtils.CookieName.TAOTAO_TICKET, ticket, -1, false);
                data.put("status", 200);
            } else {
                data.put("status", 500);
            }

        } catch (Exception e) {
            data.put("status", 500);
        }
        return data;
    }

    //url : "http://sso.taotao.com/user/" + ticket,
    //实际参数形式url : "http://sso.taotao.com/user/" + ticket+？callback=xxxxxxx
    //callback,是回调函数，用于传值
    @GetMapping("/{ticket}")
    public ResponseEntity<String> selectUserByTicket(@PathVariable("ticket")String ticket,
     @RequestParam(value="callback",required = false)String callback){
            try{
                String result = userService.findUserByTicket(ticket);
                //封装返回的值，此处的result就是用户的信息包
                StringBuilder sb = new StringBuilder();
                //判断是否为跨域请求
                if(StringUtils.isNoneEmpty(callback)){
                    //回调函数
                    sb.append(callback+"("+result+")");
                }else{
                    sb.append(result);
                }
                return ResponseEntity.ok(sb.toString());
            }catch (Exception exception){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
    }
    //用户退出
    @GetMapping("/logout")
    public String logout(@RequestParam(value="redirectURL",defaultValue = "http://www.taotao.com")String redirectURL,
                         HttpServletRequest request,HttpServletResponse response){
                //获取票据信息
        String ticket = CookieUtils.getCookieValue(request, CookieUtils.CookieName.TAOTAO_TICKET, false);
        //删除redis中的cookie
        userService.delTicket(ticket);
        //删除Cookie中的 ticket
        CookieUtils.deleteCookie(request,response,CookieUtils.CookieName.TAOTAO_TICKET);
        return "redirect:" + redirectURL;
    }


}
