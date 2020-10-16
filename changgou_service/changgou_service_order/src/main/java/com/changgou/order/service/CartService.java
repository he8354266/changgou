package com.changgou.order.service;

import java.util.Map;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/1510:02
 */
public interface CartService {
    //添加购物车
    void addCart(String skuId,Integer num,String username);
    //查询购物车数据
    Map list(String username);
}
