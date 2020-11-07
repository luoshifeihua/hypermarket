package com.wqk.rabbitmqspringboot;

import com.wqk.rabbitmqspringboot.publish.Sender;
import com.wqk.rabbitmqspringboot.simple.SimpleSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RabbitmqSpringbootApplicationTests {
    @Autowired
    private Sender sender;

    @Test
    void contextLoads() {
        sender.send("springboot整合rabbitmq成功！");
    }

}
