package com.changgou.user.feign;

import com.changgou.pojo.Result;
import com.changgou.user.pojo.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/1910:42
 */
@FeignClient(name = "user")
public interface AddressFeign {
    @GetMapping("/address/list")
    public Result<List<Address>> list();
}
