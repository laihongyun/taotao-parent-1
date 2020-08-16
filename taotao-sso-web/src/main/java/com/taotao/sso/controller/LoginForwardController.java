package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: taotao-sso
 * @description:
 * @author: lhy
 * @create: 2020-07-28 08:31
 **/
@Controller
public class LoginForwardController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "redirectURL",defaultValue = "http://www.taotao.com")String redirectURL,
                        Model model){

        model.addAttribute("redirectURL",redirectURL);

        return  "login";
    }

}
