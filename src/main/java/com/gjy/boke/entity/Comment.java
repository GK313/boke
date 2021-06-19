package com.gjy.boke.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * t_comment
 * @author 
 */
@Table(name="t_comment")
public class Comment implements Serializable {
    private Long id;

    private String nickname;

    private String email;

    private String content;

    private String avatar;

    @JsonFormat(pattern = "yyyy-MM-dd-HH-mm-ss",timezone="Asia/Shanghai")
    private Date createtime;

    private Long blogid;

    private Long parentcommentid;

    private String parentnickname;

    private Boolean admincomment;

    //回复评论，一个留言实体类下可以有多个子留言
    private List<Comment> replyComments = new ArrayList<>();

    //一个留言实体类只属于一个父留言，判断当前留言的parentcommentid是否为空，来判断当前留言有没有上级留言
    private Comment parentComment;


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

    private static final long serialVersionUID = 1L;

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

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Long getBlogid() {
        return blogid;
    }

    public void setBlogid(Long blogid) {
        this.blogid = blogid;
    }

    public Long getParentcommentid() {
        return parentcommentid;
    }

    public void setParentcommentid(Long parentcommentid) {
        this.parentcommentid = parentcommentid;
    }

    public String getParentnickname() {
        return parentnickname;
    }

    public void setParentnickname(String parentnickname) {
        this.parentnickname = parentnickname;
    }

    public Boolean getAdmincomment() {
        return admincomment;
    }

    public void setAdmincomment(Boolean admincomment) {
        this.admincomment = admincomment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createtime=" + createtime +
                ", blogid=" + blogid +
                ", parentcommentid=" + parentcommentid +
                ", parentnickname='" + parentnickname + '\'' +
                ", admincomment=" + admincomment +
                ", replyComments=" + replyComments +
                ", parentComment=" + parentComment +
                '}';
    }
}