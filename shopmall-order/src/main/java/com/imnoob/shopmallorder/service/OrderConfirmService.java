package com.imnoob.shopmallorder.service;

import com.imnoob.shopmallorder.vo.OrderConfirmVo;

public interface OrderConfirmService {

    public OrderConfirmVo getOrderInfo(Long userId,Long[] skuIds);
}
