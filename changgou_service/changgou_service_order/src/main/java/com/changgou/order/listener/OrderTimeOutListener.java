package com.changgou.order.listener;

import com.changgou.order.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/2810:58
 */
public class OrderTimeOutListener {
    @Autowired
    private OrderService orderService;

    @RabbitListener(queues = "queue.ordertimeout")
    public void receiveCloseOrderMessage(String message) {
        System.out.println("接收到关闭订单的消息:" + message);
        orderService.closeOrder(message);
    }
}
