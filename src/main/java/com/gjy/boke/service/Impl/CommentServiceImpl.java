package com.gjy.boke.service.Impl;

import com.gjy.boke.dao.CommentDao;
import com.gjy.boke.entity.Comment;
import com.gjy.boke.service.CommentService;
import org.apache.tomcat.util.compat.JrePlatform;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author GJY
 * @Date 2021/2/19 15:49
 * @Version 1.0
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentDao commentDao;

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();

    @Override
    public int saveComment(Comment comment) throws ParseException {
        /*Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");*/
        comment.setCreatetime( new Date());

        comment.setAvatar("https://picsum.photos/id/274/3824/2520");
        Long parentcommentid = comment.getParentcommentid();

        if(parentcommentid!=Long.parseLong("-1")){
            //如果当前的评论的getParentcommentid不为-1，即不为默认值，则该评论有父评论
            //根据parentcommentid找出该当前评论的父评论
            comment.setParentComment(commentDao.getCommentById(parentcommentid));
        }else{
            comment.setParentComment(null);
        }
        return commentDao.saveComment(comment);
    }

    @Override
    public List<Comment> listCommentByBlogIdParentIdNull(Long blogId,Long parentCommentId) {
        //查询出parentId为-1的Comment,即最顶层评论
        List<Comment> comments = commentDao.listCommentByBlogIdInParentCommentIdNull(blogId,Long.parseLong("-1"));
        //查询出parentId不为-1的Comment，即为子评论
        for (Comment comment : comments) {
            Long id = comment.getId();
            String parentCommentNickname = comment.getNickname();
            //根据当前的BlogId和父评论id查找出当前父评论下的所有子评论，然后保存到childComments集合中
            List<Comment> childComments = commentDao.listCommentByBlogIdInParentCommentIdNotNull(blogId,
                    id);
            //查询出子评论
            combineChildren(blogId,childComments,parentCommentNickname);

            comment.setReplyComments(tempReplys);
            tempReplys = new ArrayList<>();
        }

        return comments;
    }

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        return commentDao.listCommentByBlogId(blogId);
    }

    @Override
    public void deleteCommentByBlogIdAndCommentId(Long BlogId, Long CommentId) {
        commentDao.deleteCommentByBlogIdAndCommentId(BlogId,CommentId);
    }

    /**
     *
     * @param blogId 博文id
     * @param childComments 保存当前博客所对应的所有子标签的集合
     * @param parentCommentNickname 父评论的名称
     */
    public void combineChildren(Long blogId,List<Comment> childComments,String parentCommentNickname){
        //判断该父标签的子评论集合childComments是否为空，判断是否有一级子评论
        if(childComments.size()>0){
            //当前评论包含子评论,循环找出子评论的id
            for (Comment childComment : childComments) {
                //当前childComment找到父评论，将父评论的nicknameSet到childComment中
                childComment.setParentnickname(parentCommentNickname);
                //将当前的childComment添加到tempReplys集合中
                tempReplys.add(childComment);

                //获取当前childComment的nickname,找出当前childComment的子评论，则是作为parentNickname
                String childCommentNickname = childComment.getNickname();
                //获取当前childComment的id
                Long childCommentId = childComment.getId();
                //根据blogId、一级子评论的Id：childCommentId、一级子评论的nickname：childCommentNickname，找出二级子评论
                recursively(blogId,childCommentId,childCommentNickname);

            }
        }
    }


    /**
     * 迭代找出下一级子评论
     * @param blogId
     * @param parentCommentId 即一级子评论的id
     * @param ParentCommentNickname 即一级子评论的nickname
     */
    private void recursively(Long blogId,Long parentCommentId,String ParentCommentNickname){
        //根据博客id和子一级评论的id找到子二级评论
        List<Comment> replyComments = commentDao.listCommentByBlogIdInParentCommentIdNotNull(blogId, parentCommentId);
        //判断二级子评论集合是否为空
        if(replyComments.size()>0){
            for (Comment replyComment : replyComments) {
                replyComment.setParentnickname(ParentCommentNickname);
                replyComments.add(replyComment);
                String nickname = replyComment.getNickname();
                Long id = replyComment.getId();
                recursively(blogId,id,nickname);
            }
        }
    }

    /**
     * 统计评论数量
     * @return
     */
    @Override
    public int getCommentCount() {
        return commentDao.getCommentCount();
    }
}
