package com.imnoob.shopmallcommon.vo.rabbitTo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class KillOrderTo {

    private String orderSn;

    private Long memberId;
    //秒杀场次消息
    private Long taskId;
    private String killName;

    //商品信息
    private Long skuId;
    private String skuName;
    private Integer num;
    private BigDecimal killPrice;
}
