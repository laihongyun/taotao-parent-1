package com.taotao.admin.service;

import com.taotao.admin.pojo.ContentCategory;

import java.util.List;
import java.util.Map;

/**
 * @program: taotao-admin
 * @description: 商品分类接口-接口类
 * @author: lhy
 * @create: 2020-07-20 20:44
 **/
public interface ContentCategoryService extends BaseService<ContentCategory>{


    List<Map<String,Object>> findContentCategoryByParentId(Long parentId);


    Long saveContentCategory(ContentCategory contentCategory);

    void deleteContentCategory(ContentCategory contentCategory);
}
