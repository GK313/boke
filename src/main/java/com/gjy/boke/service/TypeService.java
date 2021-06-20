package com.gjy.boke.service;

import com.gjy.boke.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;

/**
 * @Author GJY
 * @Date 2021/1/3 21:01
 * @Version 1.0
 */
public interface TypeService {
    /**
     * 新增分类
     * @param
     * @return
     */
    int saveType(String name);

    /**
     * 删除分类
     * @param typeId
     */
    void deleteTypeById(Long typeId);

    /**
     * 修改分类
     * @param typeId
     * @param newType
     * @return
     * 根据typeId定位到所要修改的标签，然后将其修改成newType
     */
    Type updateTypeById(Long typeId,Type newType);

    /**
     * 分页查询
     * @param
     * @return
     */
    List<Type> getTypeList();

    /**
     * 根据用户名查看分类
     * @param name
     * @return
     */
    Type getTypeByName(String name);

    /**
     * 根据分类id删除分类信息
     * @param id
     * @return
     */
    int deleteByName(Long id);

    /*根据 分类编号id，查询该分类*/
    Type getTypeById(Long id);

    int updateTypeById(Long id,String name);

    /**
     * 获取指定数量的前几行数据
     * @return
     */
    List<Type> getTypeByLimit();

    /**
     * 获取分类下博客最多的n条分类信息
     * @return
     */
    List<Type> GetTypeOrderByBlogNumber();

    /**
     * 获取所有分类和对应的博客
     */
    List<Type> getAllTypeAndBlog();


}
