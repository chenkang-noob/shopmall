package com.imnoob.shopmallmember.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * »áÔ±ÊÕ»õµØÖ·
 * </p>
 *
 * @author chenkang
 * @since 2021-05-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_member_receive_address")
public class MemberReceiveAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * member_id
     */
    private Long memberId;

    /**
     * ÊÕ»õÈËÐÕÃû
     */
    private String name;

    /**
     * µç»°
     */
    private String phone;

    /**
     * ÓÊÕþ±àÂë
     */
    private String postCode;

    /**
     * Ê¡·Ý/Ö±Ï½ÊÐ
     */
    private String province;

    /**
     * ³ÇÊÐ
     */
    private String city;

    /**
     * Çø
     */
    private String region;

    /**
     * ÏêÏ¸µØÖ·(½ÖµÀ)
     */
    private String detailAddress;

    /**
     * Ê¡ÊÐÇø´úÂë
     */
    private String areacode;

    /**
     * ÊÇ·ñÄ¬ÈÏ
     */
    private Boolean defaultStatus;


}
