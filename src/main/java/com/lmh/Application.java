package com.lmh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @ClassName: Application
 * @Description: TODO
 * @author: ALin
 * @date: 2020/3/29 下午5:41
 */
@SpringBootApplication
@MapperScan(basePackages = "com.lmh.mapper")
// 扫描所有需要的包,包含一些自用的工具类包所在的路径
@ComponentScan(basePackages = {"com.lmh","org.n3r.idworker"})
public class Application {

    @Bean
    public SpringUtil getSpringUtil() {
        return new SpringUtil();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}