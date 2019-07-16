package com.edu.bupt.wechatpost;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.edu.bupt.wechatpost.dao")
public class WechatPostApplication {
    public static void main(String[] args) {
        SpringApplication.run(WechatPostApplication.class, args);
    }
}
