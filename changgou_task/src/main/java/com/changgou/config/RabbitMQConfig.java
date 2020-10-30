package com.changgou.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/2814:32
 */
@Configuration
public class RabbitMQConfig {
    public static final String ORDER_TACK = "order_tack";

    @Bean
    public Queue queue() {
        return new Queue(ORDER_TACK);
    }
}
