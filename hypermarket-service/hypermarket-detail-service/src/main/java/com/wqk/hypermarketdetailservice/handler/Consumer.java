package com.wqk.hypermarketdetailservice.handler;

import com.wqk.api.detail.IDetailService;
import com.wqk.common.constant.RabbitMQConstant;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    @Autowired
    private IDetailService detailService;

    @RabbitListener(queues = RabbitMQConstant.BACKGROUND_PRODUCT_UPDATE_detail_QUEUE)
    public void processAddOrUpdate(long id){
        detailService.createHtmlById(id);
    }
}
