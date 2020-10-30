package com.changgou.consume.service.impl;

import com.changgou.consume.dao.SeckillGoodsMapper;
import com.changgou.consume.dao.SeckillOrderMapper;
import com.changgou.consume.service.SecKillOrderService;
import com.changgou.seckill.pojo.SeckillOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/3011:54
 */
@Service
public class SecKillOrderServiceImpl implements SecKillOrderService {
    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;
    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    @Override
    @Transactional
    public int createOrder(SeckillOrder seckillOrder) {
        //同步mysql中的数据
        //1.扣减秒杀商品的库存
        int result = seckillGoodsMapper.updateStockCount(seckillOrder.getId());
        if (result <= 0) {
            return 0;
        }
        //2.新增秒杀订单
        result = seckillOrderMapper.insertSelective(seckillOrder);
        if (result <= 0) {
            return 0;
        }
        return 1;
    }
}
