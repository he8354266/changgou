package com.changgou.pay.service;

import java.util.Map;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/2210:34
 */
public interface WXPayService {
    Map nativePay(String orderId, Integer money);

    //基于微信查询订单
    Map queryOrder(String orderId);

    Map closeOrder(String orderId);
}
