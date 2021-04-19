package com.imnoob.shopmallproduct.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * ÉÌÆ·ÆÀ¼Û
 * </p>
 *
 * @author chenkang
 * @since 2021-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pms_spu_comment")
public class SpuComment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * sku_id
     */
    private Long skuId;

    /**
     * spu_id
     */
    private Long spuId;

    /**
     * ÉÌÆ·Ãû×Ö
     */
    private String spuName;

    /**
     * »áÔ±êÇ³Æ
     */
    private String memberNickName;

    /**
     * ÐÇ¼¶
     */
    private Boolean star;

    /**
     * »áÔ±ip
     */
    private String memberIp;

    /**
     * ´´½¨Ê±¼ä
     */
    private Date createTime;

    /**
     * ÏÔÊ¾×´Ì¬[0-²»ÏÔÊ¾£¬1-ÏÔÊ¾]
     */
    private Boolean showStatus;

    /**
     * ¹ºÂòÊ±ÊôÐÔ×éºÏ
     */
    private String spuAttributes;

    /**
     * µãÔÞÊý
     */
    private Integer likesCount;

    /**
     * »Ø¸´Êý
     */
    private Integer replyCount;

    /**
     * ÆÀÂÛÍ¼Æ¬/ÊÓÆµ[jsonÊý¾Ý£»[{type:ÎÄ¼þÀàÐÍ,url:×ÊÔ´Â·¾¶}]]
     */
    private String resources;

    /**
     * ÄÚÈÝ
     */
    private String content;

    /**
     * ÓÃ»§Í·Ïñ
     */
    private String memberIcon;

    /**
     * ÆÀÂÛÀàÐÍ[0 - ¶ÔÉÌÆ·µÄÖ±½ÓÆÀÂÛ£¬1 - ¶ÔÆÀÂÛµÄ»Ø¸´]
     */
    private Integer commentType;


}
