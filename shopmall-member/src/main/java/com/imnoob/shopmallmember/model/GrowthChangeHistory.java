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
 * ³É³¤Öµ±ä»¯ÀúÊ·¼ÇÂ¼
 * </p>
 *
 * @author chenkang
 * @since 2021-05-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_growth_change_history")
public class GrowthChangeHistory implements Serializable {

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
     * create_time
     */
    private Date createTime;

    /**
     * ¸Ä±äµÄÖµ£¨Õý¸º¼ÆÊý£©
     */
    private Integer changeCount;

    /**
     * ±¸×¢
     */
    private String note;

    /**
     * »ý·ÖÀ´Ô´[0-¹ºÎï£¬1-¹ÜÀíÔ±ÐÞ¸Ä]
     */
    private Integer sourceType;


}
