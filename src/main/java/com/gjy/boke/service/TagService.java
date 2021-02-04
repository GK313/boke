package com.gjy.boke.service;

import com.gjy.boke.entity.Tag;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author GJY
 * @Date 2021/1/21 15:07
 * @Version 1.0
 */

public interface TagService {
    /*
    * 查询所有标签*/
    List<Tag> GetTagList();

    /**
     * 保存标签
     * @param name
     * @return
     */
    int SaveTag(String name);


    /**
     * 删除标签
     * @param id
     * @return
     */
    int deleteTag(Long id);

    /**
     * 根据id获取标签
     * @param id
     * @return
     */
    Tag getTagById(Long id);

    /**
     * 根据id编辑对应的标签名称
     * @param id
     * @param name
     * @return
     */
    int updateTagNameById(Long id,String name);

    /**
     * 根据标签名获取对应的标签
     * @param name
     * @return
     */
    Tag getTagByName(String name);

    /**
     * 获取指定数量的前几行数据
     * @return
     */
    List<Tag> getTagByLimit();

    /**
     * 统计所有标签的数量
     */
    int GetTagNumber();
}
