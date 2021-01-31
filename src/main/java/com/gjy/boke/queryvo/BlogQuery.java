package com.gjy.boke.queryvo;

import com.gjy.boke.entity.Comment;
import com.gjy.boke.entity.Tag;
import com.gjy.boke.entity.Type;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author GJY
 * @Date 2021/1/28 9:21
 * @Version 1.0
 */
public class BlogQuery {
    private Long id;
    private String tagids;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagids() {
        return tagids;
    }

    public void setTagids(String tagids) {
        this.tagids = tagids;
    }
}

