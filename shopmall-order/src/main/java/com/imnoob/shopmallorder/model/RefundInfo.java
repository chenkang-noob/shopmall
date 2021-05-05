package com.imnoob.shopmallorder.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * ÍË¿îÐÅÏ¢
 * </p>
 *
 * @author chenkang
 * @since 2021-05-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("oms_refund_info")
public class RefundInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * ÍË¿îµÄ¶©µ¥
     */
    private Long orderReturnId;

    /**
     * ÍË¿î½ð¶î
     */
    private BigDecimal refund;

    /**
     * ÍË¿î½»Ò×Á÷Ë®ºÅ
     */
    private String refundSn;

    /**
     * ÍË¿î×´Ì¬
     */
    private Boolean refundStatus;

    /**
     * ÍË¿îÇþµÀ[1-Ö§¸¶±¦£¬2-Î¢ÐÅ£¬3-ÒøÁª£¬4-»ã¿î]
     */
    private Integer refundChannel;

    private String refundContent;


}
