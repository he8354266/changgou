package com.changgou.seckill.service.impl;

import com.changgou.seckill.dao.SeckillGoodsMapper;
import com.changgou.seckill.pojo.SeckillGoods;
import com.changgou.seckill.service.SecKillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecKillGoodsServiceImpl implements SecKillGoodsService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    public static final String SECKILL_GOODS_KEY = "seckill_goods_";

    @Override
    public List<SeckillGoods> list(String time) {
        List<SeckillGoods> list = redisTemplate.boundHashOps(SECKILL_GOODS_KEY + time).values();
        System.out.println(list);
        return list;
    }

    @Override
    public void add(String time, String id) {
        SeckillGoods seckillGoods = seckillGoodsMapper.selectByPrimaryKey(id);
        redisTemplate.boundHashOps(SECKILL_GOODS_KEY + time).put("seckillGoods",seckillGoods);
    }
}
