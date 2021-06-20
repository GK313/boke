package com.gjy.boke.queryvo;

/**
 * @Author GJY
 * @Date 2021/6/19 19:29
 * @Version 1.0
 * 统计每个分类下的所有文章的浏览总数Vo
 */
public class BtVo {
    private String name;
    private Integer views;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }
}
