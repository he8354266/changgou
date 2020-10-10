package com.changgou.canal.listener;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.xpand.starter.canal.annotation.CanalEventListener;
import com.xpand.starter.canal.annotation.ListenPoint;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import com.changgou.canal.config.RabbitMQConfig;
/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/914:53
 */
@CanalEventListener
public class BusinessListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    //监控数据库
    @ListenPoint(schema = "changgou_business",table = "tb_ad")
    public void adUpdate(CanalEntry.EventType eventType,CanalEntry.RowData rowData){
        System.out.println("广告表数据发生改变");
//        //获取改变之前的数据
//        rowData.getBeforeColumnsList().forEach(
//                (c)-> System.out.println("改变前的数据："+c.getName()+"==="+c.getValue())
//        );
//        //获取改变后的数据
//        rowData.getAfterColumnsList().forEach(
//                (c)-> System.out.println("改变后的数据："+c.getName()+"==="+c.getValue()));

        //发送消息到mq
        for (CanalEntry.Column column:rowData.getAfterColumnsList()){
            if ("position".equals(column.getName())){
                System.out.println("发送最新的消息到mq"+column.getValue());
            }
            //发送消息
            rabbitTemplate.convertAndSend("",RabbitMQConfig.AD_UPDATE_QUEUE,column.getValue());
        }
    }
}
