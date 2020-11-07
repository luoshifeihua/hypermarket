package com.wqk.hypermarketindex;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableDubbo
public class HypermarketIndexApplication {

    public static void main(String[] args) {
        SpringApplication.run(HypermarketIndexApplication.class, args);
    }

}
