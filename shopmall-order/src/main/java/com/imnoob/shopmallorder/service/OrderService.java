package com.imnoob.shopmallorder.service;

import com.imnoob.shopmallorder.model.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imnoob.shopmallorder.vo.OrderConfirmVo;

/**
 * <p>
 * ¶©µ¥ 服务类
 * </p>
 *
 * @author chenkang
 * @since 2021-05-05
 */
public interface OrderService extends IService<Order> {

    Order createOrder(Long memberId, OrderConfirmVo orderinfo);

    Order queryByOrderSn(String orderSn);

    Integer payOrder(String orderSn);
    Integer expiredOrder(String orderSn);


}
