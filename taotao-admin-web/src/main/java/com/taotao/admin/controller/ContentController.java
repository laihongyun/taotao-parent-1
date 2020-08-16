package com.taotao.admin.controller;

import com.taotao.admin.pojo.Content;
import com.taotao.admin.pojo.ContentCategory;
import com.taotao.admin.service.ContentService;
import com.taotao.common.vo.DataGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @program: taotao-admin-interface
 * @description: 商品管理
 * @author: lhy
 * @create: 2020-07-23 11:17
 **/
@RestController
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @GetMapping
    public DataGridResult selectContentByCategoryId(@RequestParam(value="categoryId",defaultValue = "0")Long categoryId,
                                                    @RequestParam(value="page",defaultValue = "1")Integer page,
                                                    @RequestParam(value="rows",defaultValue = "15")Integer rows
    ){
        try {
            return contentService.findContentByCategoryId(categoryId,page,rows);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }


    /** 添加 */
    @PostMapping("/save")
    public void saveContent(Content content){
        try{
            content.setCreated(new Date());
            content.setUpdated(content.getCreated());
            contentService.saveSelective(content);
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    /** 修改 */
    @PostMapping("/update")
    public void updateContent(Content content){
        try{
            content.setUpdated(new Date());
            contentService.updateSelective(content);
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
    /** 删除 */
    @PostMapping("/delete")
    public void deleteContent(@RequestParam("ids") String ids){
        try{
            String[] strs = ids.split(",");

            for(String id:strs){
                contentService.delete(Long.parseLong(id));
            }

        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }


}
