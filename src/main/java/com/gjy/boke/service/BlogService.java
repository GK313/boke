package com.gjy.boke.service;

import com.gjy.boke.entity.Blog;
import com.gjy.boke.queryvo.BlogTypeQuery;

import java.util.List;

/**
 * @Author GJY
 * @Date 2021/1/23 13:39
 * @Version 1.0
 */
public interface BlogService {
    /**
     * 根据主键id查询博客信息
     * @param id
     * @return
     */
    Blog getBlogById(Long id);

    /**
     * 查询所有博客，封装到list中
     * @return
     */
    List<Blog> getAllBlog();

    /**
     * 新增博客
     * @param blog
     * @return
     */
    int saveBlog(Blog blog);

    /**
     * 根据主键id，修改对应的博客信息
     * @param id
     * @param blog
     * @return
     */
    int editBlog(Long id ,Blog blog);

    /**
     * 根据主键id删除对应的博客
     * @param id
     * @return
     */
    int deleteBlog(Long id);

    /**
     * 根据博文标题、分类名称、是否推荐查询博客信息
     * @param title
     * @param name
     * @param recommend
     * @return
     */
    List<Blog> getBlogByTitleAndTypeNameAndRecommend(String title,String name,String recommend);

    /**
     * 取最新更新的前n条博客信息
     * @return
     */
    List<Blog> getBlogByUpdateTimeLimit();

    /**
     * 计算已发布博客数量
     * @return
     */
    int getBlogNumber();

    /**
     * 获取包含分类名称、id、该分类下的对应博客的数量的BlogTypeQueryVo
     * @return
     */
    List<BlogTypeQuery> getBlogTypeQueryVO();
}
