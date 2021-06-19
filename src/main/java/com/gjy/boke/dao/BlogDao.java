package com.gjy.boke.dao;

import com.gjy.boke.entity.Blog;
import com.gjy.boke.entity.Tag;
import com.gjy.boke.queryvo.BlogQuery;
import com.gjy.boke.queryvo.BlogTypeQuery;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
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
     *获取封装有博客id和其对应的标签的
     */
    List<Blog> GetBlogIdAndTagids();

    /**
     * 计算已发布博客数量
     * @return
     */
    int GetBlogNumber();

    /**
     * 获取包含BlogTypeQuery集合
     */
    List<BlogTypeQuery> getBlogTypeQuery();

    /**
     * 根据分类id获取该分类下的所有博客
     * @return
     */
    List<Blog> GetBlogByTypeId(Long typeId);

    /**
     * 根获取最新的博客的id
     */
    Long getNewestBlogId();

    void insert_blog_tag(Long blogid,Long tagid);

    /**
     * 获取每个标签所对应的博客，以及当前标签的名称
     * 若当前标签下有博客，则包含id，否则标签id为null
     */
    List<Tag> getTagInBlogList();

    /**
     * 根据标签id获取该标签下的所有博客
     */
    @Transactional
    List<Tag> GetBlogInTagByTagId(Long tagId);

    /**
     * 根据博客id，将博客的评论数-1
     */
    @Transactional
    int updateBlogCommentCountsCutOneByBlogId(Long BlogId);

    /**
     * 根据博客id，将博客的评论数+1
     */
    @Transactional
    int updateBlogCommentCountsAddOneByBlogId(Long BlogId);

    /**
     * 文章访问数量自增
     */
    void updateBlogViewsAddOneByBlogId(Long BlogId);

    /**
     * 根据文章的更新时间对文章集合进行排序
     * @return
     */
    List<Blog> listBlogOrderByUpdateTime();

    /**
     * 根据文章的评论数和文章的浏览数量对文章集合进行排序
     * @return
     */
    List<Blog> listBlogOrderByBlogViewsAndCommentCount();

    /**
     * 全局搜索
     */
    List<Blog> listBlogBySearchQuery(String query);

    /**
     * 收藏博客
     * @param blogId
     * @param userId
     * @return
     */
    int insertCollection(Long blogId, Long userId, Date date);

    /**
     * 根据用户id获取收藏的文章
     * @param userId
     * @return
     */
    List<Blog> getCollectionsById(Long userId);

    /**
     * 根据用户id和文章id查询收藏记录
     * @param blogId
     * @param userId
     * @return
     */
    int getCollectionByBlogIdAndUserId(Long blogId, Long userId);

    /**
     * 取消收藏
     * @param blogId
     * @return
     */
    int deleteCollect(Long blogId, Long userId);


}