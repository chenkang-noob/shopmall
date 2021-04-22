package com.imnoob.shopmallproduct.service;

import com.imnoob.shopmallproduct.model.ProductAttrValue;

import java.util.List;

public interface ProductAttrValueService {
    List<ProductAttrValue> baseAttrlistforspu(Long spuId);

    void updateSpuAttr(Long spuId, List<ProductAttrValue> entities);
}
