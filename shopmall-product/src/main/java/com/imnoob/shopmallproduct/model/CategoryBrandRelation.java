package com.imnoob.shopmallproduct.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * Æ·ÅÆ·ÖÀà¹ØÁª
 * </p>
 *
 * @author chenkang
 * @since 2021-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pms_category_brand_relation")
public class CategoryBrandRelation implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * Æ·ÅÆid
     */
    private Long brandId;

    /**
     * ·ÖÀàid
     */
    private Long catelogId;

    private String brandName;

    private String catelogName;


}
