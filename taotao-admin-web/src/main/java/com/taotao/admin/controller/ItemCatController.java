package com.taotao.admin.controller;

import com.taotao.admin.pojo.ItemCat;
import com.taotao.admin.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @program: taotao-admin-web
 * @description: 商品类目前端控制类
 * @author: lhy
 * @create: 2020-07-08 17:57
 **/
@RestController
@RequestMapping("/itemcat")
public class ItemCatController {
    
    //导入服务类
    @Autowired
    private ItemCatService itemCatService;
    
    @RequestMapping("{page}")
    public List<ItemCat> selectItemCatByPage(
            @PathVariable("page")Integer page,
            @RequestParam(name = "rows",defaultValue = "15")Integer rows){

        List<ItemCat> itemCatList = itemCatService.findByPage(page, rows);
        
        return itemCatList;
    }

        /**
        * @Description: 根据父级id,查询物品编号
        * @Param:  parentId
        */
    @GetMapping
    public List<Map<String,Object>> selectItemCatByParentId(
            @RequestParam(value="id",defaultValue = "0")
                    Long parentId){
        try {

            return itemCatService.findItemCatByParentId(parentId);
        }catch (Exception e){
                e.printStackTrace();
        }
        return null;
    }


    }
