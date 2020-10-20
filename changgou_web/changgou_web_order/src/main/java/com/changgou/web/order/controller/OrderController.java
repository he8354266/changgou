package com.changgou.web.order.controller;

import com.changgou.order.feign.CartFeign;
import com.changgou.order.feign.OrderFeign;
import com.changgou.order.pojo.Order;
import com.changgou.order.pojo.OrderItem;
import com.changgou.pojo.Result;
import com.changgou.user.feign.AddressFeign;
import com.changgou.user.pojo.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/1910:37
 */
@Controller
@RequestMapping("/worder")
public class OrderController {
    @Autowired
    private CartFeign cartFeign;
    @Autowired
    private AddressFeign addressFeign;
    @Autowired
    private OrderFeign orderFeign;

    @RequestMapping("/ready/order")
    public String readyOrder(Model model) {
        List<Address> addressList = addressFeign.list().getData();
        model.addAttribute("address", addressList);
        //购物车信息
        Map map =  cartFeign.list();


        List<OrderItem> orderItemList = (List<OrderItem>) map.get("orderItemList");
        System.out.println(orderItemList);
        Integer totalMoney = (Integer) map.get("totalMoney");
        Integer totalNum = (Integer) map.get("totalNum");
        model.addAttribute("carts", orderItemList);
        model.addAttribute("totalMoney", totalMoney);
        model.addAttribute("totalNum", totalNum);
        //默认收件人信息
        for (Address address : addressList) {
            if ("1".equals(address.getIsDefault())) {
                //默认收件人
                model.addAttribute("deAddr", address);
                break;
            }
        }
        return "order";
    }

    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody Order order) {
        Result result = orderFeign.add(order);
        return result;
    }
}
