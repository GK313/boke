package com.gjy.boke.dao;

import com.gjy.boke.entity.Guser;

public interface GuserDao {
    int deleteByPrimaryKey(String phone);

    int insert(Guser record);

    int insertSelective(Guser record);

    Guser selectByPrimaryKey(String phone);

    int updateByPrimaryKeySelective(Guser record);

    int updateByPrimaryKey(Guser record);
}