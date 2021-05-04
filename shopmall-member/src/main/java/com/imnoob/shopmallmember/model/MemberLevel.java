package com.imnoob.shopmallmember.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * »áÔ±µÈ¼¶
 * </p>
 *
 * @author chenkang
 * @since 2021-05-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_member_level")
public class MemberLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * µÈ¼¶Ãû³Æ
     */
    private String name;

    /**
     * µÈ¼¶ÐèÒªµÄ³É³¤Öµ
     */
    private Integer growthPoint;

    /**
     * ÊÇ·ñÎªÄ¬ÈÏµÈ¼¶[0->²»ÊÇ£»1->ÊÇ]
     */
    private Integer defaultStatus;

    /**
     * ÃâÔË·Ñ±ê×¼
     */
    private BigDecimal freeFreightPoint;

    /**
     * Ã¿´ÎÆÀ¼Û»ñÈ¡µÄ³É³¤Öµ
     */
    private Integer commentGrowthPoint;

    /**
     * ÊÇ·ñÓÐÃâÓÊÌØÈ¨
     */
    private Integer priviledgeFreeFreight;

    /**
     * ÊÇ·ñÓÐ»áÔ±¼Û¸ñÌØÈ¨
     */
    private Integer priviledgeMemberPrice;

    /**
     * ÊÇ·ñÓÐÉúÈÕÌØÈ¨
     */
    private Integer priviledgeBirthday;

    /**
     * ±¸×¢
     */
    private String note;


}
