package com.imnoob.shopmallproduct.vo;

import com.imnoob.shopmallproduct.model.SkuInfo;
import com.imnoob.shopmallproduct.model.SkuSaleAttrValue;
import lombok.Data;

import java.util.List;

@Data
public class SkuInfoVo {

    private SkuInfo skuInfo;

    private List<SkuSaleAttrValue> attrs;
}
