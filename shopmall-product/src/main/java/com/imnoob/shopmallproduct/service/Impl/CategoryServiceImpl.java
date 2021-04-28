package com.imnoob.shopmallproduct.service.Impl;

import com.imnoob.shopmallproduct.mapper.CategoryMapper;
import com.imnoob.shopmallproduct.model.Category;
import com.imnoob.shopmallproduct.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final static String categeoryKey = "categeoryKey";

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;


    public List<Category> listWithTree(){

        Object s = redisTemplate.opsForValue().get(categeoryKey);
        if (s != null ) return  (List<Category>) s;
        List<Category> all = categoryMapper.selectList(null);

        List<Category> level1 = all.stream().filter(category -> category.getParentCid() == 0).collect(Collectors.toList());
        for (Category item : level1) {
            List<Category> leve2 = all.stream().filter(category -> category.getParentCid() == item.getCatId()).collect(Collectors.toList());
            item.setList(leve2);

            for (Category item2 : leve2) {
                List<Category> leve3 = all.stream().filter(category -> category.getParentCid() == item2.getCatId()).collect(Collectors.toList());
                item2.setList(leve3);
            }

        }
        redisTemplate.opsForValue().set(categeoryKey,level1);
        return level1;
    }

    public void removeMenuByIds(List<Long> asList) {
        //TODO  1、检查当前删除的菜单，是否被别的地方引用

        //逻辑删除
        categoryMapper.deleteBatchIds(asList);
    }

    public void save(Category category) {
        categoryMapper.insert(category);
    }

    public void updateBatchById(List<Category> asList) {
        //categoryMapper.updateBatchById();
    }
}
