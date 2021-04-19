package com.imnoob.shopmallproduct.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * skuÐÅÏ¢
 * </p>
 *
 * @author chenkang
 * @since 2021-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pms_sku_info")
public class SkuInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * skuId
     */
      @TableId(value = "sku_id", type = IdType.AUTO)
    private Long skuId;

    /**
     * spuId
     */
    private Long spuId;

    /**
     * skuÃû³Æ
     */
    private String skuName;

    /**
     * sku½éÉÜÃèÊö
     */
    private String skuDesc;

    /**
     * ËùÊô·ÖÀàid
     */
    private Long catalogId;

    /**
     * Æ·ÅÆid
     */
    private Long brandId;

    /**
     * Ä¬ÈÏÍ¼Æ¬
     */
    private String skuDefaultImg;

    /**
     * ±êÌâ
     */
    private String skuTitle;

    /**
     * ¸±±êÌâ
     */
    private String skuSubtitle;

    /**
     * ¼Û¸ñ
     */
    private BigDecimal price;

    /**
     * ÏúÁ¿
     */
    private Long saleCount;


}
