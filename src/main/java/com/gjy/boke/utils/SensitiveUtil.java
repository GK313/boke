package com.gjy.boke.utils;

import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * @Author GJY
 * @Date 2021/6/21 15:34
 * @Version 1.0
 */
@Component
public class SensitiveUtil {

    public static String sensitiveReplace(String str){
        String [] s={"傻","死","暴力","色情","傻逼","政治","共产党","暴力","分裂","民主","自由","独立"};
        for (String sss:s) //foreach遍历更换
        {
            str = str.replace(sss, "**");//将s数组中的关键字用*替换
        }
        return str ;
    }


}
