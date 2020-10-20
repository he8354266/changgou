package com.changgou.order.feign;

import com.changgou.order.pojo.Order;
import com.changgou.pojo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/1911:18
 */
@FeignClient(name = "order")
public interface OrderFeign {
    @PostMapping("/order")
    public Result add(@RequestBody Order order);
}
