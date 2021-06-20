package com.gjy.boke.service;

import com.gjy.boke.entity.Blog;
import com.gjy.boke.entity.Tag;
import com.gjy.boke.queryvo.BlogTypeQuery;

import javax.servlet.http.HttpSession;
import java.awt.image.Kernel;
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
    int saveBlog(Blog blog, String tagId, HttpSession session);

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

    /**
     * 根据分类id获取该分类下的所有博客
     * @param typeId
     * @return
     */
    List<Blog> getBlogByTypeId(Long typeId);

    /**
     * 获取每个标签所对应的博客
     */
    List<Tag> getBlogInTag();

    /**
     * 根据标签id获取该标签下的所有博客
     */
    List<Tag> getBlogInTagByTagId(Long id);

    int updateBlogCommentCountsAddOneByBlogId(Long BlogId);
    int updateBlogCommentCountsCutOneByBlogId(Long BlogId);
    //文章浏览次数自增
    void updateBlogViewsAddOneByBlogId(Long blogId);

    /**
     * 根据文章的更新时间对文章集合进行排序
     * @return
     */
    List<Blog> ListBlogOrderByUpdateTime();

    /**
     * 根据文章的评论数和文章的浏览数量对文章集合进行排序
     * @return
     */
    List<Blog> ListBlogOrderByBlogViewsAndCommentCount();

    /**
     * 全局搜索
     */
    List<Blog> listBlogSearchByQuery(String query);

    /**
     * 收藏博客
     * @param blogId 文章id
     * @param userId 用户id
     * @return
     */
    int insertCollection(Long blogId, Long userId);

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


    /**
     * 统计词云
     * @return
     */
    String getBlogTitle();
}
