package com.imnoob.shopmallorder.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * ¶©µ¥ÅäÖÃÐÅÏ¢
 * </p>
 *
 * @author chenkang
 * @since 2021-05-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("oms_order_setting")
public class OrderSetting implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * ÃëÉ±¶©µ¥³¬Ê±¹Ø±ÕÊ±¼ä(·Ö)
     */
    private Integer flashOrderOvertime;

    /**
     * Õý³£¶©µ¥³¬Ê±Ê±¼ä(·Ö)
     */
    private Integer normalOrderOvertime;

    /**
     * ·¢»õºó×Ô¶¯È·ÈÏÊÕ»õÊ±¼ä£¨Ìì£©
     */
    private Integer confirmOvertime;

    /**
     * ×Ô¶¯Íê³É½»Ò×Ê±¼ä£¬²»ÄÜÉêÇëÍË»õ£¨Ìì£©
     */
    private Integer finishOvertime;

    /**
     * ¶©µ¥Íê³Éºó×Ô¶¯ºÃÆÀÊ±¼ä£¨Ìì£©
     */
    private Integer commentOvertime;

    /**
     * »áÔ±µÈ¼¶¡¾0-²»ÏÞ»áÔ±µÈ¼¶£¬È«²¿Í¨ÓÃ£»ÆäËû-¶ÔÓ¦µÄÆäËû»áÔ±µÈ¼¶¡¿
     */
    private Integer memberLevel;


}
