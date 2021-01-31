package com.gjy.boke.controller;
import com.gjy.boke.dao.TypeDao;
import com.gjy.boke.entity.Type;
import com.gjy.boke.entity.User;
import com.gjy.boke.service.TypeService;
import com.gjy.boke.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
public class HelloController {
    @Resource
    UserService userService;
    @Resource
    TypeService typeService;


    @GetMapping("/blog")
    public String test2(){
        return "blog";
    }

    @GetMapping("/404")
    public String test3(){
        return "error/404";
    }

    @GetMapping("/500")
    public String test4(){
        return "error/500   ";
    }

    @RequestMapping("/tags")
    public String test6(){
        return "/tags";
    }

    @RequestMapping("/types")
    public String test5(){
        return "/types";
    }

    @RequestMapping("/test2")
    public void test7(){
        Type type = typeService.getTypeById(1l);
        System.out.println(type.getName());
    }

    @RequestMapping("/blogs")
    public String test8(){
        return "/admin/blogs";
    }
}
