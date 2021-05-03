package com.imnoob.shopmallproduct.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.imnoob.shopmallproduct.model.AttrGroup;
import com.imnoob.shopmallproduct.model.ProductAttrValue;
import com.imnoob.shopmallproduct.model.SpuInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class SpuInfoDetailVo {

    private static final long serialVersionUID = 1L;

   //商品本身信息
    private SpuInfo spuInfo;

    //商品属性信息
    private List<ProductAttrValue> groupVos;
}
