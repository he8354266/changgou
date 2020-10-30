package com.changgou.seckill.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/3011:11
 */
@Configuration
public class RabbitMQConfig {
    public static final String SECKILL_ORDER_QUEUE="seckill_order";

    @Bean(SECKILL_ORDER_QUEUE)
    public Queue queue(){
        return new Queue(SECKILL_ORDER_QUEUE,true);
    }
}
