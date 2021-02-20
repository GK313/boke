package com.gjy.boke.controller;

import com.gjy.boke.dao.CommentDao;
import com.gjy.boke.entity.Comment;
import com.gjy.boke.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author GJY
 * @Date 2021/2/19 10:01
 * @Version 1.0
 */
@Controller
@RequestMapping("/comment")
public class ComentController {
    @Resource
    private CommentService commentService;

    //发布评论后，更新评论列表（查询博文下的评论列表）
    @GetMapping("/commentList/{blogId}")
    public String Comments(@PathVariable Long blogId, Model model){
        //根据blogId获取该博客下的CommentList
        List<Comment> comments = commentService.listCommentByBlogIdParentIdNull(blogId, null);

        model.addAttribute("comments",comments);
        System.out.println("我输出了");
        return "blog :: commentList";
    }

    //新增评论
    @PostMapping("/save")
    public String postComment(Comment comment){
        int i = commentService.saveComment(comment);
        return "redirect:/comment/commentList/"+comment.getBlogid();
    }
}
