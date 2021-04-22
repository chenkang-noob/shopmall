package com.imnoob.shopmallproduct.model;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

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


    @TableId(value = "cat_id", type = IdType.AUTO)
    private Long catId;

    @NotNull(message = "名字不能为空")
    private String name;


    private Long parentCid;


    private Integer catLevel;

    @TableLogic
    private Integer showStatus;


    private Integer sort;


    private String icon;


    private String productUnit;


    private Integer productCount;

    @TableField(exist = false)
    private List<Category> list;
}
