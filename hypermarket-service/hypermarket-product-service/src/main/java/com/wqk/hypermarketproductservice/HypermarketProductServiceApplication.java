package com.wqk.hypermarketproductservice;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
@MapperScan("com.wqk.mapper")
public class HypermarketProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HypermarketProductServiceApplication.class, args);
    }

}
