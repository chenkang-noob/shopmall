package com.imnoob.shopmallproduct.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imnoob.shopmallproduct.mapper.ProductAttrValueMapper;
import com.imnoob.shopmallproduct.model.ProductAttrValue;
import com.imnoob.shopmallproduct.service.ProductAttrValueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueMapper, ProductAttrValue> implements ProductAttrValueService {
    @Resource
    ProductAttrValueMapper productAttrValueMapper;

    @Override
    public List<ProductAttrValue> baseAttrlistforspu(Long spuId) {
        List<ProductAttrValue> entities = productAttrValueMapper.selectList(new QueryWrapper<ProductAttrValue>().eq("spu_id", spuId));
        return entities;
    }

    @Transactional
    @Override
    public void updateSpuAttr(Long spuId, List<ProductAttrValue> entities) {
        //1、删除这个spuId之前对应的所有属性
        productAttrValueMapper.delete(new QueryWrapper<ProductAttrValue>().eq("spu_id",spuId));

        List<ProductAttrValue> collect = entities.stream().map(item -> {
            item.setSpuId(spuId);
            return item;
        }).collect(Collectors.toList());

        this.saveBatch(collect);
    }


}
