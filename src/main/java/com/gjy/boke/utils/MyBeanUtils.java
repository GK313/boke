package com.gjy.boke.utils;

import com.gjy.boke.entity.Blog;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Date;

/**
 * @Author GJY
 * @Date 2021/1/30 10:24
 * @Version 1.0
 */
public class MyBeanUtils {


    /**
     * 传进一个对象，返回该对象对应的属性值为空的所有属性名称组成的一个数组
     * @param source
     * @return
     */
    public static String[] getNullPropertiesName(Object source){
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = beanWrapper.getPropertyDescriptors();
        ArrayList<String> nullPropertyNames = new ArrayList<>();
        for (PropertyDescriptor pd : pds) {
            String propertyName = pd.getName();
            if(beanWrapper.getPropertyValue(propertyName)==null){
                nullPropertyNames.add(propertyName);
            }
        }
        return nullPropertyNames.toArray(new String [nullPropertyNames.size()]);
    }

    public static void main(String[] args) {
        Blog oldblog = new Blog();
        oldblog.setId(1l);
        oldblog.setViews(1);
        oldblog.setPublished("off");
        oldblog.setUpdatetime(new Date());
        oldblog.setTitle("t111");

        Blog newBlog = new Blog();
        newBlog.setTitle("t222");
        newBlog.setPublished("on");

        //其实就是将老的没有而新的有的那部分赋值为老的
        BeanUtils.copyProperties(newBlog,oldblog,getNullPropertiesName(oldblog));
        newBlog.setUpdatetime(new Date());

        System.out.println(newBlog);
        System.out.println(newBlog.getUpdatetime());
    }

}
