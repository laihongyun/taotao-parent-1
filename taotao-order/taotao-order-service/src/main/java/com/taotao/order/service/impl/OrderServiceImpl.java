package com.taotao.order.service.impl;

import com.taotao.order.mapper.OrderItemMapper;
import com.taotao.order.mapper.OrderMapper;
import com.taotao.order.mapper.OrderShippingMapper;
import com.taotao.order.pojo.Order;
import com.taotao.order.service.OrderService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * @program: taotao-order-service
 * @description:
 * @author: lhy
 * @create: 2020-08-15 11:51
 **/
@Service
@Transactional(readOnly = false,rollbackFor = RuntimeException.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private OrderShippingMapper orderShippingMapper;

    @Override
    public String saveOrder(Order order) {
//        生成订单号
        String orderId = order.getUserId().toString()+System.currentTimeMillis();
        order.setOrderId(orderId);
        order.setCreateTime(new Date());
        order.setUpdateTime(order.getCreateTime());
        order.setStatus(1);
        orderMapper.save(order);
        return orderId;
    }

    @Override
    public Order findOrderByOrderId(String orderId) {
        return orderMapper.get(orderId);
    }

    @Override
    public void autoCloseOrder() {
        System.out.println("111");
        orderMapper.closeOrder(DateTime.now().minusDays(2).toDate());
    }
}
