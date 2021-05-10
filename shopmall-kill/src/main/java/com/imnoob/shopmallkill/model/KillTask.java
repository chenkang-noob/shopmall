package com.imnoob.shopmallkill.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author chenkang
 * @since 2021-05-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class KillTask implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private Long createtime;

    /**
     * 开始时间
     */
    @TableField("startTime")
    private Long starttime;

    /**
     * 结束时间
     */
    @TableField("endTime")
    private Long endtime;

    /**
     * 秒杀名称
     */
    private String killName;

    @TableField("status")
    private Integer status;


}
