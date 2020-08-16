package com.taotao.cart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.cart.pojo.CartItem;
import com.taotao.cart.service.CartService;
import com.taotao.common.utils.CookieUtils;
import com.taotao.order.pojo.Order;
import com.taotao.order.service.OrderService;
import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;
import javafx.beans.binding.ObjectExpression;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: taotao-admin
 * @description:
 * @author: lhy
 * @create: 2020-08-14 17:22
 **/
@Controller
@RequestMapping("/order")
public class OrderController {

@Autowired
    private UserService userService;
@Autowired
    private CartService cartService;
@Autowired
    private OrderService orderService;

//去订单管理结算页面
    @GetMapping("/balance")
    public String balance(HttpServletRequest request, Model model){

        try{
            User user = (User) request.getAttribute("user");
            //根据用户id获取购物车信息
            List<CartItem> cart = cartService.findCartByUserId(user.getId());
            //添加响应数据
            model.addAttribute("carts",cart);
        }catch(Exception exception){
            exception.printStackTrace();
            }
        return "order-cart";
    }
//提交订单
    @PostMapping("/submit")
    @ResponseBody
    public Map<String, Object> submit(Order order,HttpServletRequest request){

        Map<String,Object> data = new HashMap<>();
        data.put("status",500);
        try{
            User user = (User) request.getAttribute("user");
            order.setUserId(user.getId());
            order.setBuyerMessage(user.getUsername());
            String orderId = orderService.saveOrder(order);
            if(StringUtils.isNoneBlank(orderId)){
                data.put("status",200);
                data.put("orderId",orderId);
//                 清空购物车
                cartService.clearCart(user.getId());
            }
            }catch(Exception exception){
            exception.printStackTrace();
            }
        return data;
    }
//    跳转到订单成功页面
    @GetMapping("/success")
    public String success(@RequestParam("id")String orderId,Model model){
        //根据订单id查询订单
        Order order = orderService.findOrderByOrderId(orderId);

        model.addAttribute("order",order);
        model.addAttribute("date", DateTime.now().plusDays(2).toString("MM月dd日"));
        return "success";
    }

}
