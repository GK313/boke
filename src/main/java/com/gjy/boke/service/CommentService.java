package com.gjy.boke.service;

import com.gjy.boke.entity.Comment;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

/**
 * @Author GJY
 * @Date 2021/2/19 15:49
 * @Version 1.0
 */

public interface CommentService {
    /**
     * 新增评论
     * @param comment
     * @return
     */
    int saveComment(Comment comment) throws ParseException;


    /**
     * 查询所有parentCommentId不为-1的comment
     * @param parentCommentId
     * @return
     */
    List<Comment> listCommentByBlogIdParentIdNull(Long blogId,Long parentCommentId);

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
    void deleteCommentByBlogIdAndCommentId(Long BlogId,Long CommentId);

    /**
     * 统计评论数量
     * @return
     */
    int getCommentCount();
}
