package com.wqk.hypermarketdetailservice;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableDubbo
@MapperScan("com.wqk.mapper")
public class HypermarketDetailServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HypermarketDetailServiceApplication.class, args);
    }

}
