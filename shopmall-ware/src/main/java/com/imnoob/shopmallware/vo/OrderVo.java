package com.imnoob.shopmallware.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderVo implements Serializable {
    private static final long serialVersionUID = 1L;


    private Long id;


    private String orderSn;

    private Date createTime;


    private Integer status;



}
