package com.changgou.consume.listener;

import com.alibaba.fastjson.JSON;
import com.changgou.consume.config.RabbitMQConfig;
import com.changgou.consume.service.SecKillOrderService;
import com.changgou.seckill.pojo.SeckillOrder;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.amqp.core.Message;

import java.io.IOException;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/3014:03
 */
@Component
public class ConsumerListener {
    @Autowired
    private SecKillOrderService secKillOrderService;

    @RabbitListener(queues = RabbitMQConfig.SECKILL_ORDER_QUEUE)
    public void receiveSecKillOrderMessage(Message message, Channel channel) {
        //设置预抓取总数
        try {
            channel.basicQos(300);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //1.转换消息格式
        SeckillOrder seckillOrder = JSON.parseObject(message.getBody(), SeckillOrder.class);
        //2.基于业务层完成同步mysql的操作
        int result = secKillOrderService.createOrder(seckillOrder);
        if(result>0){
            //同步mysql成功
            //向消息服务器返回成功通知
            try {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
