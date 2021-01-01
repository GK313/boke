package com.gjy.boke.contorller;

import com.gjy.boke.exception.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class HelloController {
    @GetMapping("/")
    public String test(){
       /*int i=9/0;*/
        String blog=null;
        if(blog==null) {
            throw new NotFoundException("博客不存在");
        }
       return "index";
    }
}
