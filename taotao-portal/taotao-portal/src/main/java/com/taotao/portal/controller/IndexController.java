package com.taotao.portal.controller;

import com.taotao.admin.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @program: taotao-portal
 * @description: 跳转到门户网站的首页
 * @author: lhy
 * @create: 2020-07-20 18:25
 **/
@Controller
public class IndexController {

//    注入内容服务接口
    @Autowired
    private ContentService contentService;

    @GetMapping("/index")
    public String index(ModelMap modelMap){

        String bigAdData = contentService.findBigAdData();
        modelMap.addAttribute("bigAdData",bigAdData);

        return "index";
    }

}
