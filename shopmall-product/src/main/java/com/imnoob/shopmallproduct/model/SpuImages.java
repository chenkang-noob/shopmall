package com.imnoob.shopmallproduct.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * spuÍ¼Æ¬
 * </p>
 *
 * @author chenkang
 * @since 2021-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pms_spu_images")
public class SpuImages implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * spu_id
     */
    private Long spuId;

    /**
     * Í¼Æ¬Ãû
     */
    private String imgName;

    /**
     * Í¼Æ¬µØÖ·
     */
    private String imgUrl;

    /**
     * Ë³Ðò
     */
    private Integer imgSort;

    /**
     * ÊÇ·ñÄ¬ÈÏÍ¼
     */
    private Integer defaultImg;


}
