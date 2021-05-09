package com.imnoob.shopmallware.feign;

import com.imnoob.shopmallcommon.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient("shopmall-order")
public interface OrderFeign {

    @GetMapping("/order/info/orderSn")
    R queryOrderByOrderSn(@RequestParam("orderSn") String orderSn);
}
