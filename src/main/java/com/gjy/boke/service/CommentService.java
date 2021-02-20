package com.gjy.boke.service;

import com.gjy.boke.entity.Comment;
import org.springframework.stereotype.Service;

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
    int saveComment(Comment comment);


    /**
     * 查询所有parentCommentId不为-1的comment
     * @param parentCommentId
     * @return
     */
    List<Comment> listCommentByBlogIdParentIdNull(Long blogId,Long parentCommentId);


    List<Comment> listCommentByBlogId(Long blogId);
}
