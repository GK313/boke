package com.gjy.boke.service.Impl;

import com.gjy.boke.dao.BlogDao;
import com.gjy.boke.dao.TypeDao;
import com.gjy.boke.entity.Blog;
import com.gjy.boke.entity.Type;
import com.gjy.boke.queryvo.BlogTypeQuery;
import com.gjy.boke.service.TypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @Author GJY
 * @Date 2021/1/3 20:59
 * @Version 1.0
 */
@Service
public class TypeServiceImpl implements TypeService {
    @Resource
    private TypeDao typeDao;

    @Resource
    private BlogDao blogDao;

    @Override
    public int saveType(String name) {
        int i = typeDao.saveType(name);
        return i;
    }

    @Override
    public void deleteTypeById(Long typeId) {

    }

    @Override
    public Type updateTypeById(Long typeId, Type newType) {
        return null;
    }


    @Override
    public List<Type> getTypeList() {
        List<Type> byPage2 = typeDao.findByPage2();
        return byPage2;
    }

    @Override
    public Type getTypeByName(String name) {
        Type byname = typeDao.findByname(name);
        return byname;
    }

    @Override
    public int deleteByName(Long id) {
        int i = typeDao.deleteTypeById(id);
        return i;
    }

    @Override
    public Type getTypeById(Long id) {
        Type type = typeDao.selectByPrimaryKey(id);
        return type;
    }

    @Override
    public int updateTypeById(Long id, String name) {
        int i = typeDao.updateTypeByName(id, name);
        return i;
    }

    @Override
    public List<Type> getTypeByLimit() {
        return typeDao.getTypeByLimit();
    }

    @Override
    public List<Type> GetTypeOrderByBlogNumber() {
        return typeDao.getTypeOrderByBlogNumber();
    }

    @Override
    public List<Type> getAllTypeAndBlog() {
        return typeDao.getAllTypeAndBlog();
    }

    /**
     * 统计分类数量
     * @return
     */
    @Override
    public int getTypeCount() {
        return typeDao.getTypeCount();
    }
}
