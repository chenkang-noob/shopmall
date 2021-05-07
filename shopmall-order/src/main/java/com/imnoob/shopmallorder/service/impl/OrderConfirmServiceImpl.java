package com.imnoob.shopmallorder.service.impl;

import com.imnoob.shopmallcommon.utils.R;
import com.imnoob.shopmallorder.fegin.MemberFeign;
import com.imnoob.shopmallorder.service.OrderConfirmService;
import com.imnoob.shopmallorder.service.ShopCartService;
import com.imnoob.shopmallorder.vo.MemberAddressVo;
import com.imnoob.shopmallorder.vo.OrderConfirmVo;
import com.imnoob.shopmallorder.vo.ShopCartVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderConfirmServiceImpl implements OrderConfirmService {

    @Resource
    MemberFeign memberFeign;

    @Resource
    ShopCartService shopCartService;

    @Override
    public OrderConfirmVo getOrderInfo(Long memberId,Long[] skuIds) {

        //远程调用member服务查询收获地址  可以异步优化
        R feign_res = memberFeign.getAddressInfo(memberId);
        List<MemberAddressVo> address = (List<MemberAddressVo>) feign_res.get("address");

        ShopCartVo shopCartVo = shopCartService.shopcartInfo(memberId, skuIds);

        OrderConfirmVo res = new OrderConfirmVo();
        res.setAddressVoList(address);
        res.setOrderItemVos(shopCartVo.getItems());
        res.setTotalPrice(shopCartVo.getTotalPrice());

        return res;
    }
}
