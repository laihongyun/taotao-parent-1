package com.taotao.search.service;

import com.taotao.search.pojo.SolrItem;
import org.apache.solr.client.solrj.impl.CloudSolrClient;

import java.util.List;
import java.util.Map;

/**
 * @program: taotao-search
 * @description:
 * @author: lhy
 * @create: 2020-07-29 13:48
 **/
public interface ItemSearchService {


    /** 添加或修改索引库 */
    void saveOrUpdate(List<SolrItem> solrItems);

    /**
     * 商品搜索方法
     * @param page 当前页码
     * @param keyword 检索关键字
     * @param rows 每页显示的记录数
     * @return Map集合
     */
    Map<String, Object> search(Integer page, String keyword, int rows);

    void delete(String[] idArr);
}
