package com.taotao.admin.service;

import com.taotao.admin.pojo.ItemCat;
import javafx.beans.binding.ObjectExpression;

import java.util.List;
import java.util.Map;

/**
 * @program: taotao-admin
 * @description: 商品类目crud接口
 * @author: lhy
 * @create: 2020-07-08 17:46
 **/

public interface ItemCatService extends BaseService<ItemCat>{

    //根据父级id查询商品
    List<Map<String, Object>> findItemCatByParentId(Long parentId);

}
