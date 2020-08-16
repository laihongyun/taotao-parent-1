package com.taotao.admin.mapper;


import com.taotao.admin.pojo.Item;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @program: taotao-admin
 * @description: 自定义crud商品实体类单表接口
 * @author: lhy
 * @create: 2020-07-08 17:39
 **/
public interface ItemMapper extends Mapper<Item>{

    List<Map<String, Object>> findAll(@Param("item")Item item);

}
