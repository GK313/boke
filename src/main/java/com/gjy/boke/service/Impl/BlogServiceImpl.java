package com.gjy.boke.service.Impl;

import com.gjy.boke.dao.BlogDao;
import com.gjy.boke.entity.Blog;
import com.gjy.boke.entity.Tag;
import com.gjy.boke.exception.NotFoundException;
import com.gjy.boke.queryvo.BlogTypeQuery;
import com.gjy.boke.service.BlogService;
import com.gjy.boke.utils.MarkdownUtil;
import com.gjy.boke.utils.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.error.Mark;

import javax.annotation.Resource;
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

    @Override
    public Blog getBlogById(Long id) {
        Blog blog = blogDao.GetBlogById(id);
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        b.setContent(MarkdownUtil.markdownToHtmlExtensions(blog.getContent()));
        return b;
    }

    @Override
    public List<Blog> getAllBlog() {
        return blogDao.GetAllBlog();
    }

    @Override
    public int saveBlog(Blog blog) {
        blog.setUpdatetime(new Date());
        blog.setCreatetime(new Date());
        blog.setViews(0);;
        blog.setCommentcount(0);
        int i = blogDao.SaveBlog(blog);
        //获取最新的博客的id
        Long newBlogid = blogDao.getNewestBlogId();
        //将博客id和当前的该博客所对应的标签id保存到blog_tag表中
        //(1)遍历当前博客的tagids
        String[] tagids = blog.getTagids().split(",");
        for (String tagid : tagids) {
            blogDao.insert_blog_tag(newBlogid,Long.parseLong(tagid));
        }
        return i;
    }

    @Override
    public int  editBlog(Long id, Blog newblog) {
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
            blogDao.insert_blog_tag(id,Long.parseLong(tagid));
        }
        return i;
    }

    @Override
    public int deleteBlog(Long id) {
        return blogDao.DeleteBlog(id);
    }

    @Override
    public List<Blog> getBlogByTitleAndTypeNameAndRecommend(String title, String name, String recommend) {
        return blogDao.GetBlogByTitleAndTypeNameAndRecommend(title,name,recommend);
    }

    @Override
    public List<Blog> getBlogByUpdateTimeLimit() {
        return blogDao.GetBlogByUpdateTimeLimit();
    }

    @Override
    public int getBlogNumber() {
        return blogDao.GetBlogNumber();
    }

    @Override
    public List<BlogTypeQuery> getBlogTypeQueryVO() {
        return blogDao.getBlogTypeQuery();
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
}
