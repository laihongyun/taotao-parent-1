package com.taotao.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.admin.mapper.ItemCatMapper;
import com.taotao.admin.pojo.ItemCat;
import com.taotao.admin.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @program: taotao-admin
 * @description: 商品类目crud实现类
 * @author: lhy
 * @create: 2020-07-08 17:48
 **/
@Service
@Transactional
public class ItemCatServiceImpl extends BaseServiceImpl<ItemCat> implements ItemCatService {

    //注入商品操作接口,为父类，提供实际的操作接口
    @Autowired
    private ItemCatMapper itemCatMapper;

    @Override
    public List<Map<String, Object>> findItemCatByParentId(Long parentId) {

        List<Map<String, Object>> itemCate =
                itemCatMapper.getItemCatByParentId(parentId);
        for (Map<String,Object> map: itemCate) {
            boolean state = (boolean)map.get("state");
            map.put("state",state?"closed":"open");
        }
        return itemCate;
    }

}
