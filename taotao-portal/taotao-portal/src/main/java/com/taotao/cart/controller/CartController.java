package com.taotao.cart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.admin.pojo.Item;
import com.taotao.admin.service.ItemService;
import com.taotao.cart.pojo.CartItem;
import com.taotao.cart.service.CartService;
import com.taotao.cart.service.CookieCartService;
import com.taotao.common.utils.CookieUtils;
import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @program: taotao-admin
 * @description:
 * @author: lhy
 * @create: 2020-08-06 15:48
 **/
@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CookieCartService cookieCartService;

    /** 注入用户服务 */
    @Autowired
    private UserService userService;
    /** 注入商品服务 */
    @Autowired
    private ItemService itemService;
    /** 注入购物车服务 */
    @Autowired
    private CartService cartService;
    /** 定义ObjectMapper操作json */
    private ObjectMapper objectMapper = new ObjectMapper();


    @GetMapping("/{itemId}/{num}")
public String addCart(@PathVariable("itemId")Long itemId, @PathVariable("num")Integer num,
                      HttpServletRequest request, HttpServletResponse response) throws IOException {
//从cookie中获取登录票据
        String ticket = CookieUtils.getCookieValue(request, CookieUtils.CookieName.TAOTAO_TICKET, false);
        //判断登录状态
        if(StringUtils.isNoneBlank(ticket)){
            //不为空，则用户已经登录
            String userByTicket = userService.findUserByTicket(ticket);
            //把用户信息的json字符串，转为对象
            User user = objectMapper.readValue(userByTicket, User.class);
            //获取商品
            Item item = itemService.findOne(itemId);
            //根据用户主键添加商品
            cartService.addCartByUserId(user.getId(),item,num);

        }else{
            //表示用户未登录，则把购物车数据，存入cookie中
            System.out.println("----------------------");
            cookieCartService.addCart(request,response,itemId,num);
        }
        return "redirect:/cart/showCart.html";

}
    @GetMapping("/showCart")
    public String showCart(HttpServletRequest request, Model model)throws Exception{

        //从cookie中获取票据，根据票据获取用户对应的购物车信息
        String cookieValue = CookieUtils.getCookieValue(request, CookieUtils.CookieName.TAOTAO_TICKET, false);

        List<CartItem> cart = null;
        if(StringUtils.isNoneBlank(cookieValue)){
            User user = objectMapper.readValue(userService.findUserByTicket(cookieValue), User.class);
            //根据用户id，获取购物车信息
            cart = cartService.findCartByUserId(user.getId());
        }else{
            //用户没有登录，从cookie中获取购物车数据
            cart = cookieCartService.findCart(request);
        }
        model.addAttribute("cart",cart);
        return "cart";
    }
    //更新购物车数据
    @PostMapping("/update/{itemId}/{num}")
    @ResponseBody
    public ResponseEntity<Void> updateCart(@PathVariable("itemId")Long itemId,@PathVariable("num")Integer num,
                                           HttpServletRequest request,HttpServletResponse response){
        try{
//            获取登录票据
            String ticket = CookieUtils.getCookieValue(request, CookieUtils.CookieName.TAOTAO_TICKET, false);
//            判断票据
            if(StringUtils.isNoneBlank(ticket)){
                String userByTicket = userService.findUserByTicket(ticket);
//                获取票据指向的用户信息，并转换信息为用户对象
                User user = objectMapper.readValue(userByTicket, User.class);
//                跟新用户购物车中的信息
                cartService.updateCartByUserId(user.getId(),itemId,num);
            }else {
//                没有登录的客户，
                cookieCartService.updateCart(request,response,itemId,num);
            }
            return ResponseEntity.ok().build();
        }catch(Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
//    删除购物车商品
    @RequestMapping("/delete/{itemId}")
    public String deleteCart(@PathVariable("itemId")Long itemId,HttpServletRequest request,HttpServletResponse response){
        try {
//            从cookie中获取票据，获取用户信息
            String tickte = CookieUtils.getCookieValue(request, CookieUtils.CookieName.TAOTAO_TICKET, false);
            if(StringUtils.isNoneBlank(tickte)){
                String userByTicket = userService.findUserByTicket(tickte);
                User user = objectMapper.readValue(userByTicket, User.class);
//                删除用户的购物车中的数据
                cartService.deleteCartByItemId(user.getId(),itemId);
            }else {
//                未登录的用户
                cookieCartService.deleteCart(request,response,itemId);
            }
        }catch (Exception e){
     e.printStackTrace();
        }
        return "redirect:/cart/showCart.html";
    }
}
