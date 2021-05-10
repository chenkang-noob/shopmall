package com.imnoob.shopmallkill.to;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SkuTo {

    private Long id;

    private Long skuId;

    private Long taskId;

    private String skuName;

    private Integer stock;

    private BigDecimal orignPrice;

    private BigDecimal killPrice;

    private String randKey;   //随机码 防止刷单

}
