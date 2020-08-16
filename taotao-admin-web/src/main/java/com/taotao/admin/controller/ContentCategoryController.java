package com.taotao.admin.controller;

import com.taotao.admin.pojo.ContentCategory;
import com.taotao.admin.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: taotao-admin-web
 * @description: 内容分类查询-控制类
 * @author: lhy
 * @create: 2020-07-20 20:38
 **/
@RestController
@RequestMapping("/contentcategory")
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    @GetMapping
    public List<Map<String,Object>> selectContentCategoryByParentId(
            @RequestParam(value="id",defaultValue = "0")Long parentId
    ){

        return contentCategoryService.findContentCategoryByParentId(parentId);

    }
    /**
    * @Description: 新增分类
    * @Param:
    * @return:
    */
    @PostMapping("/save")
    public Long saveContentCategory(ContentCategory contentCategory){

        try{
            return contentCategoryService.saveContentCategory(contentCategory);
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }

    }
    /**
    * @Description:
    * @Param:
    * @return:
    */
    @PostMapping("/update")
    public void updateContentCategory(ContentCategory contentCategory){
            try {
                contentCategory.setUpdated(new Date());
                contentCategoryService.updateSelective(contentCategory);
            }catch (Exception ex){
                throw new RuntimeException(ex);
            }
    }
    @PostMapping("/delete")
    public void deleteContentCategory(ContentCategory contentCategory){
        try {
            contentCategoryService.deleteContentCategory(contentCategory);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
