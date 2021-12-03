package com.gjy.boke.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.HyperLogLogOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author GJY
 * @Date 2021/6/20 12:41
 * @Version 1.0
 * 统计系统活跃量
 */
@Component
public class ViewsUtil {

    @Resource
    RedisTemplate redisTemplate;

    public void setViews(Long userId){
        //获取当前星期值
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        String week = sdf.format(new Date());
        //hyperLoglog用来统计每一天的访问量
        HyperLogLogOperations ofhll = redisTemplate.opsForHyperLogLog();
        //hash用来存放普通用户一周7天的访问量
        HashOperations hash = redisTemplate.opsForHash();


        if(week.equals("星期日")){
            //将当前userId加入到周日的访问量统计中
            ofhll.add("uday7",userId);
            //将周日最新的统计结果加入到hash集合中
            hash.put("weekUser","day7",ofhll.size("uday7"));
        }else if(week.equals("星期六")){
            ofhll.add("uday6",userId);
            hash.put("weekUser","day6",ofhll.size("uday6"));
        }else if(week.equals("星期五")){
            ofhll.add("uday5",userId);
            hash.put("weekUser","day5",ofhll.size("uday5"));
        }else if(week.equals("星期四")){
            ofhll.add("uday4",userId);
            hash.put("weekUser","day4",ofhll.size("uday4"));
        }else if(week.equals("星期三")){
            ofhll.add("uday3",userId);
            hash.put("weekUser","day3",ofhll.size("uday3"));
        }else if(week.equals("星期二")){
            ofhll.add("uday2",userId);
            hash.put("weekUser","day2",ofhll.size("uday2"));
        }else if(week.equals("星期一")){
            ofhll.add("uday1",userId);
            hash.put("weekUser","day1",ofhll.size("uday1"));
        }

    }
}
