package com.imnoob.shopmallorder.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * spuÐÅÏ¢
 * </p>
 *
 * @author chenkang
 * @since 2021-04-19
 */
@Data
public class SpuInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;


    private String spuName;

    private String spuDescription;


    private Long catalogId;


    private Long brandId;

    private BigDecimal weight;

    private Integer publishStatus;

    private Date createTime;

    private Date updateTime;


}
