package com.changgou.seckill.web.aspect;

import java.lang.annotation.*;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/3015:43
 */
@Inherited
@Documented
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {
}
