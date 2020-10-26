package com.changgou.pay.feign;

import com.changgou.pojo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/239:33
 */
@FeignClient(name = "pay")
public interface PayFeign {
    @GetMapping("/wxpay/nativePay")
    public Result nativePay(@RequestParam("orderId") String orderId, @RequestParam("money") Integer money);
}
