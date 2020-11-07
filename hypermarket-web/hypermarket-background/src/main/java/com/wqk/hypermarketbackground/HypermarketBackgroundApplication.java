package com.wqk.hypermarketbackground;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableDubbo
@Import(FdfsClientConfig.class)
public class HypermarketBackgroundApplication {

    public static void main(String[] args) {
        SpringApplication.run(HypermarketBackgroundApplication.class, args);
    }

}
