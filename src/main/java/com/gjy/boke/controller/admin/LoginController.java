package com.gjy.boke.controller.admin;

import com.gjy.boke.entity.User;
import com.gjy.boke.service.BlogService;
import com.gjy.boke.service.UserService;
import com.gjy.boke.utils.MD5Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.UUID;

/**
 * @Author GJY
 * @Date 2021/1/2 17:11
 * @Version 1.0
 */
@Controller
public class LoginController {

    @Resource
    private UserService userService;

    @Resource
    private BlogService blogService;

    @GetMapping("/admin")
    public String loginPage() {
        return "admin/login";
    }

    @RequestMapping("alllogin")
    public String allLogin() {
        return "logins";
    }


    @RequestMapping("/statistics")
    public String goStatistics(){
        return "/admin/statistics";
    }



    /**
     * 用户登录校验
     *
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session,
                        RedirectAttributes attributes) {
        User user = userService.checkUserByUsernameAndPassword(username, password);
        session.setAttribute("collectionSize",blogService.getCollectionsById(user.getId()).size());
        //判断是否已经注册、是普通用户还是admin
        //普通用户跳转到系统首页
        //管理员跳转到管理后台首页
        //注册用用户名，昵称随机生成
        if (user != null && user.getNickname().equals("admin")) {
            //管理员跳转到管理后台首页
            //不把密码传递到前端
            user.setPassword(null);
            session.setAttribute("user", user);
            return "admin/index";
        } else if (user==null && userService.checkUserByUsernameAndPassword(username,null)==null) {
            //未注册用户，进行注册
            User user2 = new User();
            user2.setPassword(MD5Utils.code(password));
            user2.setAvatar("https://picsum.photos/id/1012/3973/2639");
            user2.setCreatetime(new Date());
            user2.setUsername(username);
            user2.setNickname("up"+ UUID.randomUUID().toString().substring(1,8));
            userService.insertUser(user2);
            session.setAttribute("user", user2);
            return "redirect:/";
        } else if (user != null && user.getNickname() != "admin") {
            //普通用户跳转到系统首页
            session.setAttribute("user", user);
            return "redirect:/";
        }else{
            //因为是重定向，所以需要使用RedirectAttribute将message传递到前端，用Model无法实现
            attributes.addFlashAttribute("message", "用户名和密码错误");
            return "redirect:/admin";
        }

    }

    @GetMapping("/logout")
    public String loginOut(HttpSession session) {
        //清空session
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
