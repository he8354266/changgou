package com.changgou.goods.feign;

import com.changgou.goods.pojo.Spu;
import com.changgou.pojo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "goods")
public interface SpuFeign {

    @GetMapping("/spu/findSpuById/{id}")
    public Result<Spu> findSpuById(@PathVariable("id") String id);
}
