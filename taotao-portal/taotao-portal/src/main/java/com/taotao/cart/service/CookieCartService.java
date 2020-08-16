package com.taotao.cart.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.cart.pojo.CartItem;

/**
 * Cookie购物车服务接口
 * @author LEE.SIU.WAH
 * @email lixiaohua7@163.com
 * @date 2017年12月9日 上午11:47:00
 * @version 1.0
 */
public interface CookieCartService {

     /**
      * 加入购物车
      * @param request 请求对象
      * @param response 响应对象
      * @param itemId 商品id
      * @param buyNum 购买数量
      */
     void addCart(HttpServletRequest request, HttpServletResponse response,
                  Long itemId, Integer buyNum);
     /**
      * 查询购物车
      * @param request 请求对象
      * @return List
      */
     List<CartItem> findCart(HttpServletRequest request);
     /**
      * 修改购物车
      * @param request 请求对象
      * @param response 响应对象
      * @param itemId 商品id
      * @param buyNum 购买数量
      */
     void updateCart(HttpServletRequest request, HttpServletResponse response,
                     Long itemId, Integer buyNum);
     /**
      * 删除购物车中的商品
      * @param request 请求对象
      * @param response 响应对象
      * @param itemId 商品id
      */
     void deleteCart(HttpServletRequest request, HttpServletResponse response,
                     Long itemId);

}
