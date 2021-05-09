package com.imnoob.shopmallorder.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class LockWareVo implements Serializable {

    private Long skuId;
    private Integer needNum;
}
