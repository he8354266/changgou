package com.changgou.seckill.controller;


import com.changgou.pojo.Result;
import com.changgou.pojo.StatusCode;
import com.changgou.seckill.pojo.SeckillGoods;
import com.changgou.seckill.service.SecKillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seckillgoods")
public class SecKillGoodsController {

    @Autowired
    private SecKillGoodsService secKillGoodsService;

    @RequestMapping("/list")
    public Result<List<SeckillGoods>> list(@RequestParam("time") String time) {
        List<SeckillGoods> seckillGoodsList = secKillGoodsService.list(time);
        return new Result<>(true, StatusCode.OK, "查询成功", seckillGoodsList);
    }

    @RequestMapping("/add")
    public Result add(@RequestParam("time") String time, @RequestParam("id") String id) {
        secKillGoodsService.add(time, id);
        return new Result(true, StatusCode.OK, "添加成功");
    }
}
