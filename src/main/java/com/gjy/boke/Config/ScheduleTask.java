package com.gjy.boke.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.HyperLogLogOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author GJY
 * @Date 2021/6/20 11:28
 * @Version 1.0
 * SpringBoot定时任务
 */
@Configuration
@EnableScheduling
public class ScheduleTask {
    @Resource
    RedisTemplate redisTemplate;

    //3.添加定时任务
    @Scheduled(cron = "0 0 0 ? * SUN")
    //周日0点执行的定时任务
    private void configureTasks() {
        //周日晚上0点就应该清除掉前一个星期的数据，重新初始化hash并统计新的一周
        //重新统计日游客和普通用户访问量
        System.out.println(new Date());
        //hyperLoglog用来统计每一天的访问量
        HyperLogLogOperations ofhll = redisTemplate.opsForHyperLogLog();
        //hash用来存放一周7天的访问量
        HashOperations hash = redisTemplate.opsForHash();

        //清空
        ofhll.delete("uday7");
        ofhll.delete("uday6");
        ofhll.delete("uday5");
        ofhll.delete("uday4");
        ofhll.delete("uday2");
        ofhll.delete("uday1");
        //初始化
        hash.put("weekUser","day7",0);
        hash.put("weekUser","day3",0);
        hash.put("weekUser","day4",0);
        hash.put("weekUser","day5",0);
        hash.put("weekUser","day6",0);
        hash.put("weekUser","day1",0);
        hash.put("weekUser","day2",0);

    }
}
