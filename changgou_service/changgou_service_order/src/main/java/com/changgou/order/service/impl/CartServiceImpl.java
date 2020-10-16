package com.changgou.order.service.impl;

import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.feign.SpuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.goods.pojo.Spu;
import com.changgou.order.pojo.OrderItem;
import com.changgou.order.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/1510:05
 */
@Service
public class CartServiceImpl implements CartService {
    private final static String CART = "cart_";
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SkuFeign skuFeign;
    @Autowired
    private SpuFeign spuFeign;

    @Override
    public void addCart(String skuId, Integer num, String username) {
        //查询redis购物车信息
        OrderItem orderItem = (OrderItem) redisTemplate.boundHashOps(CART + username).get(skuId);
        if (orderItem != null) {

            if (orderItem.getNum() <= 0) {
                //删除该商品
                redisTemplate.boundHashOps(CART + username).delete(skuId);
                return;
            }
            //2.如果当前商品在redis中的存在,则更新商品的数量与价钱
            orderItem.setNum(orderItem.getNum() + num);
            orderItem.setMoney(orderItem.getNum() * orderItem.getPrice());
            orderItem.setPayMoney(orderItem.getNum() * orderItem.getPrice());
        } else {
            //3.如果当前商品在redis中不存在,将商品添加到redis中
            Sku sku = skuFeign.findById(skuId).getData();
            System.out.println(sku.getSpuId());
            Spu spu = spuFeign.findSpuById(sku.getSpuId()).getData();
            if(spu!=null){
                //封装orderItem
                orderItem = this.sku2OrderItem(sku, spu, num);
            }else{
                throw new RuntimeException("spu数据为空");
            }


            redisTemplate.boundHashOps(CART + username).put(skuId, orderItem);
        }
    }

    private OrderItem sku2OrderItem(Sku sku, Spu spu, Integer num) {
        OrderItem orderItem = new OrderItem();
        orderItem.setSkuId(sku.getId());
        orderItem.setSpuId(sku.getSpuId());
        orderItem.setName(sku.getName());
        orderItem.setPrice(sku.getPrice());
        orderItem.setNum(num);
        orderItem.setMoney(sku.getPrice() * num);
        orderItem.setPayMoney(sku.getPrice() * num);
        orderItem.setImage(sku.getImage());
        orderItem.setWeight(sku.getWeight() * num);
        //分类信息
        orderItem.setCategoryId1(spu.getCategory1Id());
        orderItem.setCategoryId2(spu.getCategory2Id());
        orderItem.setCategoryId3(spu.getCategory3Id());
        return orderItem;
    }

    //查询购物车列表数据
    @Override
    public Map list(String username) {
        Map map = new HashMap();
        List<OrderItem> orderItemList = redisTemplate.boundHashOps(CART + username).values();
        map.put("orderItemList", orderItemList);
        //商品的总数量与总价格
        Integer totalNum = 0;
        Integer totalMoney = 0;
        for (OrderItem orderItem : orderItemList) {
            totalNum += orderItem.getNum();
            totalMoney += orderItem.getMoney();
        }
        map.put("totalNum", totalNum);
        map.put("totalMoney", totalMoney);
        return map;
    }
}
