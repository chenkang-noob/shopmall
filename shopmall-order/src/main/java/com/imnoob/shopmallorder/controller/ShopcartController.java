package com.imnoob.shopmallorder.controller;

import com.imnoob.shopmallcommon.utils.R;
import com.imnoob.shopmallorder.service.ShopCartService;
import com.imnoob.shopmallorder.vo.CartItem;
import com.imnoob.shopmallorder.vo.ShopCartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order/shopcart")
public class ShopcartController {

    /**
     * 实际上只需要存储商品的skuID
     * 因为价格是实时变化的，不应该写死在redis中
     */

   @Resource
    ShopCartService shopCartService;


    @PostMapping("/addshop")
    public R addShop(Long userId, CartItem item){

        ShopCartVo shopCartVo = shopCartService.addShop(userId, item);

        return R.ok().put("shopcart",shopCartVo);
    }

    @GetMapping("/info")
    public R ShopcartInfo(Long userId,Long[] skuids){
        ShopCartVo shopCartVo = null;
        if (skuids == null) {
            shopCartVo = shopCartService.shopcartInfo(userId);
        }else shopCartVo  = shopCartService.shopcartInfo(userId,skuids);

        return R.ok().put("shopcart",shopCartVo);
    }

    //删除商品

}
