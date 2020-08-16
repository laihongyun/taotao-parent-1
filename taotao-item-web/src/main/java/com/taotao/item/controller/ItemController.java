package com.taotao.item.controller;

import com.taotao.admin.service.ItemDescService;
import com.taotao.admin.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @program: taotao-item-web
 * @description:
 * @author: lhy
 * @create: 2020-08-05 12:57
 **/
@Controller
public class ItemController {

//    注入商品服务
    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemDescService itemDescService;

    //根据商品id查询商品，并返回视图层
    @GetMapping("/{id}")
    public String getItem(@PathVariable("id") Long id, Model model){
        System.out.println("++++++++++++"+id);
//        根据id查询商品
        model.addAttribute("item",itemService.findOne(id));
        model.addAttribute("itemDesc",itemDescService.findOne(id));

//        返回视图
        return "item";
    }
}
