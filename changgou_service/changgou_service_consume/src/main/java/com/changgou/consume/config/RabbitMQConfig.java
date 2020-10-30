package com.changgou.consume.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/3011:41
 */
@Configuration
public class RabbitMQConfig {
    public static final String SECKILL_ORDER_QUEUE = "seckill_order";

    @Bean
    public Queue queue() {
        return new Queue(SECKILL_ORDER_QUEUE, true);
    }
}
