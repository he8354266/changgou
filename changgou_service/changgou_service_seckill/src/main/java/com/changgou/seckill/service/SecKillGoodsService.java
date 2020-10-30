package com.changgou.seckill.service;

import com.changgou.seckill.pojo.SeckillGoods;

import java.util.List;

public interface SecKillGoodsService {

    List<SeckillGoods> list(String time);

    void add(String time, String id);
}
