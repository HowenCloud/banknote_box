package com.ixilink.banknote_box.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.ixilink.banknote_box.common.dao")
@ComponentScan(basePackages = {"com.ixilink.banknote_box.common.config","com.ixilink.banknote_box.common.dao","com.ixilink.banknote_box.common.util","com.ixilink.banknote_box.service"})
public class ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

}
