package com.changgou.canal.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;


import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/9/2917:24
 */
@Configuration
public class RabbitMQConfig {
    //定义交换机名称
    public static final String GOODS_UP_EXCHANGE = "goods_up_exchange";
    public static final String GOODS_DOWN_EXCHANGE = "goods_down_exchange";

    //定义队列名称
    public static final String AD_UPDATE_QUEUE = "ad_update_queue";
    public static final String SEARCH_ADD_QUEUE = "search_add_queue";
    public static final String SEARCH_DEL_QUEUE = "search_del_queue";

    //声明队列
    @Bean(AD_UPDATE_QUEUE)
    public Queue queue() {
        return new Queue(AD_UPDATE_QUEUE);
    }

    @Bean(SEARCH_ADD_QUEUE)
    public Queue SEARCH_ADD_QUEUE() {
        return new Queue(SEARCH_ADD_QUEUE);
    }

    @Bean(SEARCH_DEL_QUEUE)
    public Queue SEARCH_DEL_QUEUE() {
        return new Queue(SEARCH_DEL_QUEUE);
    }

    //声明交换机
    @Bean(GOODS_UP_EXCHANGE)
    public Queue GOODS_UP_EXCHANGE() {
        return new Queue(GOODS_UP_EXCHANGE);
    }
    @Bean(GOODS_DOWN_EXCHANGE)
    public Queue GOODS_DOWN_EXCHANGE() {
        return new Queue(GOODS_DOWN_EXCHANGE);
    }

    //队列与交换机的绑定
//    @Bean
//    public Binding GOODS_UP_EXCHANGE_BINDING(@Qualifier(SEARCH_ADD_QUEUE)Queue queue, @Qualifier(GOODS_UP_EXCHANGE)Exchange exchange){
//        return BindingBuilder.bind(queue).to(exchange).with("").noargs();
//    }
//    @Bean
//    public Binding GOODS_DOWN_EXCHANGE_BINDING(@Qualifier(SEARCH_DEL_QUEUE)Queue queue,@Qualifier(GOODS_DOWN_EXCHANGE)Exchange exchange){
//        return BindingBuilder.bind(queue).to(exchange).with("").noargs();
//    }
}
