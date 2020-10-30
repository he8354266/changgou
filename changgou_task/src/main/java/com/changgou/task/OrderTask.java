package com.changgou.task;

import com.changgou.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/2814:41
 */
@Component
public class OrderTask {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "0 0 0 * * ?")
    public void autoTake() {
        System.out.println(new Date());
        rabbitTemplate.convertAndSend("", RabbitMQConfig.ORDER_TACK, "-");
    }
}
