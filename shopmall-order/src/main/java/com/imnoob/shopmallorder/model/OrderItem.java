package com.imnoob.shopmallorder.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * ¶©µ¥ÏîÐÅÏ¢
 * </p>
 *
 * @author chenkang
 * @since 2021-05-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("oms_order_item")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * order_id
     */
    private Long orderId;

    /**
     * order_sn
     */
    private String orderSn;

    /**
     * spu_id
     */
    private Long spuId;

    /**
     * spu_name
     */
    private String spuName;

    /**
     * spu_pic
     */
    private String spuPic;

    /**
     * Æ·ÅÆ
     */
    private String spuBrand;

    /**
     * ÉÌÆ··ÖÀàid
     */
    private Long categoryId;

    /**
     * ÉÌÆ·sku±àºÅ
     */
    private Long skuId;

    /**
     * ÉÌÆ·skuÃû×Ö
     */
    private String skuName;

    /**
     * ÉÌÆ·skuÍ¼Æ¬
     */
    private String skuPic;

    /**
     * ÉÌÆ·sku¼Û¸ñ
     */
    private BigDecimal skuPrice;

    /**
     * ÉÌÆ·¹ºÂòµÄÊýÁ¿
     */
    private Integer skuQuantity;

    /**
     * ÉÌÆ·ÏúÊÛÊôÐÔ×éºÏ£¨JSON£©
     */
    private String skuAttrsVals;

    /**
     * ÉÌÆ·´ÙÏú·Ö½â½ð¶î
     */
    private BigDecimal promotionAmount;

    /**
     * ÓÅ»ÝÈ¯ÓÅ»Ý·Ö½â½ð¶î
     */
    private BigDecimal couponAmount;

    /**
     * »ý·ÖÓÅ»Ý·Ö½â½ð¶î
     */
    private BigDecimal integrationAmount;

    /**
     * ¸ÃÉÌÆ·¾­¹ýÓÅ»ÝºóµÄ·Ö½â½ð¶î
     */
    private BigDecimal realAmount;

    /**
     * ÔùËÍ»ý·Ö
     */
    private Integer giftIntegration;

    /**
     * ÔùËÍ³É³¤Öµ
     */
    private Integer giftGrowth;


}
