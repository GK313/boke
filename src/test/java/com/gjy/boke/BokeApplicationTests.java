package com.gjy.boke;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gjy.boke.dao.BlogDao;
import com.gjy.boke.dao.TagDao;
import com.gjy.boke.dao.TypeDao;
import com.gjy.boke.dao.UserDao;
import com.gjy.boke.entity.Blog;
import com.gjy.boke.entity.Tag;
import com.gjy.boke.entity.Type;
import com.gjy.boke.entity.User;
import com.gjy.boke.queryvo.BlogQuery;
import com.gjy.boke.queryvo.BlogTypeQuery;
import com.gjy.boke.service.BlogService;
import com.gjy.boke.service.Impl.TypeServiceImpl;
import com.gjy.boke.service.TagService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
class BokeApplicationTests {
    @Resource
    TypeServiceImpl typeService;
    @Resource
    TagService tagService;
    @Resource
    BlogService blogService;

    @Resource
    BlogDao blogDao;

    @Resource
    TagDao tagDao;

    @Resource
    TypeDao typeDao;

    @Resource
    UserDao userDao;

    @Test
    public void test() {
        PageHelper.startPage(3,3);
        List<Tag> tags = tagService.GetTagList();
        //获取分页结果对象
        PageInfo<Tag> p = new PageInfo<>(tags);
        System.out.println("1-当前页所处的页码:"+p.getPageNum());
        System.out.println("2-分页后的总页数:"+p.getPages());
        System.out.println("3-下一页的页码:"+p.getNextPage());
        System.out.println("4-每页存放的数据量:"+p.getPageSize());
        System.out.println("5-数据库中总记录数:"+p.getTotal());
        System.out.println("6-最后一行数据的行数:"+p.getEndRow());
        System.out.println("7-相对于当前页的前一页的页码:"+p.getPrePage());
        System.out.println("8-获取第一页的页码:"+p.getNavigateFirstPage());
        System.out.println("9-获取最后一页的页码:"+p.getNavigateLastPage());
        System.out.println("10-获取当前页的数据，存放到数组中,并显示数组中的记录数:"+p.getNavigatepageNums());
        System.out.println("11-导航页码数:"+p.getNavigatePages());
        System.out.println("12-当前页的数据量:"+p.getSize());
        System.out.println("13-获取起始页的页码:"+p.getStartRow());
        System.out.println("14-获取第一页的页码:"+p.getFirstPage());
        System.out.println("15-获取最后一页的页码:"+p.getLastPage());
        System.out.println("16-判断是否有下一页:"+p.isHasNextPage());
        System.out.println("17-判断是否是第一页:"+p.isIsFirstPage());
        System.out.println("18-判断是否是最后一页:"+p.isIsLastPage());
        System.out.println("19-判断是否有前一页:"+p.isHasPreviousPage());
        System.out.println("20-获取当前页的数据量:"+p.getSize());

    }

    @Test
    public void test4(){
        List<Blog> allBlog = blogService.getAllBlog();
        PageHelper.startPage(1,3);
        PageInfo<Blog> blogPageInfo = new PageInfo<>(allBlog);
        List<Blog> list = blogPageInfo.getList();
        for (Blog blog : list) {
            System.out.println(blog.getType().getName());
        }
    }
    @Test
    public void test5(){
        List<Type> typeOrderByBlogNumber = typeDao.getTypeOrderByBlogNumber();
        System.out.println(typeOrderByBlogNumber);
    }

    @Test
    public void test6(){
        List<BlogTypeQuery> blogTypeQuery = blogDao.getBlogTypeQuery();
        System.out.println(blogTypeQuery);
    }

    @Test
    public void test7() {
        /*List<BlogQuery> blogQueries = blogDao.GetBlogquery();
        //key为标签的id，value为该标签所对应的博客数量
        HashMap<Long, Integer> map = new LinkedHashMap<>();

        for (BlogQuery blogQuery : blogQueries) {
            String[] tagid = blogQuery.getTagids().split(",");
            for (String s : tagid) {
                long id = Long.parseLong(s);
                if(map.containsKey(id)){
                    //如果map中存在该键，则value加1
                    map.put(id,map.get(id)+1);
                }else{
                    map.put(id,1);
                }
            }
        }

        Set<Long> longs = map.keySet();
        for (Long aLong : longs) {
            Integer value = map.get(aLong);
            System.out.println("标签id:"+aLong + ":记录数"+value);
        }

        ArrayList<Tag> tags = new ArrayList<>();

        Set<Long> ids = map.keySet();
        int num=0;
        for (Long id : ids) {
            if(num==5) break;
            Tag tag = tagDao.GetTagById(id);
            tags.add(tag);
            num++;
        }

        for (Tag tag : tags) {
            System.out.println(tag.getId() + ":" + tag.getName());
        }
*/
    }
}
