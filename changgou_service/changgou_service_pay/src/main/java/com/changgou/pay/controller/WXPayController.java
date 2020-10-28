package com.changgou.pay.controller;

import com.alibaba.fastjson.JSON;
import com.changgou.pay.config.RabbitMQConfig;
import com.changgou.pay.service.WXPayService;
import com.changgou.pojo.Result;
import com.changgou.pojo.StatusCode;
import github.wxpay.sdk.WXPayUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.changgou.util.ConvertUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/2216:28
 */
@RestController
@RequestMapping("/wxpay")
public class WXPayController {
    @Autowired
    private WXPayService wxPayService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //下单
    @GetMapping("/nativePay")
    public Result nativePay(@RequestParam("orderId") String orderId, @RequestParam("money") Integer money) {
        Map resultMap = wxPayService.nativePay(orderId, money);
        return new Result(true, StatusCode.OK, "", resultMap);
    }

    /**
     * 关闭微信订单
     *
     * @param
     * @return
     */
    @PutMapping("/close/{orderId}")
    public Result closeOrder(@PathVariable String orderId) {
        Map map = wxPayService.closeOrder(orderId);
        return new Result(true, StatusCode.OK, "关闭成功", map);
    }

    /**
     * 查询微信订单
     *
     * @param
     * @return
     */
    @GetMapping("/query/{orderId}")
    public Result queryOrder(@PathVariable String orderId) {
        Map map = wxPayService.queryOrder(orderId);
        return new Result(true, StatusCode.OK, "查询成功", map);
    }


    @RequestMapping("/notify")
    public void notifyLogic(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("支付成功回调");
        //输入流转换为字符串
        try {
            String xml = ConvertUtils.convertToString(request.getInputStream());
            System.out.println(xml);
            //基于微信发送的通知内容,完成后续的业务逻辑处理
            Map<String, String> map = WXPayUtil.xmlToMap(xml);
            if ("SUCCESS".equals(map.get("result_code"))) {
                //查询订单
                Map result = wxPayService.queryOrder(map.get("out_trade_no"));
                System.out.println("订单查询结果" + map.get("out_trade_no"));
                if ("SUCCESS".equals(result.get("result_code"))) {
                    //将消息发送到mq
                    Map message = new HashMap();
                    message.put("orderId", result.get("out_trade_no"));
                    message.put("transactionId", result.get("transaction_id"));
                    System.out.println(message);
                    //消息发送
                    rabbitTemplate.convertAndSend("", RabbitMQConfig.ORDER_PAY, JSON.toJSONString(message));
                    //完成双向通信
                    rabbitTemplate.convertAndSend("paynotify", "", result.get("out_trade_no"));
                } else {
                    //报错原因
                    System.out.println(map.get("err_code_des"));
                }
            } else {
                //报错原因
                System.out.println(map.get("err_code_des"));
            }
            //给微信一个结果通知
            response.setContentType("text/html");
            String data = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
            response.getWriter().write(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
