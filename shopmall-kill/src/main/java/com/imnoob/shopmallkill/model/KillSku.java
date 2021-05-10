package com.imnoob.shopmallkill.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class KillSku implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long skuId;

    private Long taskId;

    private String skuName;

    private Integer stock;

    private BigDecimal orignPrice;

    private BigDecimal killPrice;


}
