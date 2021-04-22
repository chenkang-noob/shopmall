package com.imnoob.shopmallware.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * ¿â´æ¹¤×÷µ¥
 * </p>
 *
 * @author chenkang
 * @since 2021-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wms_ware_order_task")
public class WareOrderTask implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * order_id
     */
    private Long orderId;

    /**
     * order_sn
     */
    private String orderSn;

    /**
     * ÊÕ»õÈË
     */
    private String consignee;

    /**
     * ÊÕ»õÈËµç»°
     */
    private String consigneeTel;

    /**
     * ÅäËÍµØÖ·
     */
    private String deliveryAddress;

    /**
     * ¶©µ¥±¸×¢
     */
    private String orderComment;

    /**
     * ¸¶¿î·½Ê½¡¾ 1:ÔÚÏß¸¶¿î 2:»õµ½¸¶¿î¡¿
     */
    private Boolean paymentWay;

    /**
     * ÈÎÎñ×´Ì¬
     */
    private Integer taskStatus;

    /**
     * ¶©µ¥ÃèÊö
     */
    private String orderBody;

    /**
     * ÎïÁ÷µ¥ºÅ
     */
    private String trackingNo;

    /**
     * create_time
     */
    private Date createTime;

    /**
     * ²Ö¿âid
     */
    private Long wareId;

    /**
     * ¹¤×÷µ¥±¸×¢
     */
    private String taskComment;


}
