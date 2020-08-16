package com.taotao.cart.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.admin.pojo.Item;
import com.taotao.admin.service.ItemService;
import com.taotao.cart.pojo.CartItem;
import com.taotao.common.utils.CookieUtils;
import com.taotao.cart.service.CookieCartService;

/**
 * Cookie购物车服务接口实现类
 * @author LEE.SIU.WAH
 * @email lixiaohua7@163.com
 * @date 2017年12月9日 上午11:47:31
 * @version 1.0
 */
@Component
public class CookieCartServiceImpl implements CookieCartService {

    /** 定义ObjectMapper操作JSON */
    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private ItemService itemService;
    /** 定义购物车存储到Cookie中的有效时间 (7天) */
    private static final int CART_MAX_AGE = 60 * 60 * 24 * 7;

    /**
     * 加入购物车
     * @param request 请求对象
     * @param response 响应对象
     * @param itemId 商品id
     * @param buyNum 购买数量
     */
    public void addCart(HttpServletRequest request, HttpServletResponse response,
                        Long itemId, Integer buyNum){
        try{
            /** 查询购物车 */
            List<CartItem> cartItemLists = findCart(request);

            /** 定义标识符 */
            boolean isBuy = false;

            /** 先判断购物车 */
            if (cartItemLists != null && cartItemLists.size() > 0){
                /** 迭代购物车 */
                for(CartItem cartItem : cartItemLists){
                    /** 判断购物车中是否购买过该商品 */
                    if (itemId.equals(cartItem.getItemId())){
                        /** 购买过，数量叠加 */
                        cartItem.setNum(cartItem.getNum() + buyNum);
                        cartItem.setUpdated(new Date());
                        isBuy = true;
                        break;
                    }
                }
            }else{
                /** 创建购物车 */
                cartItemLists = new ArrayList<>();
            }

            if (!isBuy){ // 代表该商品没有购买过
                /** 创建新的购物车商品 */
                CartItem cartItem = new CartItem();
                Item item = itemService.findOne(itemId);
                cartItem.setCreated(new Date());
                cartItem.setItemId(item.getId());
                if (item.getImages() != null && item.getImages().length > 0){
                    cartItem.setItemImage(item.getImages()[0]);
                }
                cartItem.setItemPrice(item.getPrice());
                cartItem.setItemTitle(item.getTitle());
                cartItem.setNum(buyNum);
                cartItem.setUpdated(cartItem.getCreated());
                cartItemLists.add(cartItem);
            }
            System.out.println("111111111111111111111");
            /** 把购物车数据写入Cookie */
            CookieUtils.setCookie(request, response,
                    CookieUtils.CookieName.TAOTAO_CART,
                    objectMapper.writeValueAsString(cartItemLists),
                    CART_MAX_AGE, true);
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
    /**
     * 查询购物车
     * @param request 请求对象
     * @return List
     */
    public List<CartItem> findCart(HttpServletRequest request){
        try{
            /** 从Cookie中获取购物车数据 [{},{}]*/
            String cartItemListJsonStr = CookieUtils.getCookieValue(request,
                    CookieUtils.CookieName.TAOTAO_CART, true);
            if (StringUtils.isNoneBlank(cartItemListJsonStr)){
                /** 把[{},{}] 转化成 List<CartItem> */
                return objectMapper.readValue(cartItemListJsonStr, objectMapper.getTypeFactory()
                        .constructCollectionLikeType(List.class, CartItem.class));
            }
            return null;
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
    /**
     * 修改购物车
     * @param request 请求对象
     * @param response 响应对象
     * @param itemId 商品id
     * @param buyNum 购买数量
     */
    public void updateCart(HttpServletRequest request, HttpServletResponse response,
                           Long itemId, Integer buyNum){
        try{
            /** 查询购物车 */
            List<CartItem> cartItemLists = findCart(request);
            /** 判断购物车 */
            if (cartItemLists != null && cartItemLists.size() > 0){
                /** 循环购物车的商品 */
                for (CartItem cartItem : cartItemLists){
                    /** 判断修改的是哪个商品 */
                    if (itemId.equals(cartItem.getItemId())){
                        /** 修改该商品的购买数量 */
                        cartItem.setNum(buyNum);
                        cartItem.setUpdated(new Date());
                        /** 把购物车数据写入Cookie */
                        CookieUtils.setCookie(request, response,
                                CookieUtils.CookieName.TAOTAO_CART,
                                objectMapper.writeValueAsString(cartItemLists),
                                CART_MAX_AGE, false);
                        break;
                    }
                }
            }
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
    /**
     * 删除购物车中的商品
     * @param request 请求对象
     * @param response 响应对象
     * @param itemId 商品id
     */
    public void deleteCart(HttpServletRequest request, HttpServletResponse response,
                           Long itemId){
        try{
            /** 查询购物车  */
            List<CartItem> cartItemLists = findCart(request);
            /** 判断购物车 */
            if (cartItemLists != null && cartItemLists.size() > 0){
                /** 获取集合的迭代器 */
                Iterator<CartItem> iter = cartItemLists.iterator();
                /** 迭代 */
                while(iter.hasNext()){
                    CartItem cartItem = iter.next();
                    if (itemId.equals(cartItem.getItemId())){
                        /** 从集合中删除该商品 */
                        iter.remove();
                        break;
                    }
                }
            }
            if (cartItemLists != null && cartItemLists.size() > 0){
                /** 把购物车数据写入Cookie */
                CookieUtils.setCookie(request, response,
                        CookieUtils.CookieName.TAOTAO_CART,
                        objectMapper.writeValueAsString(cartItemLists),
                        CART_MAX_AGE, true);
            }else{
                /** 把购物车对应的Cookie删除 */
                CookieUtils.deleteCookie(request, response,
                        CookieUtils.CookieName.TAOTAO_CART);
            }
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
}