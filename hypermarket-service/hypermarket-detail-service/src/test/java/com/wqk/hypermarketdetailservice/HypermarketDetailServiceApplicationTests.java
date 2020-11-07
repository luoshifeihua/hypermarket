package com.wqk.hypermarketdetailservice;

import com.wqk.api.detail.IDetailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class HypermarketDetailServiceApplicationTests {
    @Autowired
    private IDetailService detailService;

    @Test
    void contextLoads() {
        List<Long> list = new ArrayList<>(8);
        for (long i = 1; i <= 6; i++) {
            list.add(i);
        }
        detailService.batchCreateHtml(list);
    }

}
