package com.wqk.freemarker;

import com.wqk.freemarker.entity.Product;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class FreemarkerApplicationTests {
    @Autowired
    private Configuration configuration;

    @Test
    void contextLoads() throws IOException, TemplateException {
        //模板+数据=输出
        //1.获取模板
        Template template = configuration.getTemplate("freemarker.ftl");
        //2.数据
        HashMap<String, Object> data = new HashMap<>();
        data.put("name","freemarker!");
        //对象
        Product product1 = new Product();
        product1.setId(1);
        product1.setName("产品1");
        product1.setBirthDay(new Date());
        Product product2 = new Product();
        product2.setId(2);
        product2.setName("产品2");
        product2.setBirthDay(new Date());
        List<Product> list = new ArrayList<>();
        list.add(product1);
        list.add(product2);
        data.put("list1",list);
        //3.输出
        FileWriter fileWriter = new FileWriter("D:\\hypermarket\\hypermarket-temp\\freemarker\\src\\main\\resources\\templates\\freemarker.html");
        template.process(data,fileWriter);
        System.out.println("生成静态页成功！");
    }

}
