package com.imnoob.shopmallproduct.model;

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
 * spuÐÅÏ¢
 * </p>
 *
 * @author chenkang
 * @since 2021-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pms_spu_info")
public class SpuInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ÉÌÆ·id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * ÉÌÆ·Ãû³Æ
     */
    private String spuName;

    /**
     * ÉÌÆ·ÃèÊö
     */
    private String spuDescription;

    /**
     * ËùÊô·ÖÀàid
     */
    private Long catalogId;

    /**
     * Æ·ÅÆid
     */
    private Long brandId;

    private BigDecimal weight;

    /**
     * ÉÏ¼Ü×´Ì¬[0 - ÏÂ¼Ü£¬1 - ÉÏ¼Ü]
     */
    private Integer publishStatus;

    private Date createTime;

    private Date updateTime;


}
