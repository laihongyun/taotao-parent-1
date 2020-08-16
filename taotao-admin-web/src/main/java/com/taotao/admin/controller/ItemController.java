package com.taotao.admin.controller;

import com.taotao.admin.pojo.Item;
import com.taotao.admin.pojo.ItemCat;
import com.taotao.admin.service.ItemCatService;
import com.taotao.admin.service.ItemService;
import com.taotao.common.vo.DataGridResult;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.*;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.io.*;
import java.net.*;

/**
 * @program: taotao-admin-web
 * @description: 商品类目前端控制类
 * @author: lhy
 * @create: 2020-07-08 17:57
 **/
@RestController
@RequestMapping("/item")
public class ItemController {
    
    //导入服务类
    @Autowired
    private ItemService itemService;
    @Autowired
    private JmsTemplate jmsTemplate;

    private void sendTopicMessage(final String target,final Object id){

        //异步发送消息
        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
             //创建map集合封装消息
                MapMessage mapMessage = new ActiveMQMapMessage();
                mapMessage.setString("target",target);
                mapMessage.setObject("id",id);

                return mapMessage;
            }
        });
    }



    @PostMapping("/save")
    public void saveItem(Item item, @RequestParam("desc")String desc){
        try {
           Long itemId =  itemService.saveItem(item, desc);
            sendTopicMessage("save",itemId);
        }catch (Exception ex){
                ex.printStackTrace();
        }
    }

    @PostMapping("/update")
    public void updateItem(Item item,@RequestParam("desc")String desc){
        itemService.update(item,desc);
        sendTopicMessage("update",item.getId());
    }

    /**
    * @Description: 接受页面查询参数，并返回查询页面
    * @Param:  item--封装输入查询条件 page--每页显示数 rows--当前页码
    * @return: DataGridResult
    */
    @GetMapping
    public DataGridResult selectItem(
            Item item,
            @RequestParam("page")Integer page,
            @RequestParam("rows")Integer rows
                                    ){
            try{
                if(item != null && StringUtils.isEmpty(item.getTitle())){
                    item.setTitle(URLDecoder.decode(item.getTitle(),"utf-8"));
                                                                         }
                return itemService.findByPageWhere(item,page,rows);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        return null;
    }

    @PostMapping("/delete")
    public void deleteItem(String ids){
        String[] split = ids.split(",");
        for(String strid : split){

            itemService.delete(Long.parseLong(strid));
        }
        sendTopicMessage("delete",ids);
    }

}
