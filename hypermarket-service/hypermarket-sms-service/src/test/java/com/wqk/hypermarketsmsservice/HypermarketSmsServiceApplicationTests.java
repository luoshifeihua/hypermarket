package com.wqk.hypermarketsmsservice;

import com.wqk.api.sms.ISMSService;
import com.wqk.api.sms.pojo.SMSResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HypermarketSmsServiceApplicationTests {
    @Autowired
    private ISMSService service;

    @Test
    void contextLoads() {
        SMSResponse s = service.sendCode("123456", "18832012937");
        System.out.println(s);
    }

}
