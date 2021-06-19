package com.gjy.boke.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gjy.boke.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {
    int deleteByPrimaryKey(Long id);

    Integer insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectUserByUsernameAndPassword(String username,String password);

    Long getUserIdByName(String name);

    /**
     * 新增用户

     * @return
     */
    Integer insertUser(User user);
}