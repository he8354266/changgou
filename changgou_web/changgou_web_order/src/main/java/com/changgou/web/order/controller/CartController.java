package com.changgou.web.order.controller;

import com.changgou.order.feign.CartFeign;
import com.changgou.pojo.Result;
import com.changgou.pojo.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/1511:54
 */
@Controller
@RequestMapping("/wcart")
public class CartController {
    @Autowired
    private CartFeign cartFeign;

    //查询
    @GetMapping("/list")
    public String list(Model modle) {
        Map map = cartFeign.list();
        System.out.println(map);
        modle.addAttribute("items", map);
        return "cart";
    }

    @GetMapping("/add")
    @ResponseBody
    public Result<Map> add(String id, Integer num) {
        cartFeign.addCart(id, num);
        Map map = cartFeign.list();
        return new Result<>(true, StatusCode.OK, "添加购物车成功", map);
    }
}
