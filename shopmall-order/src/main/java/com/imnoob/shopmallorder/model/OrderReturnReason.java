package com.imnoob.shopmallorder.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * ÍË»õÔ­Òò
 * </p>
 *
 * @author chenkang
 * @since 2021-05-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("oms_order_return_reason")
public class OrderReturnReason implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * ÍË»õÔ­ÒòÃû
     */
    private String name;

    /**
     * ÅÅÐò
     */
    private Integer sort;

    /**
     * ÆôÓÃ×´Ì¬
     */
    private Boolean status;

    /**
     * create_time
     */
    private Date createTime;


}
