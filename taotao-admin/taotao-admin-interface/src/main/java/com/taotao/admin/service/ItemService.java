package com.taotao.admin.service;

import com.taotao.admin.pojo.Item;
import com.taotao.admin.pojo.ItemCat;
import com.taotao.common.vo.DataGridResult;

import java.util.List;
import java.util.Map;

/**
 * @program: taotao-admin
 * @description: 商品类目crud接口
 * @author: lhy
 * @create: 2020-07-08 17:46
 **/

public interface ItemService extends BaseService<Item>{

    Long saveItem(Item item,String desc);

    void update(Item item,String desc);
    /**
     * @Description: 接受页面查询参数，并返回查询页面
     * @Param:  item--封装输入查询条件 page--每页显示数 rows--当前页码
     * @return: DataGridResult
     */
    DataGridResult findByPageWhere(Item item, Integer page, Integer rows);


}
