package com.gjy.boke.controller;

import com.gjy.boke.dao.CommentDao;
import com.gjy.boke.entity.Blog;
import com.gjy.boke.entity.Comment;
import com.gjy.boke.entity.User;
import com.gjy.boke.service.BlogService;
import com.gjy.boke.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionActivationListener;
import java.text.ParseException;
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
    @Resource
    private BlogService blogService;

    //发布评论后，更新评论列表（查询博文下的评论列表）
    @GetMapping("/commentList/{blogId}")
    public String Comments(@PathVariable Long blogId, Model model){
        System.out.println(blogId+"更新评论列表");
        //根据blogId获取该博客下的CommentList
        List<Comment> comments = commentService.listCommentByBlogIdParentIdNull(blogId, null);
        //新增评论后，应当将当前博客的评论数+1
        blogService.updateBlogCommentCountsAddOneByBlogId(blogId);

        model.addAttribute("comments",comments);
        return "blog :: commentList";
    }

    //新增评论
    @PostMapping("/save")
    public String pstComment(Comment comment, HttpSession session) throws ParseException {
        //应该增加一个过滤器，来过滤掉一些不合适的评论
        User user=(User) session.getAttribute("user");
        if(user!=null){
            comment.setAvatar(user.getAvatar());
            //设置comment为博主评论标记
            comment.setAdmincomment(true);
            comment.setNickname(user.getNickname());
        }
        int i = commentService.saveComment(comment);


        //新增成功后重定向到commentList下，并查询出评论列表并局部刷新commentList
        return "redirect:/comment/commentList/"+comment.getBlogid();
    }


    /**
     * 删除评论
     * @param blogId
     * @param replyId
     * @param model
     * @return
     */
    @GetMapping("/operate/{blogId}/{replyId}/delete")
    public String deleteComment(@PathVariable Long blogId,@PathVariable Long replyId,Model model){
        //根据博客id和对应的CommentId删除评论信息(如果删除的是最顶层的评论，那子评论怎么办->父评论被删除，子评论也删除？or下一级子评论成为新的父评论)
        commentService.deleteCommentByBlogIdAndCommentId(blogId,replyId);
        List<Comment> comments = commentService.listCommentByBlogIdParentIdNull(blogId, null);
        //删除评论后将当前blog的评论数-1
        blogService.updateBlogCommentCountsCutOneByBlogId(blogId);
        Blog blog = blogService.getBlogById(blogId);
        model.addAttribute("comments",comments);
        model.addAttribute("blog",blog);
        return "blog";

    }
}
