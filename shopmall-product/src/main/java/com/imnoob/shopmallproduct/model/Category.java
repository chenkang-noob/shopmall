package com.imnoob.shopmallproduct.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * ÉÌÆ·Èý¼¶·ÖÀà
 * </p>
 *
 * @author chenkang
 * @since 2021-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pms_category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ·ÖÀàid
     */
      @TableId(value = "cat_id", type = IdType.AUTO)
    private Long catId;

    /**
     * ·ÖÀàÃû³Æ
     */
    private String name;

    /**
     * ¸¸·ÖÀàid
     */
    private Long parentCid;

    /**
     * ²ã¼¶
     */
    private Integer catLevel;

    /**
     * ÊÇ·ñÏÔÊ¾[0-²»ÏÔÊ¾£¬1ÏÔÊ¾]
     */
    private Integer showStatus;

    /**
     * ÅÅÐò
     */
    private Integer sort;

    /**
     * Í¼±êµØÖ·
     */
    private String icon;

    /**
     * ¼ÆÁ¿µ¥Î»
     */
    private String productUnit;

    /**
     * ÉÌÆ·ÊýÁ¿
     */
    private Integer productCount;


}
