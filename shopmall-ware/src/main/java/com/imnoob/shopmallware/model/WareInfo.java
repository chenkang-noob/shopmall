package com.imnoob.shopmallware.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * ²Ö¿âÐÅÏ¢
 * </p>
 *
 * @author chenkang
 * @since 2021-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wms_ware_info")
public class WareInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * ²Ö¿âÃû
     */
    private String name;

    /**
     * ²Ö¿âµØÖ·
     */
    private String address;

    /**
     * ÇøÓò±àÂë
     */
    private String areacode;


}
