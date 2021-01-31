package com.gjy.boke.entity;

import java.io.Serializable;

/**
 * t_type
 * @author 
 */
public class Type implements Serializable {
    private Long id;

    private String name;

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
}