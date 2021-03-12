package com.gjy.boke.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * t_tag
 * @author 
 */
public class Tag implements Serializable,Comparable<Tag> {
    private Long id;

    private String name;

    private List<Blog> blogs = new ArrayList<>();

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    /**
     * 重写排序规则
     * @param o
     * @return
     */
    @Override
    public int compareTo(Tag o) {
        return o.getBlogs().size()-this.getBlogs().size();
    }
}