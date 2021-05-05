package com.imnoob.shopmallorder.controller;

import com.imnoob.shopmallcommon.utils.R;
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
    RedisTemplate<String, Object> redisTemplate;


    @PostMapping("/addshop")
    public R addShop(Long userId, CartItem item){

        CartItem cartItem = (CartItem) redisTemplate.opsForHash().get("shopcart::" + userId, item.getSkuId());
        if (cartItem == null){
            redisTemplate.opsForHash().put("shopcart::"+userId,item.getSkuId(),item);
        }
        CartItem tmp = (CartItem) redisTemplate.opsForHash().get("shopcart::" + userId, item.getSkuId());
        System.out.println(tmp.getSkuId());
        return R.ok();
    }

    @GetMapping("/info")
    public R ShopcartInfo(Long userId){
        ArrayList<Object> list = new ArrayList<>();
        List<Object> items = redisTemplate.opsForHash().values("shopcart::" + userId);
        ShopCartVo shopCartVo = new ShopCartVo();
        List<CartItem> items1 = shopCartVo.getItems();

        for (Object item : items) {
            items1.add((CartItem) item);
        }

        return R.ok().put("shopcart",shopCartVo);
    }

    //删除商品

}
