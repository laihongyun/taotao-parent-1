package com.taotao.order.service;

import com.taotao.order.pojo.Order;

/**
 * @program: taotao-order-service
 * @description:
 * @author: lhy
 * @create: 2020-08-15 11:49
 **/
public interface OrderService {

    String saveOrder(Order order);

    Order findOrderByOrderId(String orderId);

    void autoCloseOrder();

}
