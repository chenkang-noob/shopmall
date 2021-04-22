package com.imnoob.shopmallproduct.service;

import com.imnoob.shopmallproduct.mapper.CategoryMapper;
import com.imnoob.shopmallproduct.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public interface CategoryService {



    public List<Category> listWithTree();

    public void removeMenuByIds(List<Long> asList);

    public void save(Category category);

    public void updateBatchById(List<Category> asList);
}
