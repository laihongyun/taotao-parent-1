package com.taotao.admin.mapper;

import com.taotao.admin.pojo.ContentCategory;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @program: taotao-admin
 * @description: 商品查询mapper
 * @author: lhy
 * @create: 2020-07-20 20:49
 **/
public interface ContentCatogeryMapper extends Mapper<ContentCategory> {

    @Select("select id," +
            "parent_id as parentId," +
            "name as text," +
            "is_parent as state " +
            "from tb_content_category " +
            "where parent_id=#{parentId} " +
            "order by id asc")
    List<Map<String,Object>> getContentCategoryByParentId(Long parentId);
}
