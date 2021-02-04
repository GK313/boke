package com.gjy.boke.controller;

import com.gjy.boke.entity.Blog;
import com.gjy.boke.entity.Type;
import com.gjy.boke.queryvo.BlogTypeQuery;
import com.gjy.boke.service.BlogService;
import com.gjy.boke.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author GJY
 * @Date 2021/1/31 14:27
 * @Version 1.0
 */
@Controller
@RequestMapping("/type")
public class TypeShowController {
    @Resource
    private BlogService blogService;

    @Resource
    private TypeService typeService;
    /**
     * 点击首页的more或者导航栏的分类，跳转到分类首页
     * @return
     */
    @RequestMapping("/typeBlog")
    public String types(Model model){
       /* //获取所有分类以及每个分类对应的博客数量，即BlogTypeQuery
        List<BlogTypeQuery> types = blogService.getBlogTypeQueryVO();*/
        List<Type> types = typeService.getAllTypeAndBlog();
        //获取所有博客
        List<Blog> blogs = blogService.getAllBlog();
        model.addAttribute("types",types);
        model.addAttribute("blogs",blogs);
        return "types";
    }


}
