package com.gjy.boke.entity;

import com.sun.istack.NotNull;
import org.aspectj.bridge.IMessage;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * t_blog
 * @author
 */
public class Blog implements Serializable {
    private Long id;

    private String title;

    //设置Blog的content为长文本的类型
    @Basic(fetch= FetchType.LAZY)
    @Lob
    private String content;

    private String firstpicture;

    private String flag;

    private Integer views=0;

    private Integer commentcount=0;

    private String appreciation="off";

    private String sharestatement="off";

    private String commentabled="off";

    private String published="off";

    private String recommend="off";

    @DateTimeFormat
    private Date createtime;
    @DateTimeFormat
    private Date updatetime;

    private Long typeid;

    private Long userid;

    //博客描述
    private String description;

    //定义一个存放Long类型数据的数组，存放该博客所对应的标签id
    private String tagids;

    private Long tagid;

    //一对一关系映射
    private Type type;
    //一对多关系映射
    private List<Tag> tags = new ArrayList<>();

    private List<Comment> comments = new ArrayList<>();

    public Blog() {
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstpicture() {
        return firstpicture;
    }

    public void setFirstpicture(String firstpicture) {
        this.firstpicture = firstpicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getCommentcount() {
        return commentcount;
    }

    public void setCommentcount(Integer commentcount) {
        this.commentcount = commentcount;
    }

    public String getAppreciation() {
        return appreciation;
    }

    public void setAppreciation(String appreciation) {
        this.appreciation = appreciation;
    }

    public String getSharestatement() {
        return sharestatement;
    }

    public void setSharestatement(String sharestatement) {
        this.sharestatement = sharestatement;
    }

    public String getCommentabled() {
        return commentabled;
    }

    public void setCommentabled(String commentabled) {
        this.commentabled = commentabled;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Long getTypeid() {
        return typeid;
    }

    public void setTypeid(Long typeid) {
        this.typeid = typeid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTagids() {
        return tagids;
    }

    public void setTagids(String tagids) {
        this.tagids = tagids;
    }

    public Long getTagid() {
        return tagid;
    }

    public void setTagid(Long tagid) {
        this.tagid = tagid;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}