package com.imnoob.shopmallproduct.service.Impl;

import com.imnoob.shopmallproduct.mapper.SkuInfoMapper;
import com.imnoob.shopmallproduct.model.SkuInfo;
import com.imnoob.shopmallproduct.service.SkuinfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SkuinfoServiceImpl implements SkuinfoService {

    @Resource
    SkuInfoMapper skuInfoMapper;

    @Override
    public SkuInfo querySkuinfo(Long id) {
        return skuInfoMapper.selectById(id);
    }
}
