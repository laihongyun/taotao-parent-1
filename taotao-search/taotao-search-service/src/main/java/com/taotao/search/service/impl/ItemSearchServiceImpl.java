package com.taotao.search.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.taotao.search.pojo.SolrItem;
import com.taotao.search.service.ItemSearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: taotao-search
 * @description:
 * @author: lhy
 * @create: 2020-07-29 13:49
 **/
@Service
public class ItemSearchServiceImpl implements ItemSearchService {


    /** 注入SolrClient */
    @Autowired
    private SolrClient solrClient;
    /** 注入需要操作的索引库集合 */
    @Value("${taotao.defaultCollection}")
    private String defaultCollection;

    /** 添加或修改索引库 */
    public void saveOrUpdate(List<SolrItem> solrItems){
        try {
            /** 添加或修改索引，得到修改响应对象 */
            UpdateResponse updateResponse = solrClient.addBeans(defaultCollection, solrItems);
            /** 判断状态码 */
            if (updateResponse.getStatus() == 0){
                /** 提交 */
                solrClient.commit(defaultCollection);
            }else{
                /** 回滚 */
                solrClient.rollback(defaultCollection);
            }
        } catch (Exception e) {
            try {
                /** 回滚 */
                solrClient.rollback(defaultCollection);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
    /**
     * 商品搜索方法
     * @param page 当前页码
     * @param keyword 检索关键字
     * @param rows 每页显示的记录数
     * @return Map集合
     */
    public Map<String, Object> search(Integer page, String keyword, int rows){
        try {
            /** 判断检索的关键字 */
            if (StringUtils.isBlank(keyword)){
                keyword = "*";
            }
            /** 创建SolrQuery封装检索条件 */
            SolrQuery solrQuery = new SolrQuery();
            /** 设置查询字符串 */
            solrQuery.setQuery("title:" + keyword + " AND status:1");
            /** 设置开始记录数 limit的第一个?值 */
            solrQuery.setStart((page - 1) * rows);
            /** 设置每页显示的记录数 */
            solrQuery.setRows(rows);

            /** 判断是否开启高亮 */
            if (!"*".equals(keyword)){
                /** 设置开启高亮 */
                solrQuery.setHighlight(true);
                /** 设置高亮字段 */
                solrQuery.addHighlightField("title");
                /** 设置高亮文本截断 */
                solrQuery.setHighlightFragsize(60);
                /** 设置高亮格式器前缀 */
                solrQuery.setHighlightSimplePre("<font color='red'>");
                /** 设置高亮格式器后缀 */
                solrQuery.setHighlightSimplePost("</font>");
            }

            /** 调用搜索方法，得到查询响应对象 */
            QueryResponse queryResponse = solrClient.query(defaultCollection,solrQuery);


            /**
             * 定义Map封装返回的数据
             ${itemList}: List<SolrItem> 商品搜索的数据
             ${totalPage}: 总页数
             * */
            Map<String, Object> data = new HashMap<>();
            /** 判断状态码 */
            if (queryResponse.getStatus() == 0){

                /** 获取搜索到的总记录数 */
                long count = queryResponse.getResults().getNumFound();
                /** 计算出总页数 */
                long totalPage = ((count - 1) / rows) + 1;
                data.put("totalPage", totalPage);

                /** 获取搜索的内容 */
                List<SolrItem> solrItems = queryResponse.getBeans(SolrItem.class);

                /** 判断是否开启了高亮显示 */
                if (solrQuery.getHighlight()){
                    /** 获取高亮内容 */
                    Map<String, Map<String, List<String>>> hMaps =
                            queryResponse.getHighlighting();
                    for (SolrItem solrItem : solrItems){
                        /** 获取标题的高亮内容 */
                        String title = hMaps.get(String.valueOf(solrItem.getId()))
                                .get("title").get(0);
                        solrItem.setTitle(title);
                    }
                }
                data.put("itemList", solrItems);
            }
            return data;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String[] idArr) {
        try {
            /** 添加或修改索引，得到修改响应对象 */
            UpdateResponse updateResponse = solrClient.deleteById(defaultCollection,Arrays.asList(idArr));
            /** 判断状态码 */
            if (updateResponse.getStatus() == 0){
                /** 提交 */
                solrClient.commit(defaultCollection);
            }else{
                /** 回滚 */
                solrClient.rollback(defaultCollection);
            }
        } catch (Exception e) {
            try {
                /** 回滚 */
                solrClient.rollback(defaultCollection);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}
