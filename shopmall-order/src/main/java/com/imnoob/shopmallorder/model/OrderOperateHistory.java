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
 * ¶©µ¥²Ù×÷ÀúÊ·¼ÇÂ¼
 * </p>
 *
 * @author chenkang
 * @since 2021-05-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("oms_order_operate_history")
public class OrderOperateHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * ¶©µ¥id
     */
    private Long orderId;

    /**
     * ²Ù×÷ÈË[ÓÃ»§£»ÏµÍ³£»ºóÌ¨¹ÜÀíÔ±]
     */
    private String operateMan;

    /**
     * ²Ù×÷Ê±¼ä
     */
    private Date createTime;

    /**
     * ¶©µ¥×´Ì¬¡¾0->´ý¸¶¿î£»1->´ý·¢»õ£»2->ÒÑ·¢»õ£»3->ÒÑÍê³É£»4->ÒÑ¹Ø±Õ£»5->ÎÞÐ§¶©µ¥¡¿
     */
    private Integer orderStatus;

    /**
     * ±¸×¢
     */
    private String note;


}
