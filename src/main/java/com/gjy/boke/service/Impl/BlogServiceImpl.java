package com.gjy.boke.service.Impl;

import com.gjy.boke.dao.BlogDao;
import com.gjy.boke.entity.Blog;
import com.gjy.boke.entity.Tag;
import com.gjy.boke.entity.User;
import com.gjy.boke.exception.NotFoundException;
import com.gjy.boke.queryvo.BlogTypeQuery;
import com.gjy.boke.service.BlogService;
import com.gjy.boke.service.TagService;
import com.gjy.boke.service.TypeService;
import com.gjy.boke.utils.MarkdownUtil;
import com.gjy.boke.utils.MyBeanUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.error.Mark;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author GJY
 * @Date 2021/1/23 13:40
 * @Version 1.0
 */
@Service
public class BlogServiceImpl implements BlogService {
    @Resource
    BlogDao blogDao;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private TypeService typeService;
    @Resource
    private TagService tagService;

    @Override
    public Blog getBlogById(Long id) {
        Blog blog = blogDao.GetBlogById(id);
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        b.setContent(MarkdownUtil.markdownToHtmlExtensions(blog.getContent()));
        return b;
    }

    @Override
    public List<Blog> getAllBlog() {
        List<Blog> blogs = blogDao.GetAllBlog();
       /* //将其保存到redis中
        ValueOperations ops = redisTemplate.opsForValue();
        ops.set("blogs",blogs);*/
        return blogs;
    }

    @Override
    public int saveBlog(Blog blog, String tagId, HttpSession session) {
        //根据tagid获取对应的Type实体,并将获取的实体填充到Blog的type属性中
        blog.setType(typeService.getTypeById(blog.getTypeid()));
        //从session中获取userid,并填充到Blog的userid属性中
        User user = (User) session.getAttribute("user");
        blog.setUserid(user.getId());
        ArrayList<Tag> tags = new ArrayList<>();
        //获取该Blog实体所关联的所有Tag标签实体
        for (String tagid : tagId.split(",")) {
            tags.add(tagService.getTagById(Long.parseLong(tagid)));
        }
        blog.setTagids(tagId);
        blog.setTags(tags);

        blog.setUpdatetime(new Date());
        blog.setCreatetime(new Date());
        blog.setViews(0);
        ;
        blog.setCommentcount(0);
        //新增博客前将redis的博客数据清空
        ValueOperations ops = redisTemplate.opsForValue();
        ops.set("blogs", null);
        int i = blogDao.SaveBlog(blog);
        //获取最新的博客的id
        Long newBlogid = blogDao.getNewestBlogId();
        //将博客id和当前的该博客所对应的标签id保存到blog_tag表中
        //(1)遍历当前博客的tagids
        String[] tagids = blog.getTagids().split(",");
        for (String tagid : tagids) {
            blogDao.insert_blog_tag(newBlogid, Long.parseLong(tagid));
        }
        //查询最新的博客数据，保存到redis中
        ops.set("blogs", blogDao.GetAllBlog());
        //将新增的文章投递到交换机,新增的文章还没有id，所以需要获取插入数据库后的文章信息
        Blog blog1 = blogDao.listBlogOrderByUpdateTime().get(0);
        System.out.println(blog1.toString());
        rabbitTemplate.convertAndSend("inform", blog1);
        return i;
    }

    @Override
    public int editBlog(Long id, Blog newblog) {
        //获取未更新的blog信息
        Blog oldblog = blogDao.GetBlogById(id);
        System.out.println(oldblog.getCommentcount());
        newblog.setViews(oldblog.getViews());
        newblog.setCommentcount(oldblog.getCommentcount());
        newblog.setCreatetime(oldblog.getCreatetime());
        newblog.setUpdatetime(new Date());
        /*if(oldblog==null) {
            throw new NotFoundException("该博客不存在");
        }
        //过滤掉新的blog中那些属性值为空的属性，将新blog的属性值copy到原来的blog中对应的属性
        BeanUtils.copyProperties(newblog,oldblog,MyBeanUtils.getNullPropertiesName(newblog));
        oldblog.setUpdatetime(new Date());*/
        int i = blogDao.EditBlog(id, newblog);
        //将博客id和当前的该博客所对应的标签id保存到blog_tag表中
        //(1)遍历当前博客的tagids
        String[] tagids = newblog.getTagids().split(",");
        for (String tagid : tagids) {
            blogDao.insert_blog_tag(id, Long.parseLong(tagid));
        }
        return i;
    }

