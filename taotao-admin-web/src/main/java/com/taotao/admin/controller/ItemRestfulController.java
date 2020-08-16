package com.taotao.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.admin.pojo.Item;
import com.taotao.admin.service.ItemService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * @program: taotao-admin-interface
 * @description:
 * @author: lhy
 * @create: 2020-07-24 11:06
 **/
@Controller
@RequestMapping("/item/restful")
public class ItemRestfulController {

    @Autowired
    private ItemService  itemService;

    //使用MD5对参数进行，签名，加强安全性
    private static final String SIGN_KEY = "f9c38f7164cffeb00fb38a35f4f1583e";


    private ObjectMapper objectMapper = new ObjectMapper();


    //查询商品资源
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItem(@PathVariable("id") Long id,@RequestParam("sign")String sign){


      try {
          /** 对请求参数先签名，再对比 */
          if (DigestUtils.md5Hex(id+SIGN_KEY).equals(sign)){
              if (id != null && id > 0){
                  Item item = itemService.findOne(id);
                  if (item != null){
                      /** 200(响应成功) */
                      return ResponseEntity.ok().body(item);
                  }
                  /**  404: 没有查询有到商品的内容 */
                  return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
              }
          }
          /** 400(错误的请求，请求参数有问题) */
          return ResponseEntity.badRequest() // Response Header
                  .body(null); // Response Body
      }catch (Exception exception){

          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
      }
    }
    //新增商品
    @PostMapping
    public ResponseEntity<Void> saveItem(@RequestBody Map<String,String> map){

    try{

        if(map!=null && map.size()>0){
            //获取签名，判断
            String sign = map.remove("sign");
            String mySign = DigestUtils.md5Hex(objectMapper.writeValueAsString(map)+SIGN_KEY);
            if(mySign.equals(sign)){
                Item item = objectMapper.readValue(objectMapper.writeValueAsString(map),Item.class);
                item.setCreated(new Date());
                item.setUpdated(item.getCreated());
                itemService.saveSelective(item);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
        }
        return ResponseEntity.badRequest().build();

    }catch (Exception exception){
    }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    //更新商品
    @PutMapping
    public ResponseEntity<Void> updateItem(@RequestBody Map<String,String> map){
        try{
            if (map != null && map.size() > 0){
                /** 得到传过来的签名 */
                String sign = map.remove("sign");
                String mySign = DigestUtils
                        .md5Hex(SIGN_KEY + objectMapper.writeValueAsString(map));
                if (mySign.equals(sign)){ // 对比签名
                    Item item = objectMapper.readValue(objectMapper.writeValueAsString(map),
                            Item.class);
                    item.setUpdated(item.getCreated());
                    itemService.updateSelective(item);
                    /**  204(修改成功，没有响应内容返回)) */
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                }
            }
            /** 400(错误的请求，请求参数有问题) */
            return ResponseEntity.badRequest().build();
        }catch(Exception ex){
            /**  500(服务器内容错误) */
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    //删除商品
    @DeleteMapping
    public ResponseEntity<Void> deleteItem(@RequestParam("id") Long id,
                                           @RequestParam("sign")String sign){
        try{
            if (DigestUtils.md5Hex(SIGN_KEY + id).equals(sign)){
                if (id != null && id > 0){
                    itemService.delete(id);
                    /**  204(删除成功，没有响应内容返回)) */
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                }
            }
            /** 400(错误的请求，请求参数有问题) */
            return ResponseEntity.badRequest().build();
        }catch(Exception ex){
            /**  500(服务器内容错误) */
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    }

