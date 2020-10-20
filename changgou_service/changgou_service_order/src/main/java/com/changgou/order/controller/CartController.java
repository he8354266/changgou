package com.changgou.order.controller;

import com.changgou.order.service.CartService;
import com.changgou.pojo.Result;
import com.changgou.pojo.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/1510:33
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/addCart")
    public Result addCart(@RequestParam("skuId") String skuId, @RequestParam("num") Integer num) {
        String username = "itcast";
        cartService.addCart(skuId, num, username);
        return new Result(true, StatusCode.OK, "加入购物车成功");
    }

    @GetMapping("/list")
    public Map list() {
        String username = "itcast";
        Map map = cartService.list(username);
        return map;
    }
}
