package com.changgou.seckill.feign;

import com.changgou.pojo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/3014:17
 */
@FeignClient(name = "seckill")
public interface SecKillOrderFeign {
    @RequestMapping("/seckill/add")
    public Result add(@RequestParam("time") String time, @RequestParam("id") Long id);
}
