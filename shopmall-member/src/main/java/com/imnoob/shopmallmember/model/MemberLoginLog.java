package com.imnoob.shopmallmember.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * »áÔ±µÇÂ¼¼ÇÂ¼
 * </p>
 *
 * @author chenkang
 * @since 2021-05-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_member_login_log")
public class MemberLoginLog implements Serializable {

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
     * ´´½¨Ê±¼ä
     */
    private Date createTime;

    /**
     * ip
     */
    private String ip;

    /**
     * city
     */
    private String city;

    /**
     * µÇÂ¼ÀàÐÍ[1-web£¬2-app]
     */
    private Boolean loginType;


}
