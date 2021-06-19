package com.gjy.boke.Config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author GJY
 * @Date 2021/6/17 13:27
 * @Version 1.0
 */
@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue queue0(){
        return new Queue("inform");
    }

    @Bean
    public Queue queue1(){
        return new Queue("queue1");
    }
}
