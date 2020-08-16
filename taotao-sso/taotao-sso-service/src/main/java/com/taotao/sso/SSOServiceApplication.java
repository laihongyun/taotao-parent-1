package com.taotao.sso;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @program: taotao-sso
 * @description:
 * @author: lhy
 * @create: 2020-07-27 20:43
 **/
@ImportResource(locations="classpath:applicationContext-dubbo.xml")
@SpringBootApplication
public class SSOServiceApplication {

    public static void main(String[] args) {
        //创建springapplication应用对象
        SpringApplication springApplication = new SpringApplication(SSOServiceApplication.class);

        //取消横幅
        springApplication.setBannerMode(Banner.Mode.OFF);
        //运行
        springApplication.run(args);
    }

}
