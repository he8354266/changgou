package com.changgou.search.listener;

import com.changgou.search.config.RabbitMQConfig;
import com.changgou.search.service.ESManagerService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/917:57
 */
public class GoodsUpListener {
    @Autowired
    private ESManagerService esManagerService;

    @RabbitListener(queues = RabbitMQConfig.SEARCH_ADD_QUEUE)
    public void receiveMessage(String spuId) {
        System.out.println("接收到的消息为" + spuId);
        //
    }
}
