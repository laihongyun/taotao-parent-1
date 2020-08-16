package com.taotao.item.listener;

import com.taotao.admin.pojo.Item;
import com.taotao.admin.pojo.ItemDesc;
import com.taotao.admin.service.ItemDescService;
import com.taotao.admin.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: taotao-item-web
 * @description:
 * @author: lhy
 * @create: 2020-08-05 16:57
 **/
@Component
public class ItemMessagerListener {
    //注入freemarkerConfigurer
    //获取spring封装好的配置信息
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    /** 注入服务接口 */
    @Autowired
    private ItemService itemSerivce;
    @Autowired
    private ItemDescService itemDescService;

    @Value("${taotao.itemHtmlDir}")
    private String itemHtmlDir;

    //接受消息的方法
    @JmsListener(destination = "item.topic")
    public void onMessage(Map<String,Object> message){


//        获取消息
        try {
            String target = message.get("target").toString();
            Object id = message.get("id");
            if("delete".equals(target)){
//              商品删除，则删除商品相对应的静态页面
                    String ids = (String)id;
                    String[] idArr = ids.split(",");
                for (String itemId : idArr){
                    String filedir = itemHtmlDir+itemId+".html";
                    System.out.println(filedir+"-111111111111111");
                    File file = new File(filedir);
                    System.out.println(file);
                    if (file.exists() && file.isFile()){
                        boolean delete = file.delete();
                        System.out.println("=============="+delete);
                    }
                }

            }else{
                Long itemId=(Long)id;
                //生成静态的html页面
                Configuration configuration = freeMarkerConfigurer.getConfiguration();
                //获取模板
                Template template = configuration.getTemplate("item.ftl");
                //填充模板，生成静态的html页面
                //生成信息封装map
                Map<String,Object> dataModel = new HashMap<>();
                //获取商品
                Item item = itemSerivce.findOne(itemId);
                ItemDesc itemDesc = itemDescService.findOne(itemId);
                //填充响应map
                dataModel.put("item",item);
                dataModel.put("itemDesc",itemDesc);
                //生成静态html
                template.process(dataModel,new FileWriter(itemHtmlDir+id+".html"));
            }
        }catch (Exception e){
                //有异常回回滚
            System.out.println("==========");
            throw new RuntimeException(e);
        }
    }
}
