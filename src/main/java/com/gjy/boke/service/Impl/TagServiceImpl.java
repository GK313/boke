package com.gjy.boke.service.Impl;

import com.gjy.boke.dao.TagDao;
import com.gjy.boke.entity.Tag;
import com.gjy.boke.service.TagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author GJY
 * @Date 2021/1/21 15:07
 * @Version 1.0
 */
@Service
public class TagServiceImpl implements TagService {
    @Resource
    TagDao tagDao;

    @Override
    public List<Tag> GetTagList() {
        return tagDao.selectAllTags();
    }

    @Override
    public int SaveTag(String name) {
        return tagDao.saveTag(name);
    }

    @Override
    public int deleteTag(Long id) {
        return tagDao.deleteTagById(id);
    }

    @Override
    public Tag getTagById(Long id) {
        return tagDao.GetTagById(id);
    }

    @Override
    public int updateTagNameById(Long id, String name) {
        return tagDao.updateNameById(id,name);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagDao.getTagByName(name);
    }

    @Override
    public List<Tag> getTagByLimit() {
        return tagDao.getTagByLimit();
    }

    @Override
    public int GetTagNumber() {
        return tagDao.getTagNumber();
    }
}

