package com.ixilink.banknote_box.login;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.ixilink.banknote_box.common.dao")
@ComponentScan(basePackages = {"com.ixilink.banknote_box.common.config","com.ixilink.banknote_box.common.controller","com.ixilink.banknote_box.common.dao","com.ixilink.banknote_box.common.util","com.ixilink.banknote_box.login"})
public class LoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginApplication.class, args);
    }

}
