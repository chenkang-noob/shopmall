package com.imnoob.shopmallproduct.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * ÊôÐÔ&ÊôÐÔ·Ö×é¹ØÁª
 * </p>
 *
 * @author chenkang
 * @since 2021-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pms_attr_attrgroup_relation")
public class AttrAttrgroupRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * ÊôÐÔid
     */
    private Long attrId;

    /**
     * ÊôÐÔ·Ö×éid
     */
    private Long attrGroupId;

    /**
     * ÊôÐÔ×éÄÚÅÅÐò
     */
    private Integer attrSort;


}
