package com.changgou.web.order.controller;

import com.changgou.order.feign.OrderFeign;
import com.changgou.order.pojo.Order;
import com.changgou.pay.feign.PayFeign;
import com.changgou.user.feign.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/2217:38
 */
@Controller
@RequestMapping("/wxpay")
public class PayController {
    @Autowired
    private OrderFeign orderFeign;

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private PayFeign payFeign;

    //跳转到微信支付二维码页面
    @GetMapping
    public String wxPay(String orderId, Model model) {
        //1.根据orderid查询订单,如果订单不存在,跳转到错误页面
        Order order = (Order) orderFeign.findById(orderId).getData();
        if (order == null) {
            return "fail";
        }
        //2.根据订单的支付状态进行判断,如果不是未支付的订单,跳转到错误页面
        if (!"0".equals(order.getPayStatus())) {
            return "fail";
        }
        //3.基于payFeign调用统计下单接口,并获取返回结果
        Map resultMap = (Map) payFeign.nativePay(orderId, order.getPayMoney()).getData();

        if (resultMap == null) {
            return "fail";
        }
        //4.封装结果数据
        resultMap.put("orderId", orderId);
        resultMap.put("payMoney", order.getPayMoney());
        System.out.println(resultMap);
        model.addAllAttributes(resultMap);
        return "wxpay";
    }

    //支付成功页面的跳转
    @RequestMapping("/toPaySuccess")
    public String toPaySuccess(Integer payMoney, Model model) {
        model.addAttribute("payMoney", payMoney);
        return "paysuccess";
    }
}
