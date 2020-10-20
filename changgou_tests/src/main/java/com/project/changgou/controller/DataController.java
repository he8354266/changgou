package com.project.changgou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/199:17
 */
@Controller
@RequestMapping("/data")
public class DataController {
    @RequestMapping("test")
    public String data() {
        return "11111";
    }
}
