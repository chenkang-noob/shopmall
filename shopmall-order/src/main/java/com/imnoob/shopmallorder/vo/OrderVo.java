package com.imnoob.shopmallorder.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrderVo implements Serializable {
    private static final long serialVersionUID = 1L;


    private Long id;


    private String orderSn;

    private Date createTime;


    private Integer status;



}
