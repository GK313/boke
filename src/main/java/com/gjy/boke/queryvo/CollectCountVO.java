package com.gjy.boke.queryvo;

/**
 * @Author GJY
 * @Date 2021/6/22 14:27
 * @Version 1.0
 * 统计被收藏数量最多的文章
 */
public class CollectCountVO {
    private String title;
    private Integer total;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
