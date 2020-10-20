package com.changgou.user.feign;

import com.changgou.pojo.Result;
import com.changgou.user.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/1411:37
 */
@FeignClient(name = "user")
public interface UserFeign {

    @GetMapping("/user/load/{username}")
    public User findUserInfo(@PathVariable("username") String username);


    @GetMapping("/user/points/add")
    public Result pointsAdd(@RequestParam(value = "points") Integer points);
}
