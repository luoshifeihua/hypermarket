package com.wqk.hypermarketsearchservice;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
@MapperScan("com.wqk.mapper")
public class HypermarketSearchServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HypermarketSearchServiceApplication.class, args);
    }

}
