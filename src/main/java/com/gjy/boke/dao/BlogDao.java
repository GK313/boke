package com.gjy.boke.dao;

import com.gjy.boke.entity.Blog;
import com.gjy.boke.queryvo.BlogQuery;
import com.gjy.boke.queryvo.BlogTypeQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogDao  {
    /*int deleteByPrimaryKey(Long id);

    int insert(Blog record);

    int insertSelective(Blog record);

    Blog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Blog record);

    int updateByPrimaryKey(Blog record);*/

    /**
     *根据id查询博客信息
     * @param id
     * @return
     */
    Blog GetBlogById(Long id);

    /**
     * 查询所有博客信息
     * @return
     */
    List<Blog> GetAllBlog();

    /**
     * 新增博客
     * @param blog
     * @return
     */
    int SaveBlog(Blog blog);

    /**
     * 根据主键id，修改对应的博客信息
     * @param id
     * @param blog
     * @return
     */
    int EditBlog(Long id ,Blog blog);

    /**
     * 根据主键id删除对应的博客
     * @param id
     * @return
     */
    int DeleteBlog(Long id);

    /**
     * 根据标题、分类、是否推荐查询博客信息
     * @param title
     * @param name
     * @param recommend
     * @return
     */
    List<Blog> GetBlogByTitleAndTypeNameAndRecommend(String title,String name,String recommend);

    /**
     *获取最新更新的前n条博客信息
     * @return
     */
    List<Blog> GetBlogByUpdateTimeLimit();

    /**
     *获取封装有博客id和其对应的标签的blogQuery
     */
    List<BlogQuery> GetBlogquery();

    /**
     * 计算已发布博客数量
     * @return
     */
    int GetBlogNumber();

    /**
     * 获取包含BlogTypeQuery集合
     */
    List<BlogTypeQuery> getBlogTypeQuery();
}