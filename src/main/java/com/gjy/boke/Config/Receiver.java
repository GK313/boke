package com.gjy.boke.Config;

import com.gjy.boke.entity.Blog;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @Author GJY
 * @Date 2021/6/17 13:44
 * @Version 1.0
 * 接收消息
 */
@Component
public class Receiver {
    @Resource
    RedisTemplate redisTemplate;
   /* //消费者端监听queue11队列
    @RabbitListener(queues = "inform")
    public void process(Blog blog){
        //消息落redis,无用pass
        ValueOperations ops = redisTemplate.opsForValue();
        ops.set("inform",blog);
    }*/

}
