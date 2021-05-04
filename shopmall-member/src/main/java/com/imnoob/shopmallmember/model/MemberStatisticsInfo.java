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
 * »áÔ±Í³¼ÆÐÅÏ¢
 * </p>
 *
 * @author chenkang
 * @since 2021-05-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_member_statistics_info")
public class MemberStatisticsInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * »áÔ±id
     */
    private Long memberId;

    /**
     * ÀÛ¼ÆÏû·Ñ½ð¶î
     */
    private BigDecimal consumeAmount;

    /**
     * ÀÛ¼ÆÓÅ»Ý½ð¶î
     */
    private BigDecimal couponAmount;

    /**
     * ¶©µ¥ÊýÁ¿
     */
    private Integer orderCount;

    /**
     * ÓÅ»ÝÈ¯ÊýÁ¿
     */
    private Integer couponCount;

    /**
     * ÆÀ¼ÛÊý
     */
    private Integer commentCount;

    /**
     * ÍË»õÊýÁ¿
     */
    private Integer returnOrderCount;

    /**
     * µÇÂ¼´ÎÊý
     */
    private Integer loginCount;

    /**
     * ¹Ø×¢ÊýÁ¿
     */
    private Integer attendCount;

    /**
     * ·ÛË¿ÊýÁ¿
     */
    private Integer fansCount;

    /**
     * ÊÕ²ØµÄÉÌÆ·ÊýÁ¿
     */
    private Integer collectProductCount;

    /**
     * ÊÕ²ØµÄ×¨Ìâ»î¶¯ÊýÁ¿
     */
    private Integer collectSubjectCount;

    /**
     * ÊÕ²ØµÄÆÀÂÛÊýÁ¿
     */
    private Integer collectCommentCount;

    /**
     * ÑûÇëµÄÅóÓÑÊýÁ¿
     */
    private Integer inviteFriendCount;


}
