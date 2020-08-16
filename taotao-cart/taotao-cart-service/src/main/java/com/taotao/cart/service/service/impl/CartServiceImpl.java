package com.taotao.cart.service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.admin.pojo.Item;
import com.taotao.cart.pojo.CartItem;
import com.taotao.cart.service.CartService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: taotao-cart
 * @description:
 * @author: lhy
 * @create: 2020-08-06 11:57
 **/
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //定义购物车存储在Redis中的key的前缀
    private static final String REDIS_CART_PREFIX = "taotao_cart_";

    //    获取objectMapper对象
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void addCartByUserId(Long id, Item item, Integer num) {
        /*
         * 存储格式
         * key 用户id====》票据+id
         * value hashMap<商品id,cartItem商品>
         * */
        try {
            //添加购物车中的数据到redis
            String key = REDIS_CART_PREFIX + id;
            Object cartItemJsonStr = null;
            //判断购物车是否为空
            //再判断商品是否已经有了，有则增加数量，无则添加
            //定义购物车物品json字符串
            if (redisTemplate.hasKey(key)) {
                //已经有购物车信息了，则操作redis中的购物车,获取购物车中该商品的商品信息
                cartItemJsonStr = redisTemplate.boundHashOps(key).get(item.getId().toString());
            }
            CartItem cartItem = null;
            //表示购物车不为空
            if (cartItemJsonStr != null) {
                //表示该商品已经被购买，数量叠加
                cartItem = objectMapper.readValue(cartItemJsonStr.toString(), CartItem.class);
                cartItem.setNum(cartItem.getNum() + num);
                //设置修改时间
                cartItem.setUpdated(new Date());

            } else {
                //购物车中没有该商品，增加新的购买的商品
                cartItem = new CartItem();
                cartItem.setCreated(new Date());
                cartItem.setItemId(item.getId());
                if (item.getImages() != null && item.getImages().length > 0) {
                    cartItem.setItemImage(item.getImages()[0]);
                }
                cartItem.setItemPrice(item.getPrice());
                cartItem.setItemTitle(item.getTitle());
                cartItem.setNum(num);
                cartItem.setUpdated(cartItem.getCreated());
                cartItem.setUserId(id);
            }
            //修改redis数据库该用户的购物车数据
            redisTemplate.boundHashOps(key).put(item.getId().toString(), objectMapper.writeValueAsString(cartItem));
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
    @Override
    public List<CartItem> findCartByUserId(Long id) {
        try {
            //构建redis中购物车所对应的key
            String key = REDIS_CART_PREFIX + id;
            Map<Object, Object> entries = redisTemplate.boundHashOps(key).entries();
            List<CartItem> cartItems = new ArrayList<>();
            if (entries != null && entries.size() > 0) {
                for (Object object : entries.values()) {
                    CartItem cartItem = objectMapper.readValue(object.toString(), CartItem.class);
                    cartItems.add(cartItem);
                }
            }
            return cartItems;
        } catch (Exception exception) {
throw new RuntimeException(exception);
        }
    }

    @Override
    public void updateCartByUserId(Long id, Long itemId, Integer num) {
        try{
//            构建redis中的购物车id
            String key = REDIS_CART_PREFIX+id;
//            获取购物车
            String cartItemJsonStr = redisTemplate.boundHashOps(key).get(itemId.toString()).toString();
//           判断
            if(StringUtils.isNoneBlank(cartItemJsonStr)){
                CartItem cartItem = objectMapper.readValue(cartItemJsonStr, CartItem.class);
                cartItem.setNum(num);
                cartItem.setUpdated(new Date());
//                同步redis
                redisTemplate.boundHashOps(key).put(itemId.toString(),objectMapper.writeValueAsString(cartItem));
            }
        }catch (Exception exception){
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void deleteCartByItemId(Long id, Long itemId) {
       try {

//           构建购物车的key
           String key = REDIS_CART_PREFIX+id;
//           删除指定字段
           redisTemplate.boundHashOps(key).delete(itemId.toString());

       }catch (Exception exception){
           throw new RuntimeException(exception);
       }
    }

    @Override
    public void clearCart(Long id) {
        try{
                String key = REDIS_CART_PREFIX+id;
                redisTemplate.delete(key);
            }catch(Exception exception){
            throw new RuntimeException(exception);
            }
    }
}