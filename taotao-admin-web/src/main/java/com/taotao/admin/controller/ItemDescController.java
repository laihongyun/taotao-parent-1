package com.taotao.admin.controller;

import com.taotao.admin.pojo.ItemDesc;
import com.taotao.admin.service.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: taotao-admin-web
 * @description: 回显商品信息
 * @author: lhy
 * @create: 2020-07-17 21:44
 **/
@RestController
@RequestMapping("/itemdesc")
public class ItemDescController {

//    注入商品服务接口
    @Autowired
    private ItemDescService itemDescService;

    @GetMapping("/{itemId}")
    public ItemDesc selectItemDesc(@PathVariable("itemId")Long itemId){

        try {

            return itemDescService.findOne(itemId);
        }catch (Exception ex){
                ex.printStackTrace();
        }
        return null;
    }
}
