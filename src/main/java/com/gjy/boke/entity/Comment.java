package com.gjy.boke.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author GJY
 * @Date 2021/1/2 14:02
 * @Version 1.0
 * 评论类
 */

public class Comment {
    /*@Id
    @GeneratedValue
    private Long id;

    private String nickname;

    //评论内容
    private String content;

    private String email;

    //头像
    private String avatar;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    //多的一端为维护方，即Comment为维护方，Blog为被维护方
    @ManyToOne
    private Blog blog;

    //对象自关联,代表当前包含多个回复的子类
    //当前作为一个父类考虑的话，即为一对多的关系，即一个父类有多个回复的子类
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replayComment = new ArrayList<>();

    @ManyToOne
    //一个Comment只能有一个直接父类
    private Comment parentComment;


    public List<Comment> getReplayComment() {
        return replayComment;
    }

    public void setReplayComment(List<Comment> replayComment) {
        this.replayComment = replayComment;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public Comment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", content='" + content + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                '}';
    }*/
    //标识一条留言
    private Long id;
    //留言者的昵称
    private String nickname;
    //留言邮箱
    private String email;
    //留言内容
    private String content;
    //头像
    private String avatar;
    //创建时间，即留言时间
    private Date createTime;

    private Long blogId;
    //父留言的id
    private Long parentCommentId;
    //父留言的昵称
    private String parentNickname;
    //回复评论，一个留言实体类下可以有多个子留言
    private List<Comment> replyComments = new ArrayList<>();
    //一个留言实体类只属于一个父留言
    private Comment parentComment;

    private boolean adminComment;

/*======================================================================================*/

    public Comment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public String getParentNickname() {
        return parentNickname;
    }

    public void setParentNickname(String parentNickname) {
        this.parentNickname = parentNickname;
    }

    public List<Comment> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(List<Comment> replyComments) {
        this.replyComments = replyComments;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public boolean isAdminComment() {
        return adminComment;
    }

    public void setAdminComment(boolean adminComment) {
        this.adminComment = adminComment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                ", blogId=" + blogId +
                ", parentCommentId=" + parentCommentId +
                ", parentNickname='" + parentNickname + '\'' +
                ", replyComments=" + replyComments +
                ", parentComment=" + parentComment +
                ", adminComment=" + adminComment +
                '}';
    }
}
