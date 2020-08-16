package com.taotao.search.listener;

import com.taotao.admin.pojo.Item;
import com.taotao.admin.service.ItemService;
import com.taotao.search.pojo.SolrItem;
import com.taotao.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.SessionAwareMessageListener;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: taotao-search
 * @description:
 * @author: lhy
 * @create: 2020-08-01 00:03
 **/
public class ItemMessageListener implements SessionAwareMessageListener <MapMessage>{

    //商品服务
    @Autowired
    private ItemService itemService;

    //商品搜索服务
    @Autowired
    private ItemSearchService itemSearchService;

    @Override
    public void onMessage(MapMessage mapMessage, Session session) throws JMSException {
//获取消息中的内容
        try {
            String target = mapMessage.getString("target");
            Object id = mapMessage.getObject("id");
            //判断target
            if("delete".equals(target)){
                String ids = (String)id;
                //分割多个id
                String[] idArr = ids.split(",");
                //批量删除id
                itemSearchService.delete(idArr);
            }else {
                Long itemId = (Long)id;
                Item item = itemService.findOne(itemId);
                //把item转换为solr
                SolrItem solrItem = new SolrItem();
                solrItem.setId(item.getId());
                solrItem.setImage(item.getImage());
                solrItem.setPrice(item.getPrice());
                solrItem.setSellPoint(item.getSellPoint());
                solrItem.setStatus(item.getStatus());
                solrItem.setTitle(item.getTitle());
                List<SolrItem> solrItemList = new ArrayList<>();
                solrItemList.add(solrItem);

                itemSearchService.saveOrUpdate(solrItemList);
            }
            session.commit();
        }catch (Exception exception){
            session.rollback();
            throw new RuntimeException(exception);
        }
    }
}
