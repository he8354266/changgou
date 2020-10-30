package com.changgou.consume.dao;

import com.changgou.seckill.pojo.SeckillGoods;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/3011:50
 */
public interface SeckillGoodsMapper extends Mapper<SeckillGoods> {
    @Update("update tb_seckill_goods set stock_count=stock_count-1 where id=#{id} and stock_count>=1")
    int updateStockCount(@Param("id") long id);
}
