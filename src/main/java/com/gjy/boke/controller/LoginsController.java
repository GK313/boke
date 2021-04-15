package com.gjy.boke.controller;

import com.gjy.boke.entity.Blog;
import com.gjy.boke.entity.Guser;
import com.gjy.boke.entity.Tag;
import com.gjy.boke.queryvo.BlogTypeQuery;
import com.gjy.boke.service.BlogService;
import com.gjy.boke.service.CommentService;
import com.gjy.boke.service.TypeService;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @Author GJY
 * @Date 2021/4/5 20:08
 * @Version 1.0
 */
@Controller
public class LoginsController {

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private BlogService blogService;


    @RequestMapping("/logins")
    public String logins(Guser guser,@RequestParam String code, Model model, HttpSession session){
        HashOperations ops = redisTemplate.opsForHash().getOperations().opsForHash();
        //判断redis中是否存在键为phone的记录，存在则直接登录，不存在则先进行注册
        if(ops.hasKey(guser.getPhone(),"password")){
            //存在该用户则进行密码校验
            if(ops.get(guser.getPhone(),"password").equals(guser.getPasswd())){
                //查询首页所有信息并返回
                //采用分页获取所有博客信息
                List<Blog> blogs = blogService.getAllBlog();
                //获取每个分类下的博客数量降序的前n条分类信息
                List<BlogTypeQuery> types = blogService.getBlogTypeQueryVO();

                //获取标签中对应的博客及其数量
                List<Tag> blogsInTag = blogService.getBlogInTag();
                //利用集合的迭代器进行遍历集合时删除集合元素
                Iterator<Tag> iterator = blogsInTag.iterator();
                while(iterator.hasNext()){
                    if(iterator.next().getBlogs().size()==0){
                        //移除那些对应博客数量为0的标签
                        iterator.remove();
                    }
                }
                //根据博客数量进行排序
                Collections.sort(blogsInTag);

                //按照更新时间查询前9条博客信息(设置为推荐的博客)
                List<Blog> blogLimit = blogService.getBlogByUpdateTimeLimit();

                //计算已发布博客数量
                int blogNumber = blogService.getBlogNumber();
                session.setAttribute("user",guser);
                model.addAttribute("blogNumber",blogNumber);
                model.addAttribute("types",types);
                model.addAttribute("blogs",blogs);
                model.addAttribute("blogLimits",blogLimit);
                model.addAttribute("blogsInTag", blogsInTag);

                return "index";
            }else{
                return "logins";
            }
        }else{
            //不存在该用户进行用户注册
        }
        return "logins";

    }
}
