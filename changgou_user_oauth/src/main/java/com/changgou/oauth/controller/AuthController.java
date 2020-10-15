package com.changgou.oauth.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/1414:32
 */
@Controller
@RequestMapping("/oauth")
public class AuthController{
    @Value("${auth.clientId}")
    private  String clientId;
    @Value("${auth.clientSecret}")
    private  String clientSecret;

    @Value("${auth.cookieDomain}")
    private String cookieDomain;

    @Value("${auth.cookieMaxAge}")
    private int cookieMaxAge;

//    @RequestMapping("/toLogin")
//    public String toLogin(){
//        return "login";
//    }
//    @RequestMapping("/login")
//    @ResponseBody
//    public Result login(String username, String password, HttpServletResponse response){
//        if(StringUtils.isEmpty(username)){
//            throw new RuntimeException("请输入用户名");
//        }
//        if (StringUtils.isEmpty(password)){
//            throw new RuntimeException("请输入密码");
//        }
//        //申请令牌
//return null;
//    }
}
