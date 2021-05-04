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
 * »áÔ±
 * </p>
 *
 * @author chenkang
 * @since 2021-05-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_member")
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * »áÔ±µÈ¼¶id
     */
    private Long levelId;

    /**
     * ÓÃ»§Ãû
     */
    private String username;

    /**
     * ÃÜÂë
     */

     private String password;

    /**
     * êÇ³Æ
     */
    private String nickname;

    /**
     * ÊÖ»úºÅÂë
     */
    private String mobile;

    /**
     * ÓÊÏä
     */
    private String email;

    /**
     * Í·Ïñ
     */
    private String header;

    /**
     * ÐÔ±ð
     */
    private Integer gender;

    /**
     * ÉúÈÕ
     */
    private Date birth;

    /**
     * ËùÔÚ³ÇÊÐ
     */
    private String city;

    /**
     * Ö°Òµ
     */
    private String job;

    /**
     * ¸öÐÔÇ©Ãû
     */
    private String sign;

    /**
     * ÓÃ»§À´Ô´
     */
    private Integer sourceType;

    /**
     * »ý·Ö
     */
    private Integer integration;

    /**
     * ³É³¤Öµ
     */
    private Integer growth;

    /**
     * ÆôÓÃ×´Ì¬
     */
    private Integer status;

    /**
     * ×¢²áÊ±¼ä
     */
    private Date createTime;


}
