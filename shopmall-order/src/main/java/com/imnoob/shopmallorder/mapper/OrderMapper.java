package com.imnoob.shopmallorder.mapper;

import com.imnoob.shopmallorder.model.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * ¶©µ¥ Mapper 接口
 * </p>
 *
 * @author chenkang
 * @since 2021-05-05
 */
public interface OrderMapper extends BaseMapper<Order> {

    Integer payByOrderSn(@Param("orderSn") String orderSn);

    Integer expiredOrder(@Param("orderSn") String orderSn);
}