    @Override
    public int deleteBlog(Long id) {
        return blogDao.DeleteBlog(id);
    }

    @Override
    public List<Blog> getBlogByTitleAndTypeNameAndRecommend(String title, String name, String recommend) {
        return blogDao.GetBlogByTitleAndTypeNameAndRecommend(title, name, recommend);
    }

    @Override
    public List<Blog> getBlogByUpdateTimeLimit() {
        List<Blog> blogLimits = blogDao.GetBlogByUpdateTimeLimit();
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        ops.set("blogLimits", blogLimits);
        return blogLimits;
    }

    @Override
    public int getBlogNumber() {
        return blogDao.GetBlogNumber();
    }

    @Override
    public List<BlogTypeQuery> getBlogTypeQueryVO() {
        List<BlogTypeQuery> types = blogDao.getBlogTypeQuery();
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        ops.set("types", types);
        return types;
    }

    @Override
    public List<Blog> getBlogByTypeId(Long typeId) {
        return blogDao.GetBlogByTypeId(typeId);
    }

    @Override
    public List<Tag> getBlogInTag() {
        return blogDao.getTagInBlogList();
    }

    @Override
    public List<Tag> getBlogInTagByTagId(Long id) {
        return blogDao.GetBlogInTagByTagId(id);
    }

    @Override
    public int updateBlogCommentCountsAddOneByBlogId(Long BlogId) {
        return blogDao.updateBlogCommentCountsAddOneByBlogId(BlogId);
    }

    @Override
    public int updateBlogCommentCountsCutOneByBlogId(Long BlogId) {
        return blogDao.updateBlogCommentCountsCutOneByBlogId(BlogId);
    }

    @Override
    public void updateBlogViewsAddOneByBlogId(Long blogId) {
        blogDao.updateBlogViewsAddOneByBlogId(blogId);
    }

    @Override
    public List<Blog> ListBlogOrderByUpdateTime() {
        return blogDao.listBlogOrderByUpdateTime();
    }

    @Override
    public List<Blog> ListBlogOrderByBlogViewsAndCommentCount() {
        return blogDao.listBlogOrderByBlogViewsAndCommentCount();
    }

    @Override
    public List<Blog> listBlogSearchByQuery(String query) {
        String query2 = "%" + query + "%";
        return blogDao.listBlogBySearchQuery(query2);
    }

    /**
     * 收藏博客
     *
     * @param blogId 文章id
     * @param userId 用户id
     * @return
     */
    @Override
    public int insertCollection(Long blogId, Long userId) {
        return blogDao.insertCollection(blogId, userId, new Date());
    }


    /**
     * 根据用户id获取收藏的文章
     *
     * @param userId
     * @return
     */
    @Override
    public List<Blog> getCollectionsById(Long userId) {
        return blogDao.getCollectionsById(userId);
    }

    /**
     * 根据用户id和文章id查询收藏记录
     *
     * @param blogId
     * @param userId
     * @return
     */
    @Override
    public int getCollectionByBlogIdAndUserId(Long blogId, Long userId) {
        return blogDao.getCollectionByBlogIdAndUserId(blogId, userId);
    }

    /**
     * 取消收藏
     *
     * @param blogId
     * @return
     */
    @Override
    public int deleteCollect(Long blogId, Long userId) {
        return blogDao.deleteCollect(blogId, userId);
    }

    /**
     * 词云统计
     *
     * @return
     */
    @Override
    public String getBlogTitle() {
        List<Blog> list = blogDao.getTitle();
        String str = null;
        for (Blog blog : list) {
            str += blog.getTitle() + ",";
        }
        return str;
    }
}
