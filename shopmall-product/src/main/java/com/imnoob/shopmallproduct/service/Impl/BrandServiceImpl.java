package com.imnoob.shopmallproduct.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imnoob.shopmallproduct.mapper.BrandMapper;
import com.imnoob.shopmallproduct.model.Brand;
import com.imnoob.shopmallproduct.service.BrandService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class BrandServiceImpl implements BrandService {
    @Resource
    BrandMapper brandMapper;

    @Override
    public List queryPage(Map<String, Object> params) {
        //1、获取key
        String key = (String) params.get("key");
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        List<Brand> brands = brandMapper.selectList(queryWrapper);
        return brands;
    }

    @Override
    public void updateDetail(Brand brand) {
        //保证冗余字段的数据一致
        this.updateById(brand);

    }

    @Override
    public void removeByIds(List<Long> asList) {
        brandMapper.deleteBatchIds(asList);
    }

    @Override
    public void updateById(Brand brand) {
        brandMapper.updateById(brand);
    }

    @Override
    public void save(Brand brand) {

    }

    @Override
    public Brand getById(Long brandId) {

        return brandMapper.selectById(brandId);
    }
}
