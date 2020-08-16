package com.taotao.order.mapper;

import com.taotao.order.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @program: taotao-order
 * @description:
 * @author: lhy
 * @create: 2020-08-15 10:52
 **/
public interface OrderMapper {

    void save(Order order);

    Order get(@Param("orderId")String orderId);

    void closeOrder(Date date);
}
