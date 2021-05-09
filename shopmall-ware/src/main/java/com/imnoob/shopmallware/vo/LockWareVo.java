package com.imnoob.shopmallware.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class LockWareVo implements Serializable {

    private String orderSn;
    private Long skuId;
    private Integer needNum;
}
