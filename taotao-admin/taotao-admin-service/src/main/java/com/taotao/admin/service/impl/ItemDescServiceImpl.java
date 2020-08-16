package com.taotao.admin.service.impl;

import com.taotao.admin.mapper.ItemCatMapper;
import com.taotao.admin.mapper.ItemDescMapper;
import com.taotao.admin.pojo.ItemCat;
import com.taotao.admin.pojo.ItemDesc;
import com.taotao.admin.service.ItemCatService;
import com.taotao.admin.service.ItemDescService;
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
@Transactional (readOnly = false,rollbackFor = RuntimeException.class)
public class ItemDescServiceImpl extends BaseServiceImpl<ItemDesc> implements ItemDescService {

    //注入商品操作接口,为父类，提供实际的操作接口

}
