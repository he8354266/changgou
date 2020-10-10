package com.changgou.search.controller;

import com.changgou.pojo.Result;
import com.changgou.pojo.StatusCode;
import com.changgou.search.service.ESManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/911:35
 */
@RestController
@RequestMapping("/manager")
public class ESManagerController {
    @Autowired
    private ESManagerService esManagerService;
    //创建索引结构
    @GetMapping("/create")
    public Result create(){
        esManagerService.createMappingAndIndex();
        return new Result(true,StatusCode.OK,"创建索引库结构成功");
    }
    //导入全部数据
    @GetMapping("/importAll")
    public Result importAll(){
        esManagerService.importAll();
        return new Result(true, StatusCode.OK,"导入全部数据成功");
    }
}
