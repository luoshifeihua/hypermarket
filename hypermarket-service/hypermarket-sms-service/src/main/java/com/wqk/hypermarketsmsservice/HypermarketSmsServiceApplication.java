package com.wqk.hypermarketsmsservice;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HypermarketSmsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HypermarketSmsServiceApplication.class, args);
    }

}
