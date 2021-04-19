package com.imnoob.shopmallproduct.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * ÊôÐÔ·Ö×é
 * </p>
 *
 * @author chenkang
 * @since 2021-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pms_attr_group")
public class AttrGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ·Ö×éid
     */
      @TableId(value = "attr_group_id", type = IdType.AUTO)
    private Long attrGroupId;

    /**
     * ×éÃû
     */
    private String attrGroupName;

    /**
     * ÅÅÐò
     */
    private Integer sort;

    /**
     * ÃèÊö
     */
    private String descript;

    /**
     * ×éÍ¼±ê
     */
    private String icon;

    /**
     * ËùÊô·ÖÀàid
     */
    private Long catelogId;


}
