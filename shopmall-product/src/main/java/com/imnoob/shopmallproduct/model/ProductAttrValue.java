package com.imnoob.shopmallproduct.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * spuÊôÐÔÖµ
 * </p>
 *
 * @author chenkang
 * @since 2021-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pms_product_attr_value")
public class ProductAttrValue implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * ÉÌÆ·id
     */
    private Long spuId;

    /**
     * ÊôÐÔid
     */
    private Long attrId;

    /**
     * ÊôÐÔÃû
     */
    private String attrName;

    /**
     * ÊôÐÔÖµ
     */
    private String attrValue;

    /**
     * Ë³Ðò
     */
    private Integer attrSort;

    /**
     * ¿ìËÙÕ¹Ê¾¡¾ÊÇ·ñÕ¹Ê¾ÔÚ½éÉÜÉÏ£»0-·ñ 1-ÊÇ¡¿
     */
    private Integer quickShow;


}
