package com.changgou.business.listener;

import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/915:52
 */
@Component
public class AdListener {
    @RabbitListener(queues = "ad_update_queue")
    public void receiveMessage(String message){
        System.out.println("接收到的消息为:"+message);
    }
}
