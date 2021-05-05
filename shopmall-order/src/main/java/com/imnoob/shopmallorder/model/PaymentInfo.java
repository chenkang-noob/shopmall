package com.imnoob.shopmallorder.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * Ö§¸¶ÐÅÏ¢±í
 * </p>
 *
 * @author chenkang
 * @since 2021-05-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("oms_payment_info")
public class PaymentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * ¶©µ¥ºÅ£¨¶ÔÍâÒµÎñºÅ£©
     */
    private String orderSn;

    /**
     * ¶©µ¥id
     */
    private Long orderId;

    /**
     * Ö§¸¶±¦½»Ò×Á÷Ë®ºÅ
     */
    private String alipayTradeNo;

    /**
     * Ö§¸¶×Ü½ð¶î
     */
    private BigDecimal totalAmount;

    /**
     * ½»Ò×ÄÚÈÝ
     */
    private String subject;

    /**
     * Ö§¸¶×´Ì¬
     */
    private String paymentStatus;

    /**
     * ´´½¨Ê±¼ä
     */
    private Date createTime;

    /**
     * È·ÈÏÊ±¼ä
     */
    private Date confirmTime;

    /**
     * »Øµ÷ÄÚÈÝ
     */
    private String callbackContent;

    /**
     * »Øµ÷Ê±¼ä
     */
    private Date callbackTime;


}
