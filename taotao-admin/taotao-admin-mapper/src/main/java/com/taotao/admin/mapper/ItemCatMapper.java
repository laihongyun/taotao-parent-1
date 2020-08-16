package com.taotao.admin.mapper;

import com.taotao.admin.pojo.ItemCat;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @program: taotao-admin
 * @description: 自定义crud商品实体类单表接口
 * @author: lhy
 * @create: 2020-07-08 17:39
 **/

public interface ItemCatMapper extends Mapper<ItemCat>{
    @Select("select id,name as text,is_parent as state " +
            "from tb_item_cat " +
            "where parent_id = #{parentId} " +
            "order by id asc")
    List<Map<String, Object>> getItemCatByParentId(Long parentId);
}
