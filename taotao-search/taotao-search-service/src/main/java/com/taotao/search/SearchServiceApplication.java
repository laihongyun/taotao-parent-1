package com.taotao.search;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @program: taotao-search
 * @description:
 * @author: lhy
 * @create: 2020-07-29 13:41
 **/
@ImportResource(locations="classpath:applicationContext-dubbo.xml")
@SpringBootApplication
public class SearchServiceApplication {

    public static void main(String[] args) {
        /** 创建SpringApplication应用对象 */
        SpringApplication springApplication = new SpringApplication(SearchServiceApplication.class);
        /** 设置横幅模式 */
        springApplication.setBannerMode(Banner.Mode.OFF);
        /** 运行 */
        springApplication.run(args);
    }
}
