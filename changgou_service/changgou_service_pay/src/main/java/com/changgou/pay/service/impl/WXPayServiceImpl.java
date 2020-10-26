package com.changgou.pay.service.impl;

import com.changgou.pay.service.WXPayService;

import github.wxpay.sdk.WXPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/2210:35
 */
@Service
public class WXPayServiceImpl implements WXPayService {
    @Value("${wxpay.notify_url}")
    private String notify_url;
    @Autowired
    private WXPay wxPay;
    @Override
    public Map nativePay(String orderId, Integer money) {

        try {
            //1. 封装请求参数
            Map<String, String> map = new HashMap<>();
            map.put("body","畅购");
            map.put("out_trade_no",orderId);
            BigDecimal payMoney =  new BigDecimal("0.01");
            BigDecimal fen = payMoney.multiply(new BigDecimal("100"));//1.00
            fen = fen.setScale(0,BigDecimal.ROUND_UP);//1

            map.put("total_fee",String.valueOf(fen));
            map.put("spbill_create_ip","127.0.0.1");
            map.put("notify_url",notify_url);
            map.put("trade_type","NATIVE");
            Map<String, String> result = wxPay.unifiedOrder(map);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Map queryOrder(String orderId) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("out_trade_no",orderId);
            Map<String, String> resultMap = wxPay.orderQuery(map);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
