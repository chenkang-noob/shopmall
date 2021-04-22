package com.imnoob.shopmallproduct.service;

import com.imnoob.shopmallproduct.model.Brand;

import java.util.List;
import java.util.Map;

public interface BrandService {
    List queryPage(Map<String, Object> params);
    void updateDetail(Brand brand);

    void removeByIds(List<Long> asList);

    void updateById(Brand brand);

    void save(Brand brand);

    Brand getById(Long brandId);
}
