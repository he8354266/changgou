package com.changgou.seckill.controller;

import com.changgou.pojo.Result;
import com.changgou.pojo.StatusCode;
import com.changgou.seckill.service.SecKillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/3010:19
 */
@RestController
@RequestMapping("/seckillorder")
public class SecKillOrderController {
    @Autowired
    private SecKillOrderService secKillOrderService;

    @RequestMapping("/add")
    public Result add(@RequestParam("time") String time, @RequestParam("id") Long id) {
        String username = "itcast";

        //2.基于业务层进行秒杀下单
        boolean result = secKillOrderService.add(id, time, username);

        if(result){
            return new Result(true, StatusCode.OK,"下单成功");
        }else{
            return new Result(false,StatusCode.ERROR,"下单失败");
        }
    }
}
