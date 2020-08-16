package com.taotao.cart.service;

import com.taotao.admin.pojo.Item;
import com.taotao.cart.pojo.CartItem;

import java.util.List;

/**
 * @program: taotao-cart
 * @description:
 * @author: lhy
 * @create: 2020-08-06 11:57
 **/
public interface CartService {

    void addCartByUserId(Long id, Item item, Integer num);

    List<CartItem> findCartByUserId(Long id);

    void updateCartByUserId(Long id,Long itemId,Integer num);

    void deleteCartByItemId(Long id,Long itemId);

    void clearCart(Long id);
}
