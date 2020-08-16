package com.taotao.cart.service;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @program: taotao-cart
 * @description:
 * @author: lhy
 * @create: 2020-08-06 11:56
 **/
@ImportResource(locations="classpath:applicationContext-dubbo.xml")
@SpringBootApplication
public class CartServiceApplication {

    public static void main(String[] args) {
        /** 创建SpringApplication应用对象 */
        SpringApplication springApplication = new SpringApplication(CartServiceApplication.class);
        /** 设置横幅模式 */
        springApplication.setBannerMode(Banner.Mode.OFF);
        /** 运行 */
        springApplication.run(args);
    }
}

