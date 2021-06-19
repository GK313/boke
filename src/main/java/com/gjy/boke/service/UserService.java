package com.gjy.boke.service;

import com.gjy.boke.entity.User;

/**
 * @Author GJY
 * @Date 2021/1/2 21:20
 * @Version 1.0
 */
public interface UserService {
    /*
    String getUserById(Integer id);

    int checkUserByUsernameAndPassword(String username,String password);
*/
    User checkUserByUsernameAndPassword(String username,String password);

    Long GetUserIdByName(String name);

    /**
     * 新增用户
     * @return
     */
    Integer insertUser(User user);
}
