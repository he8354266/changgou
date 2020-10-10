package com.changgou.goods.handler;

import com.changgou.pojo.Result;
import com.changgou.pojo.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Title: 统一异常处理
 * @Package * @Description:     * @author CodingSir
 * @date 2020/9/2317:43
 */
@ControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler
   @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return new Result(false,StatusCode.ERROR,"查询出错");
    }
}
