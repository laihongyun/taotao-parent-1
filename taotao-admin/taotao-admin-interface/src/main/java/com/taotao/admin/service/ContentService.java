package com.taotao.admin.service;

import com.taotao.admin.pojo.Content;
import com.taotao.common.vo.DataGridResult;

/**
 * @program: taotao-admin-interface
 * @description:
 * @author: lhy
 * @create: 2020-07-23 11:21
 **/
public interface ContentService extends  BaseService<Content>{

    DataGridResult findContentByCategoryId(Long categoryId, Integer page, Integer rows);

    String findBigAdData();
}
