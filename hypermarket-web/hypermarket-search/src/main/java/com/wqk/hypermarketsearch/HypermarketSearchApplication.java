package com.wqk.hypermarketsearch;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class HypermarketSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(HypermarketSearchApplication.class, args);
    }

}
