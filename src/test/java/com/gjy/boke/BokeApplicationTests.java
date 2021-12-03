package com.gjy.boke;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.gjy.boke.dao.BlogDao;
import com.gjy.boke.dao.TypeDao;
import com.gjy.boke.entity.Blog;
import com.gjy.boke.entity.Type;
import com.gjy.boke.entity.User;
import com.gjy.boke.queryvo.BlogTypeQuery;
import com.gjy.boke.queryvo.BtVo;
import com.gjy.boke.queryvo.CollectCountVO;
import com.gjy.boke.service.BlogService;
import com.gjy.boke.service.TypeService;
import com.gjy.boke.service.UserService;
import com.gjy.boke.utils.MD5Utils;
import net.minidev.json.JSONArray;
import org.hibernate.cache.spi.SecondLevelCacheLogger;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class BokeApplicationTests {
/*

    @Resource
    BlogDao  blogDao;
    @Test
    public void test1(){

    }

    //注入 RedisAutoConfiguration配置类的配置的RedisTemplate
    @Resource
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() throws JsonProcessingException, InterruptedException {
        //针对key-value操作
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Boolean aBoolean = valueOperations.setIfAbsent("key1", "value1");
        System.out.println(aBoolean);
        //设置指定key的过期时间
        valueOperations.getOperations().expire("key1",200, TimeUnit.SECONDS);
        Object key1 = valueOperations.get("key1");
        System.out.println(key1);
        //根据key获取过期时间
        Long keyTimeOut = valueOperations.getOperations().getExpire("key1");
        System.out.println(keyTimeOut);
        //判断key是否存在
        Boolean key11 = valueOperations.getOperations().hasKey("key1");
        Boolean k1 = redisTemplate.hasKey("k1");
        System.out.println(key11);
        //1、通过redisTemplate设置值
        redisTemplate.boundValueOps("k2").set("v2");
        redisTemplate.boundValueOps("k3").set("v3",1,TimeUnit.MINUTES);
        //2、通过BoundValueOperations设置值
        BoundValueOperations stringkey = redisTemplate.boundValueOps("Stringkey");
        stringkey.set("StringValue");
        stringkey.set("StringValue",1,TimeUnit.MINUTES);
        //3、ValueOperation设置值
        ValueOperations valueOperations1 = redisTemplate.opsForValue();
        valueOperations1.set("k4","v4");
        valueOperations1.set("k5","v5",1,TimeUnit.SECONDS);

        //同时获取多个key对应的值
        System.out.println("======同时获取多个key对应的值=====");
        ArrayList<String> list = new ArrayList<>();
        list.add("k1");
        list.add("k2");
        list.add("k3");
        List list1 = redisTemplate.opsForValue().multiGet(list);
        for (Object o : list1) {
            System.out.println(o);
        }

        //同时设置多个key value
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("mk1","mv1");
        hashMap.put("mk2","mv2");
        hashMap.put("mk3","mv3");
        redisTemplate.opsForValue().multiSet(hashMap);


        //删除指定的key
        Boolean k4 = redisTemplate.delete("k4");

        //顺序递增
        redisTemplate.boundValueOps("StringKey").increment(3L);

        System.out.println("==============Hash=============");


        //hash数据类型操作

        //1、通过redisTemplate设置值
        redisTemplate.boundHashOps("hkey1").put("field1","value1");

        //2、通过BoundValueOperations设置值
        BoundHashOperations hkey2 = redisTemplate.boundHashOps("hkey2");
        hkey2.put("field2","value2");
        hkey2.put("field4","value4");
        System.out.println("获取指定key中的多个field-value键值对:");
        for (Object o : hkey2.entries().entrySet()) {
            System.out.println(o);
        }

        //3、通过ValueOperations设置值
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put("hkey3","field3","value3");

        //添加一个map集合到指定的key的hash集合中
        BoundHashOperations hkey5 = redisTemplate.boundHashOps("hkey5");
        HashMap<String, String> map = new HashMap<>();
        map.put("f1","v1");
        map.put("f2","v2");
        hkey5.putAll(map);
        for (Object o : hkey5.entries().entrySet()) {
            System.out.println(o);
        }

        String phone="13144466909";
        String phone2="13144466909";
        String password="1234";
        String name="雷军";
        String avator="https://picsum.photos/id/258/4608/3072";
        redisTemplate.opsForHash().put(phone,"password",password);
        HashMap<String, String> map2 = new HashMap<>();
        map2.put("name",name);
        map2.put("password",password);
        map2.put("avator",avator);
        redisTemplate.opsForHash().putAll(phone2,map2);

        Boolean passwd = redisTemplate.opsForHash().hasKey(phone, "password");
        if(passwd){
            //存在该用户，判断密码是否正确
            if (redisTemplate.opsForHash().get(phone,"password").equals("1234")) {
                System.out.println("密码正确");
            }else{
                System.out.println("密码错误");
            }
        }

        //list类型操作
        ListOperations listOperations = redisTemplate.opsForList();


        //set类型操作
        SetOperations setOperations = redisTemplate.opsForSet();


        //zset数据类型操作
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
    }
*/
    @Resource
    BlogService blogService;
    @Resource
    BlogDao blogDao;

    @Resource
    TypeDao typeDao;

    @Resource
    TypeService typeService;
    @Resource
    RedisTemplate redisTemplate;

    @Test
    public void test(){
        Blog blog = blogDao.mostCommentBlog();
        System.out.println(blog.getCommentcount());
        System.out.println(blog.getTitle());
    }


}

