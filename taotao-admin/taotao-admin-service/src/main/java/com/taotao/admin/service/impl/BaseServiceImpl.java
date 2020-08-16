package com.taotao.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.taotao.admin.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;

/**
 * @program: taotao-admin
 * @description: 通用单表crud接口-实现类
 * @author: lhy
 * @create: 2020-07-09 10:24
 **/
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    //注入数据访问通用接口Mapper,mapper通用接口
    @Autowired
    private Mapper<T> mapper;
    //定义当前访问的实体类
    private Class<T> entityClass;
    public BaseServiceImpl(){
        //获取类上面的泛型参数
        ParameterizedType  parameterizedType =
                (ParameterizedType)this.getClass()
                        .getGenericSuperclass();
//    获取所有的实际类型参数，取第一个
        this.entityClass=
                (Class<T>)parameterizedType.getActualTypeArguments()[0];
    }
//  选择保存
    @Override
    public void saveSelective(T entity) {
    mapper.insertSelective(entity);
    }
//选择更新
    @Override
    public void updateSelective(T entity) {
    mapper.updateByPrimaryKeySelective(entity);
    }
//根据主键删除
    @Override
    public void delete(Serializable id) {
mapper.deleteByPrimaryKey(id);
    }
//批量删除
    @Override
    public void deleteAll(String idField, Serializable[] ids) {
        Example example = new Example(entityClass);
        Criteria criteria = example.createCriteria();
        //=delete * from table
        criteria.andIn(idField, Arrays.asList(ids));
        mapper.deleteByExample(criteria);
    }
//    根据主键id查询
    @Override
    public T findOne(Serializable id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<T> findAll() {
        return mapper.selectAll();
    }

    @Override
    public List<T> findAllByWhere(T entity) {
        return mapper.select(entity);
    }

    @Override
    public int countByWhere(T entity) {
        return mapper.selectCount(entity);
    }

    @Override
    public List<T> findByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return mapper.selectAll();
    }
}
