package com.gjy.boke.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author GJY
 * @Date 2021/4/27 23:22
 * @Version 1.0
 */
@Controller
public class DateController {
    @RequestMapping("/getWeek")
    public String getWeek(@RequestParam Date today, @RequestParam int year, Model model) {
        Calendar calendar = Calendar.getInstance();
        today.setYear(today.getYear()+year);
        calendar.setTime(today);
        int i = calendar.get(Calendar.DAY_OF_WEEK);
        String week="星期一";
        switch (i) {
            case 1:
                 week="星期日";
                break;
            case 2:
                week="星期一";
                break;
            case 3:
                week="星期二";
                break;
            case 4:
                week="星期三";
                break;
            case 5:
                week="星期四";
                break;
            case 6:
                week="星期五";
                break;
            default:
                week="星期六";
                break;
        }
        model.addAttribute("result", week);
        return "date";
    }
}
