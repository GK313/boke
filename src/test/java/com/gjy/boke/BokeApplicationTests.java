package com.gjy.boke;


import com.gjy.boke.dao.BlogDao;
import com.gjy.boke.entity.Blog;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class BokeApplicationTests {

    @Resource
    BlogDao  blogDao;
    @Test
    public void test1(){

    }
}
