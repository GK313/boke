package com.gjy.boke.service.Impl;

import com.gjy.boke.dao.UserDao;
import com.gjy.boke.entity.User;
import com.gjy.boke.service.UserService;
import com.gjy.boke.utils.MD5Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author GJY
 * @Date 2021/1/2 21:21
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserDao userDao;


    @Override
    public User checkUserByUsernameAndPassword(String username,String password) {
        //将前端获取的密码用MD5加密后在到数据库中查找
        User user = userDao.selectUserByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }

    @Override
    public Long GetUserIdByName(String name) {
        return userDao.getUserIdByName(name);
    }
}
