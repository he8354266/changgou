package com.changgou.order.listener;

import com.alibaba.fastjson.JSON;
import com.changgou.order.config.RabbitMQConfig;
import com.changgou.order.dao.TaskHisMapper;
import com.changgou.order.dao.TaskMapper;
import com.changgou.order.pojo.Task;
import com.changgou.order.service.TaskService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/2015:21
 */
public class DelTaskListener {
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private TaskHisMapper taskHisMapper;
    @Autowired
    private TaskService taskService;

    @RabbitListener(queues = RabbitMQConfig.CG_BUYING_FINISHADDPOINT)
    public void receiveDelTaskMessage(String message) {
        System.out.println("订单服务接收到了删除任务操作的消息");
        Task task = JSON.parseObject(message, Task.class);
        //删除原有的任务数据,并向历史任务表中添加记录
        taskService.delTask(task);
    }
}
