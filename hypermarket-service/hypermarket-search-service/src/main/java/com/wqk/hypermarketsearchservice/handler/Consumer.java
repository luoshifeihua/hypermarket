package com.wqk.hypermarketsearchservice.handler;

import com.wqk.api.search.ISearchService;
import com.wqk.common.constant.RabbitMQConstant;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    @Autowired
    private ISearchService searchService;

    @RabbitListener(queues = RabbitMQConstant.BACKGROUND_PRODUCT_UPDATE_QUEUE)
    public void processAddOrUpdate(long id){
        searchService.updateByID(id);
    }
}
