package com.taotao.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.admin.mapper.ItemCatMapper;
import com.taotao.admin.mapper.ItemDescMapper;
import com.taotao.admin.mapper.ItemMapper;
import com.taotao.admin.pojo.Item;
import com.taotao.admin.pojo.ItemCat;
import com.taotao.admin.pojo.ItemDesc;
import com.taotao.admin.service.ItemCatService;
import com.taotao.admin.service.ItemDescService;
import com.taotao.admin.service.ItemService;
import com.taotao.common.vo.DataGridResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: taotao-admin
 * @description: 商品类目crud实现类
 * @author: lhy
 * @create: 2020-07-08 17:48
 **/
@Service
@Transactional(readOnly = false,rollbackFor = RuntimeException.class)
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {


    //注入商品操作接口,为父类，提供实际的操作接口
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemDescMapper itemDescMapper;
    @Override
    public Long saveItem(Item item, String desc) {

        item.setCreated(new Date());

        item.setUpdated(item.getCreated());
        //itemMapp选择性保存数据
        itemMapper.insertSelective(item);
        //创建商品描述对象
        ItemDesc itemDesc = new ItemDesc();

        itemDesc.setCreated(new Date());

        itemDesc.setUpdated(itemDesc.getCreated());

        itemDesc.setItemId(item.getId());

        itemDesc.setItemDesc(desc);

        //保存商品描述
        itemDescMapper.insertSelective(itemDesc);
        //返回保存后的商品的id；
        return  item.getId();
    }


    @Override
    public void update(Item item, String desc) {
        //修改商品信息
        item.setUpdated(new Date());
        itemMapper.updateByPrimaryKeySelective(item);

        //修改商品描述信息
        ItemDesc itemDesc = new ItemDesc();

        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        itemDesc.setUpdated(new Date());

        itemDescMapper.updateByPrimaryKeySelective(itemDesc);
    }


    @Override
    public DataGridResult findByPageWhere(Item item, Integer page, Integer rows) {
      try{
          //开启分页
          PageHelper.startPage(page,rows);
          //获取分页数据包
          PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(itemMapper.findAll(item));
          //创建datagrid
          DataGridResult dataGridResult = new DataGridResult();
          dataGridResult.setTotal(pageInfo.getTotal());
          dataGridResult.setRows(pageInfo.getList());
          return dataGridResult;
      }catch (Exception ex){
          throw new RuntimeException(ex);
      }
    }


}
