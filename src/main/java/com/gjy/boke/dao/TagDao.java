package com.gjy.boke.dao;

import com.gjy.boke.entity.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagDao {
    /*int deleteByPrimaryKey(Long id);

    int insert(Tag record);

    int insertSelective(Tag record);

    Tag selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Tag record);

    int updateByPrimaryKey(Tag record);*/

    /**
     * 查询所有标签
     * @return
     */
    List<Tag> selectAllTags();

    /**
     * 新增标签
     * @param name
     * @return
     */
    int saveTag(String name);


    /**
     * 根据id删除标签
     * @param id
     * @return
     */
    int deleteTagById(Long id);

    /**
     * 根据id获取标签
     * @param id
     * @return
     */
    Tag GetTagById(Long id);

    /**
     * 根据id修改对应的标签名
     * @param id
     * @param name
     * @return
     */
    int updateNameById(Long id,String name);

    /**
     * 根据标签名获取标签
     * @param name
     * @return
     */
    Tag getTagByName(String name);

    /**
     * 获取前4条标签记录
     * @return
     */
    List<Tag> getTagByLimit();
}