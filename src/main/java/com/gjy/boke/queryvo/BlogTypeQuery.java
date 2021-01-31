package com.gjy.boke.queryvo;

/**
 * @Author GJY
 * @Date 2021/1/31 10:58
 * @Version 1.0
 */
public class BlogTypeQuery {
    private Long id;
    private String name;
    private int total;

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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
