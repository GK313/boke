package com.gjy.boke.interceptor;

import com.gjy.boke.entity.User;
import org.springframework.data.redis.core.HyperLogLogOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author GJY
 * @Date 2021/1/3 20:12
 * @Version 1.0
 * 登录拦截器
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Resource
    RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        User user = (User)request.getSession().getAttribute("user");
        if(user!=null){
            if(user.getNickname().equals("admin")){
                return true;
            }else if(!user.getNickname().equals("admin")){
                response.sendRedirect("/admin");
                return false;
            }
        }
        response.sendRedirect("/admin");
        return false;


    }
}
