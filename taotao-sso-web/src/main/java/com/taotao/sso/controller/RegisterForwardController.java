package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @program: taotao-sso
 * @description:
 * @author: lhy
 * @create: 2020-07-28 08:46
 **/
@Controller
public class RegisterForwardController {

    @GetMapping("/register")
    public String registerForward(){

        return "register";
    }

}
