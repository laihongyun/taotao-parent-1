package com.taotao.admin.mapper;

import com.taotao.admin.pojo.Content;
import com.taotao.admin.pojo.ContentCategory;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @program: taotao-admin
 * @description: 商品查询mapper
 * @author: lhy
 * @create: 2020-07-20 20:49
 **/
public interface ContentMapper extends Mapper<Content> {

}
