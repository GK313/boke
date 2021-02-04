package com.gjy.boke.controller;

import com.gjy.boke.entity.Blog;
import com.gjy.boke.entity.Tag;
import com.gjy.boke.service.BlogService;
import com.gjy.boke.service.TagService;
import com.gjy.boke.service.TypeService;
import org.mybatis.generator.logging.AbstractLogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author GJY
 * @Date 2021/1/31 14:32
 * @Version 1.0
 */
@Controller
@RequestMapping("/tag")
public class TagShowController {
    @Resource
    private TagService tagService;
    @Resource
    private BlogService blogService;
    @Resource
    private TypeService typeService;
    /**
     * 点击首页的more或者导航栏的标签，跳转到标签首页
     * @return
     */
    @RequestMapping("/tagBlog")
    public String tagblog(Model model){
        List<Tag> blogsList = blogService.getBlogInTag();
        List<Blog> blogs = blogService.getAllBlog();
        model.addAttribute("blogsList",blogsList);
        model.addAttribute("blogs",blogs);
        return "tags";
    }

    /**
     * 根据标签id获取对应的所有博客
     * @param model
     * @return
     */
    @RequestMapping("/bloglist/{id}")
    public String getBlogByTagId(Model model, @PathVariable Long id) {
        List<Tag> blogsList = blogService.getBlogInTag();
        List<Tag> blog_tagList = blogService.getBlogInTagByTagId(id);
        if(blog_tagList.size()!=0){
            List<Blog> blogs = blog_tagList.get(0).getBlogs();
            //为该标签下的每个blog赋type属性
            for (Blog blog : blog_tagList.get(0).getBlogs()) {
                blog.setType(typeService.getTypeById(blog.getTypeid()));
            }
            model.addAttribute("blogs",blogs);
        }else{
            model.addAttribute("message","该标签下还没有文章，关注博主，等待博主更新吧@_@");
        }
        model.addAttribute("blogsList",blogsList);
        return "tags";
    }
}
