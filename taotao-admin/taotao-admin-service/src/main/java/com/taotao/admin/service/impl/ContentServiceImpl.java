package com.taotao.admin.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.admin.mapper.ContentMapper;
import com.taotao.admin.pojo.Content;
import com.taotao.admin.redis.RedisService;
import com.taotao.admin.service.ContentService;
import com.taotao.common.vo.DataGridResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: taotao-admin-interface
 * @description:
 * @author: lhy
 * @create: 2020-07-23 11:22
 **/
@Service
@Transactional(readOnly = false)
public class ContentServiceImpl extends BaseServiceImpl<Content> implements ContentService {

    private final String REDIS_INDEX_BIGAD_DATA="redisIndexBigAdData";

    @Autowired
    private RedisService redisService;

    @Autowired
    private ContentMapper contentMapper;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public DataGridResult findContentByCategoryId(Long categoryId, Integer page, Integer rows) {

        Example example = new Example(Content.class);
//        创建查询条件
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("categoryId",categoryId);
//        设置分页查询条件
        PageHelper.startPage(page,rows);
//        根据条件查询
        PageInfo<Content> pageInfo = new PageInfo<Content>(this.contentMapper.selectByExample(example));
//        封装结果集
        DataGridResult dataGridResult = new DataGridResult();
        dataGridResult.setTotal(pageInfo.getTotal());
        dataGridResult.setRows(pageInfo.getList());

        return dataGridResult;
    }

    @Override
    public String findBigAdData() {
        try {
            String jsonData = null;
            try {
                jsonData = redisService.get(REDIS_INDEX_BIGAD_DATA);
                if (StringUtils.isNotBlank(jsonData)) {
                    return jsonData;
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

            List<Content> contentLists = (List<Content>) findContentByCategoryId(25L, 1, 6).getRows();

            List<Map<String, Object>> data = new ArrayList<>();
            for (Content content : contentLists) {
                Map<String, Object> map = new HashMap<>();
                map.put("alt", content.getTitle());
                map.put("height", 240);
                map.put("heightB", 240);
                map.put("href", content.getUrl());
                map.put("src", content.getPic());
                map.put("srcB", content.getPic2());
                map.put("width", 670);
                map.put("widthB", 550);
                data.add(map);
            }
            //并将数据存入redis
            jsonData = objectMapper.writeValueAsString(data);
            try {
                redisService.setex(REDIS_INDEX_BIGAD_DATA, jsonData, 60 * 60 * 24);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            return jsonData;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }
        }
