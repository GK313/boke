package com.gjy.boke.controller;

import com.gjy.boke.entity.Blog;
import com.gjy.boke.entity.Tag;
import com.gjy.boke.entity.Type;
import com.gjy.boke.queryvo.BlogTypeQuery;
import com.gjy.boke.service.BlogService;
import com.gjy.boke.service.TagService;
import com.gjy.boke.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author GJY
 * @Date 2021/1/30 15:59
 * @Version 1.0
 */
@Controller
public class indexController {
    @Resource
    private BlogService blogService;
    @Resource
    private TypeService typeService;
    @Resource
    private TagService tagService;

    @RequestMapping("/")
    public String index(Model model){
        //采用分页获取所有博客信息
        List<Blog> blogs = blogService.getAllBlog();
        //获取每个分类下的博客数量降序的前n条分类信息
        List<BlogTypeQuery> types = blogService.getBlogTypeQueryVO();

        //获取标签中对应博客数量的前4个标签记录(未实现)
        //List<Tag> tags = tagService.getTagByLimit();

        //按照更新时间查询前9条博客信息
        List<Blog> blogLimit = blogService.getBlogByUpdateTimeLimit();

        //计算已发布博客数量
        int blogNumber = blogService.getBlogNumber();
        model.addAttribute("blogNumber",blogNumber);
        model.addAttribute("types",types);
        model.addAttribute("blogs",blogs);
        model.addAttribute("blogLimit",blogLimit);
        return "index";
    }

    /**
     * 查看更多分类
     * @return
     */
    @RequestMapping("/moreType")
    public String moreType(){
        return "types";
    }

    /**
     * 查看更多标签
     * @return
     */
    @RequestMapping("/moreTag")
    public String moreTag(){
        return "tags";
    }
}
