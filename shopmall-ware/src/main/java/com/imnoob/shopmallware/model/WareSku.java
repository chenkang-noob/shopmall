package com.imnoob.shopmallware.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * ÉÌÆ·¿â´æ
 * </p>
 *
 * @author chenkang
 * @since 2021-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wms_ware_sku")
public class WareSku implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * sku_id
     */
    private Long skuId;

    /**
     * ²Ö¿âid
     */
    private Long wareId;

    /**
     * ¿â´æÊý
     */
    private Integer stock;

    /**
     * sku_name
     */
    private String skuName;

    /**
     * Ëø¶¨¿â´æ
     */
    private Integer stockLocked;


}
