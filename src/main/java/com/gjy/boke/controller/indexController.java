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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

        //获取标签中对应的博客及其数量
        List<Tag> blogsInTag = blogService.getBlogInTag();

        //按照更新时间查询前9条博客信息(设置为推荐的博客)
        List<Blog> blogLimit = blogService.getBlogByUpdateTimeLimit();

        //计算已发布博客数量
        int blogNumber = blogService.getBlogNumber();
        model.addAttribute("blogNumber",blogNumber);
        model.addAttribute("types",types);
        model.addAttribute("blogs",blogs);
        model.addAttribute("blogLimits",blogLimit);
        model.addAttribute("blogsInTag",blogsInTag);
        return "index";
    }


    /**
     * 查看具体博客信息,跳转到博客详情页
     */
    @GetMapping("/blog/{id}")
    public String getBlogByid(@PathVariable Long id,Model model){
        //根据id获取博客信息
        Blog blog = blogService.getBlogById(id);
        model.addAttribute("blog",blog);
        return "blog";
    }

    /**
     * 根据分类id获取该分类下的所有博客
     */
    @GetMapping("/type/{id}")
    public String getBlogByTypeId(@PathVariable Long id, RedirectAttributes attributes,Model model){
        List<Blog> blogs = blogService.getBlogByTypeId(id);
        List<Type> types = typeService.getAllTypeAndBlog();
        if(blogs.size()!=0){
            model.addAttribute("blogs",blogs);
            /*attributes.addFlashAttribute("blogs",blogs);*/
        }else{
            model.addAttribute("message","该分类下还没有文章，关注博主，等待博主更新吧@_@");
           /* attributes.addFlashAttribute("message","该分类下还没有文章，关注博主，等待博主更新吧@_@");*/

        }

        model.addAttribute("types",types);
        /*attributes.addFlashAttribute("types",types);*/
        return "types";
    }
}
