package com.taotao.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: taotao-admin-web
 * @description: 通用页面跳转-控制类
 * @author: lhy
 * @create: 2020-07-08 19:08
 **/
@Controller
public class PageForwardController {
    @GetMapping("/page/{viewName}")
    public String forward(@PathVariable("viewName")String viewName){
        return viewName;
    }
}
