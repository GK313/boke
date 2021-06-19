package com.gjy.boke.controller;

import com.gjy.boke.service.BlogService;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @Author GJY
 * @Date 2021/4/5 20:08
 * @Version 1.0
 */
@Controller
public class LoginsController {
    //cab daac acd adcb
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private BlogService blogService;


    @RequestMapping("/logins")
    public String logins(@RequestParam String code, Model model, HttpSession session){

        return "admin/index";

    }
}
