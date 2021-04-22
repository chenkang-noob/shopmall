package com.imnoob.shopmallware.model;

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
 * ²É¹ºÐÅÏ¢
 * </p>
 *
 * @author chenkang
 * @since 2021-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wms_purchase")
public class Purchase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ²É¹ºµ¥id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * ²É¹ºÈËid
     */
    private Long assigneeId;

    /**
     * ²É¹ºÈËÃû
     */
    private String assigneeName;

    /**
     * ÁªÏµ·½Ê½
     */
    private String phone;

    /**
     * ÓÅÏÈ¼¶
     */
    private Integer priority;

    /**
     * ×´Ì¬
     */
    private Integer status;

    /**
     * ²Ö¿âid
     */
    private Long wareId;

    /**
     * ×Ü½ð¶î
     */
    private BigDecimal amount;

    /**
     * ´´½¨ÈÕÆÚ
     */
    private Date createTime;

    /**
     * ¸üÐÂÈÕÆÚ
     */
    private Date updateTime;


}
