package com.gjy.boke.controller.admin;

import com.gjy.boke.entity.User;
import com.gjy.boke.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @Author GJY
 * @Date 2021/1/2 17:11
 * @Version 1.0
 */
@Controller
public class LoginController {

    @Resource
    private UserService userService;

    @GetMapping("/admin")
    public String loginPage() {
        return "admin/login";
    }


    /**
     * 用户登录校验
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session,
                        RedirectAttributes attributes){
        User user = userService.checkUserByUsernameAndPassword(username, password);
        System.out.println(user);
        if(user!=null){
            //不把密码传递到前端
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/index";
        }else{
            //因为是重定向，所以需要使用RedirectAttribute将message传递到前端，用Model无法实现
            attributes.addFlashAttribute("message","用户名和密码错误");
            return "redirect:/admin";
        }

    }

    @GetMapping("/loginOut")
    public String loginOut(HttpSession session){
        //清空session
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
