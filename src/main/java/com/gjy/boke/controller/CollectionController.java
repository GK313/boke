package com.gjy.boke.controller;

import com.gjy.boke.dao.TypeDao;
import com.gjy.boke.entity.Blog;
import com.gjy.boke.entity.User;
import com.gjy.boke.queryvo.BtVo;
import com.gjy.boke.queryvo.CollectCountVO;
import com.gjy.boke.service.BlogService;
import com.gjy.boke.service.CommentService;
import com.gjy.boke.service.TagService;
import com.gjy.boke.service.TypeService;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.HyperLogLogOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

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
    @Resource
    CommentService commentService;
    @Resource
    TypeService typeService;
    @Resource
    TagService tagService;
    @Resource
    TypeDao typeDao;


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

    //统计每个分类下的所有文章的浏览总数
    @RequestMapping("/getCount")
    @ResponseBody
    public List<BtVo> getCount(){
        return typeDao.getBtVoList();
    }

    //统计词云
    @RequestMapping("/wordCloud")
    @ResponseBody
    public String wordCloud(){
        return blogService.getBlogTitle();
    }

    //获取一周内游客和普通用户的系统访问量
    @RequestMapping("/systemVisits")
    @ResponseBody
    public ArrayList<ArrayList<Integer>> systemVisits(){
        HyperLogLogOperations log = redisTemplate.opsForHyperLogLog();
        HashOperations hash = redisTemplate.opsForHash();

        //游客访问量，根据ip+session（session不存在user）来判断是否为游客
        Long ts = log.size("tourist");
        //普通用户访问量

        int day7 = (Integer)hash.get("weekUser", "day7");
        int day6 = (Integer)hash.get("weekUser", "day6");
        int day5 = (Integer)hash.get("weekUser", "day5");
        int day4 = (Integer)hash.get("weekUser", "day4");
        int day3 = (Integer)hash.get("weekUser", "day3");
        int day2 = (Integer)hash.get("weekUser", "day2");
        int day1 = (Integer)hash.get("weekUser", "day1");


        //游客
        ArrayList<Integer> list1 = new ArrayList<>();
        list1.add(Integer.parseInt(ts+""));list1.add(2);list1.add(3);list1.add(4);list1.add(5);list1.add(16);list1.add(15);

        //普通用户
        ArrayList<Integer> list2 = new ArrayList<>();
        list2.add(day1);list2.add(day2);list2.add(day3);list2.add(day4);list2.add(day5);;list2.add(day6);;list2.add(day7);

        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();

        result.add(list1);
        result.add(list2);
        return result;
    }

    //排行榜1
    @RequestMapping("/leaderBoard")
    @ResponseBody
    public ArrayList<Integer> leaderBoard(){
        //统计收藏数量
        int collectCount = blogService.getCollectCount();
        //统计评论数量
        int commentCount = commentService.getCommentCount();
        //统计浏览量
        int viewCount = blogService.getViewCount();
        //统计文章数量
        int blogCount = blogService.getBlogNumber();
        //统计标签数量
        int tagCount = tagService.GetTagNumber();
        //统计分类数量
        int typeCount = typeService.getTypeCount();

        ArrayList<Integer> list = new ArrayList<>();
        list.add(collectCount);list.add(commentCount);list.add(viewCount);list.add(blogCount);list.add(tagCount);list.add(typeCount);
        return list;
    }

    //排行榜2
    @RequestMapping("/leaderBoard1")
    @ResponseBody
    public LinkedList<HashMap<String,Integer>> leaderBoard1(){
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        //统计被收藏最多的文章
        CollectCountVO collectCountVO = blogService.mostFavoriteBlog();
        //统计评论最多的文章
        Blog blog = blogService.mostCommentBlog();
        //统计评论和浏览最多的文章
        Blog hotBlog = blogService.getHotBlog();
        LinkedList<HashMap<String, Integer>> list = new LinkedList<>();

        HashMap<String, Integer> map1 = new HashMap<>();
        map1.put(collectCountVO.getTitle(),collectCountVO.getTotal());
        list.add(map1);

        HashMap<String, Integer> map2 = new HashMap<>();
        map2.put(blog.getTitle(),blog.getCommentcount());
        list.add(map2);

        HashMap<String, Integer> map3 = new HashMap<>();
        map3.put(hotBlog.getTitle(),hotBlog.getViews());
        list.add(map3);
        return list;

    }


}

