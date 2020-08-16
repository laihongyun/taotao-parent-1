package com.taotao.portal.interceptor;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.utils.CookieUtils;
import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;

/**
 * 登录拦截器
 * @author LEE.SIU.WAH
 * @email lixiaohua7@163.com
 * @date 2017年12月10日 上午9:08:51
 * @version 1.0
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    /** 注入用户服务接口代理对象 */
    @Autowired
    private UserService userSerivce;
    private ObjectMapper objectMapper = new ObjectMapper();

    /** 在处理请求之前会进来 */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        /** 从Cookie中获取ticket登录票据 */
        String ticket = CookieUtils.getCookieValue(request,
                CookieUtils.CookieName.TAOTAO_TICKET, false);
        /** 判断 */
        if (StringUtils.isNoneBlank(ticket)){
            /** 根据ticket查询登录用户信息 */
            String userJsonStr = userSerivce.findUserByTicket(ticket);
            /** 判断用户信息是否为空 */
            if (StringUtils.isNoneBlank(userJsonStr)){
                /** 把json格式的字符串读成用户对象 */
                User user = objectMapper.readValue(userJsonStr, User.class);
                request.setAttribute("user", user);
                /** 返回true，代表登录成功 */
                return true;
            }
        }

//        /** 获取查询字符串  请求URL后台的参数 id=1&name=xx*/
//        String queryStr = request.getQueryString();
//        /** 获取用户请求的URL : http://www.taotao.com/order/balance.html ? id=1 */
//        String redirectURL = request.getRequestURL().toString();
//        System.out.println("redirectURL: " + redirectURL);
//        if (StringUtils.isNoneBlank(queryStr)){
//            redirectURL += "?" + queryStr;
//        }
        /** 重定向到登录页面 */
//        response.sendRedirect("http://sso.taotao.com/login?redirectURL="
//                + URLEncoder.encode(redirectURL, "utf-8"));
        return false;
    }

}
