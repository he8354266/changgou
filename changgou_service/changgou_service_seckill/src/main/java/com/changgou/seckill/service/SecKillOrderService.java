package com.changgou.seckill.service;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/3010:20
 */
public interface SecKillOrderService {
    //秒杀下单
    boolean add(Long id, String time, String usernmae);
}
