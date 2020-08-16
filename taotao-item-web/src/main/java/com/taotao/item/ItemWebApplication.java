package com.taotao.item;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @program: taotao-item-web
 * @description:
 * @author: lhy
 * @create: 2020-08-05 11:07
 **/
@ImportResource(locations = "classpath:taotao-item-web-dubbo.xml")
@SpringBootApplication
public class ItemWebApplication {

    public static void main(String[] args) {
        //创建spring Application应用
        SpringApplication springApplication = new SpringApplication(ItemWebApplication.class);

        //关闭横轴模式
        springApplication.setBannerMode(Banner.Mode.OFF);
        //
        springApplication.run(args);
    }

}
