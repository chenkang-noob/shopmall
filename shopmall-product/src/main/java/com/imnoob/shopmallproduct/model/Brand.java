package com.imnoob.shopmallproduct.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * Æ·ÅÆ
 * </p>
 *
 * @author chenkang
 * @since 2021-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pms_brand")
public class Brand implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Æ·ÅÆid
     */
      @TableId(value = "brand_id", type = IdType.AUTO)
    private Long brandId;

    /**
     * Æ·ÅÆÃû
     */
    private String name;

    /**
     * Æ·ÅÆlogoµØÖ·
     */
    private String logo;

    /**
     * ½éÉÜ
     */
    private String descript;

    /**
     * ÏÔÊ¾×´Ì¬[0-²»ÏÔÊ¾£»1-ÏÔÊ¾]
     */
    private Integer showStatus;

    /**
     * ¼ìË÷Ê××ÖÄ¸
     */
    private String firstLetter;

    /**
     * ÅÅÐò
     */
    private Integer sort;


}
