package com.wqk.hypermarketproductservice.config;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 通过注解的方式配置分页
 */
@Configuration
public class PageConfig {
    @Bean
    public PageInterceptor getPageHelper(){
        PageInterceptor pageHelper=new PageInterceptor();
        Properties properties=new Properties();
        properties.setProperty("reasonable","true");
        pageHelper.setProperties(properties);
        return pageHelper;
    }
}
