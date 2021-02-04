package com.gjy.boke.dao;

import com.gjy.boke.entity.Type;
import javafx.scene.control.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/*@Mapper*/
@Repository
public interface TypeDao {
    /**
     * 根据分类编号返回一个分类实体
     * @param id
     * @return
     */
    @Transactional
    Type selectByPrimaryKey(Long id);

    /**
     * 根据分类实体更新该分类
     * @param record
     * @return
     */
    @Transactional
    int updateByPrimaryKeySelective(Type record);

    @Transactional
    int updateByPrimaryKey(Type record);

    /**
     * 传入一个分类名称来新增一个分类
     * @param name
     * @return
     */
    @Transactional
    int saveType(String name);

    /**
     * 利用Limit关键字实现分页查询
     * @param offset
     * @param PageSize
     * @return
     */
    @Transactional
    List<Type> findByPage(int offset,int PageSize);

    /**
     * 利用Mybatis-plus实现分页查询
     */
    @Transactional
    List<Type> findByPage2();

    /**
     * 根据前端所要新增的分类名称，查看数据库中该名称是否已经存在
     * @param name
     * @return
     */
    Type findByname(String name);

    /**
     * 根据前端传递的分类编号，进行删除
     * @param id
     * @return
     */
    int deleteTypeById(Long id);

    /**
     * 根据前端传递的分类名称,和要修改的编号id修改分类
     * @param name
     * @return
     */
    int  updateTypeByName(Long id,String name);

    /**
     * 获取前4条分类记录
     * @return
     */
    List<Type> getTypeByLimit();

    /**
     * 获取分类下博客最多的n条分类信息
     * @return
     */
    List<Type> getTypeOrderByBlogNumber();

    /**
     *获取所有分类和对应的博客
     */
    List<Type> getAllTypeAndBlog();
    
}