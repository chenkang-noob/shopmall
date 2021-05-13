package com.imnoob.shopmallcommon.vo.rabbitTo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrderTo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String orderSn;
    private Date createTime;
    private Integer status;

    private Integer OrderType; //0 表示普通订单


}
