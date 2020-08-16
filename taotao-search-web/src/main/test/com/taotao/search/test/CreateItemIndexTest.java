package com.taotao.search.test;

import com.taotao.admin.pojo.Item;
import com.taotao.admin.service.ItemService;
import com.taotao.search.pojo.SolrItem;
import com.taotao.search.service.ItemSearchService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: taotao-search
 * @description:
 * @author: lhy
 * @create: 2020-07-29 19:35
 **/
public class CreateItemIndexTest {

    private ItemService itemService;

    private ItemSearchService itemSearchService;

    @Before
    public void before(){
        //获取spring容器
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("taotao-search-web-servlet.xml");
        itemService = ac.getBean(ItemService.class);
        itemSearchService = ac.getBean(ItemSearchService.class);
    }
    @Test
    public void test1(){

        //定义当前页码
        int pageNum = 1;
        int pageSize = 500;
        do{
            System.out.println("开始查询第{"+pageNum+"}页数据");
            List<Item> itemList = itemService.findByPage(pageNum,pageSize);

                if(itemList!= null && itemList.size()>0){

                    List<SolrItem> solrItems = new ArrayList<>();
                    for(Item item:itemList){
                        SolrItem solrItem = new SolrItem();
                        solrItem.setId(item.getId());
                        solrItem.setImage(item.getImage());
                        solrItem.setPrice(item.getPrice());
                        solrItem.setSellPoint(item.getSellPoint());
                        solrItem.setStatus(item.getStatus());
                        solrItem.setTitle(item.getTitle());

                        solrItems.add(solrItem);
                    }

                    itemSearchService.saveOrUpdate(solrItems);
                    System.out.println("结束查询第"+pageNum+"页数据");
                    pageNum ++;
                    pageSize = itemList.size();
                }else{
                    pageSize =0;
                }


        }while (pageSize==500);

    }

}
