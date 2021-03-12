package com.gjy.boke.dao;

import com.gjy.boke.entity.Comment;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public interface CommentDao {
    /*int deleteByPrimaryKey(Long id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);*/

    /**
     * 查询所有parentCommentId为-1的comment，即父评论
     * @param blogid
     * @return
     */
    List<Comment> listCommentByBlogIdInParentCommentIdNull(Long blogid,Long parentCommentId);

    /**
     * 查询所有parentCommentId不为-1的comment，即子评论
     * @param blogid
     * @return
     */
    List<Comment> listCommentByBlogIdInParentCommentIdNotNull(Long blogid,Long parentCommentId);

    /**
     * 保存评论
     * @param comment
     * @return
     */
    int saveComment(Comment comment);

    /**
     * 根据commentId查询Comment
     * @param byid
     * @return
     */
    Comment getCommentById(Long byid);

    /**
     * 根据博客id和父博客id查询出子博客
     * @param BlogId
     * @param CommentId
     * @return
     */
    List<Comment> listChildCommentByBlogIdAndCommentId(Long BlogId,Long CommentId);

    /**
     * 根据博客id查询Comment
     * @param blogId
     * @return
     */
    List<Comment> listCommentByBlogId(Long blogId);

    /**
     * 根据博客id和评论id删除评论
     * @return
     */
    int deleteCommentByBlogIdAndCommentId(Long BlogId,Long CommentId);
}