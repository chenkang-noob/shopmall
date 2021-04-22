package com.imnoob.shopmallware.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author chenkang
 * @since 2021-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wms_purchase_detail")
public class PurchaseDetail implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * ²É¹ºµ¥id
     */
    private Long purchaseId;

    /**
     * ²É¹ºÉÌÆ·id
     */
    private Long skuId;

    /**
     * ²É¹ºÊýÁ¿
     */
    private Integer skuNum;

    /**
     * ²É¹º½ð¶î
     */
    private BigDecimal skuPrice;

    /**
     * ²Ö¿âid
     */
    private Long wareId;

    /**
     * ×´Ì¬[0ÐÂ½¨£¬1ÒÑ·ÖÅä£¬2ÕýÔÚ²É¹º£¬3ÒÑÍê³É£¬4²É¹ºÊ§°Ü]
     */
    private Integer status;


}
