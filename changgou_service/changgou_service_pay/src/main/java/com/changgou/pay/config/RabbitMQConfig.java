package com.changgou.pay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/2210:32
 */
@Configuration
public class RabbitMQConfig {
    public static final String ORDER_PAY = "order_pay";

    @Bean
    public Queue queue() {
        return new Queue(ORDER_PAY);
    }
}
