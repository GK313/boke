package com.gjy.boke.controller;

import com.gjy.boke.Config.Receiver;
import com.gjy.boke.entity.*;
import com.gjy.boke.queryvo.BlogTypeQuery;
import com.gjy.boke.service.BlogService;
import com.gjy.boke.service.CommentService;
import com.gjy.boke.service.TagService;
import com.gjy.boke.service.TypeService;
import com.gjy.boke.utils.ViewsUtil;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

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
    private CommentService commentService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    ViewsUtil viewsUtil;

    @RequestMapping("/")
    public String index(Model model,HttpSession session) {
        //判断当前是否登录，如果已经登录，根据用户id从缓存中查询出当前用户所订阅的分类名称
        //用户订阅某个分类时，以该分类的名称为队列与direct模式的交换机进行绑定，routing key为分类的名称

        //先查看redis中是否已经存在数据
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        List<Blog> blogs=(List<Blog>)ops.get("blogs");
        List<BlogTypeQuery> types =(List<BlogTypeQuery>)ops.get("types");
        List<Blog> blogLimits = (List<Blog>)ops.get("blogLimits");
        if (blogs!=null && types!=null && blogLimits!=null){
            //取出存放在里面的博客数据
            blogs = (List<Blog>) ops.get("blogs");
            types = (List<BlogTypeQuery>) ops.get("types");
            blogLimits = (List<Blog>) ops.get("blogLimits");
        } else {
            //采用分页获取所有博客信息
            blogs = blogService.getAllBlog();
            //获取每个分类下的博客数量降序的前n条分类信息
            types = blogService.getBlogTypeQueryVO();
            //按照更新时间查询前9条博客信息(设置为推荐的博客)
            blogLimits = blogService.getBlogByUpdateTimeLimit();
            ops.set("blogs",blogs);;
            ops.set("types",types);;
            ops.set("blogLimits",blogLimits);;
        }

        //获取标签中对应的博客及其数量
        List<Tag> blogsInTag = blogService.getBlogInTag();
        //利用集合的迭代器进行遍历集合时删除集合元素
        Iterator<Tag> iterator = blogsInTag.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getBlogs().size() == 0) {
                //移除那些对应博客数量为0的标签
                iterator.remove();
            }
        }

       /* //采用分页获取所有博客信息
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
        List<Blog> blogLimit = blogService.getBlogByUpdateTimeLimit();*/
        //计算已发布博客数量
        int blogNumber = blogService.getBlogNumber();
        model.addAttribute("blogNumber", blogNumber);
        model.addAttribute("types", types);
        model.addAttribute("blogs", blogs);
        model.addAttribute("blogLimits", blogLimits);
        model.addAttribute("blogsInTag", blogsInTag);
        if(session.getAttribute("user")!=null){
            User user = (User)session.getAttribute("user");
            session.setAttribute("collectionSize",blogService.getCollectionsById(user.getId()).size());
            if(user.getNickname()!="admin"){
                //不将admin计入用户活跃量
                viewsUtil.setViews(user.getId());
            }
        }
        return "index";
    }

    /**
     * 全局搜索
     *
     * @param query
     */
    @PostMapping("/search")
    public String searchblog(@RequestParam String query, Model model) {
        List<Blog> blogs = blogService.listBlogSearchByQuery(query);
        if (blogs.size() == 0) {
            model.addAttribute("message", "还没有相关的文章哦@——@");
        }
        model.addAttribute("query", query);
        model.addAttribute("blogs", blogs);
        return "result";
    }

    /**
     * 查看具体博客信息,跳转到博客详情页
     */
    @GetMapping("/blog/{id}")
    public String getBlogByid(@PathVariable Long id, Model model, HttpSession session) {
        blogService.updateBlogViewsAddOneByBlogId(id);
        //根据id获取博客信息
        Blog blog = blogService.getBlogById(id);
        List<Comment> comments = commentService.listCommentByBlogIdParentIdNull(id, null);

        //获取当前用户
        User user = (User) session.getAttribute("user");
        if(user!=null){
            //查询当前用户是否已经收藏
            int i = blogService.getCollectionByBlogIdAndUserId(id, user.getId());
            if(i>0){
                //已收藏
                model.addAttribute("collection","取消收藏");
            }else{
                model.addAttribute("collection","收藏");
            }
        }

        model.addAttribute("blog", blog);
        model.addAttribute("comments", comments);
        return "blog";
    }

    /**
     * 根据分类id获取该分类下的所有博客
     */
    @GetMapping("/type/{id}")
    public String getBlogByTypeId(@PathVariable Long id, Model model) {
        System.out.println(id);
        List<Blog> blogs = blogService.getBlogByTypeId(id);
        List<Type> types = typeService.getAllTypeAndBlog();
        Collections.sort(types);
        if (blogs.size() != 0) {
            model.addAttribute("blogs", blogs);
            /*attributes.addFlashAttribute("blogs",blogs);*/
        } else {
            model.addAttribute("message", "该分类下还没有文章，关注博主，等待博主更新吧@_@");
            /* attributes.addFlashAttribute("message","该分类下还没有文章，关注博主，等待博主更新吧@_@");*/
        }
        model.addAttribute("types", types);
        model.addAttribute("activeTypeId", id);
        /*attributes.addFlashAttribute("types",types);*/
        return "types";

    }

    /**
     * 根据标签id获取对应的所有博客
     *
     * @param model
     * @return
     */
    @GetMapping("/tag/{id}")
    public String getBlogByTagId(Model model, @PathVariable Long id) {
        List<Tag> blogsList = blogService.getBlogInTag();
        Collections.sort(blogsList);
        List<Tag> blog_tagList = blogService.getBlogInTagByTagId(id);

        if (blog_tagList.size() != 0) {
            List<Blog> blogs = blog_tagList.get(0).getBlogs();
            //为该标签下的每个blog赋type属性
            for (Blog blog : blog_tagList.get(0).getBlogs()) {
                blog.setType(typeService.getTypeById(blog.getTypeid()));
                System.out.println(blog.getUpdatetime());
            }
            model.addAttribute("blogs", blogs);
        } else {
            model.addAttribute("message", "该标签下还没有文章，关注博主，等待博主更新吧@_@");
        }

        model.addAttribute("blogsList", blogsList);
        model.addAttribute("activeTagId", id);

        return "tags";
    }

    @PostMapping("/searchBlog")
    public String getBlogs(@RequestParam String searchCondition, Model model) {
        //判断是要根据那个条件显示博客
        if (searchCondition.equals("hot")) {
            //获取热门的文章集合
            List<Blog> blogs = blogService.ListBlogOrderByBlogViewsAndCommentCount();
            model.addAttribute("blogs", blogs);
            model.addAttribute("flag", "hot");
        } else {
            //获取最新的文章集合
            List<Blog> blogs = blogService.ListBlogOrderByUpdateTime();
            model.addAttribute("blogs", blogs);
            model.addAttribute("flag", "new");
        }

        return "index::bloglistSearched";
    }

    @GetMapping("/collections/{id}")
    public String getMessage(@PathVariable Long id) {
        System.out.println(id);
        return "collections";
    }

    @GetMapping("/vuetest")
    @ResponseBody
    public List<Type> getTyle() {
        return typeService.getTypeList();
    }

    @GetMapping("/datetest")
    public String dateTest() {
        return "date";
    }

}
