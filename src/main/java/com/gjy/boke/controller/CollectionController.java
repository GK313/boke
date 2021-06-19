package com.gjy.boke.controller;

import com.gjy.boke.entity.Blog;
import com.gjy.boke.entity.User;
import com.gjy.boke.service.BlogService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @Author GJY
 * @Date 2021/6/18 11:45
 * @Version 1.0
 */
@Controller
public class CollectionController {
    @Resource
    BlogService  blogService;
    @Resource
    RedisTemplate redisTemplate;

    //收藏博客
    @PostMapping("/collect")
    public ModelAndView collectionBlog(@RequestParam Long blogId, HttpSession session, Model model) {
        System.out.println(blogId);
        //获取当前用户
        User user = (User) session.getAttribute("user");
        if(user!=null){
            int i = blogService.insertCollection(blogId, user.getId());
            if(i>0){
                model.addAttribute("message","收藏成功");
            }else{
                model.addAttribute("message","收藏失败");
            }
        }else{
            model.addAttribute("message","赶紧去登录吧，收藏你喜欢的文章");
        }
        //更新session的收藏值
        session.setAttribute("collectionSize",blogService.getCollectionsById(user.getId()).size());

        return new ModelAndView("message");
    }

    //获取收藏的文章
    @GetMapping("/getCollection")
    public String getCollections(HttpSession session,Model model) {
        //获取当前用户
        User user = (User) session.getAttribute("user");
        List<Blog> blogs = blogService.getCollectionsById(user.getId());
        model.addAttribute("blogs",blogs);
        session.setAttribute("collectionSize",blogService.getCollectionsById(user.getId()).size());
        return "collections";
    }

    //取消收藏
    @PostMapping("/deleteCollect")
    public ModelAndView deleteCollect(@RequestParam Long blogId, HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        int i = blogService.deleteCollect(blogId, user.getId());
        if(i>0){
            model.addAttribute("message","取消收藏成功");
            //更新收藏数
            session.setAttribute("collectionSize",blogService.getCollectionsById(user.getId()).size());
        }else{
            model.addAttribute("message","取消收藏失败");
        }
        return new ModelAndView("message");
    }

    //跳转到公告列表页面
    @RequestMapping("/inform")
    public String process() {

        return "inform";
    }


}
