package com.imnoob.shopmallorder.service.impl;

import com.imnoob.shopmallorder.service.ShopCartService;
import com.imnoob.shopmallorder.vo.CartItem;
import com.imnoob.shopmallorder.vo.ShopCartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ShopCartServiceImpl implements ShopCartService {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public ShopCartVo addShop(Long userId, CartItem item){

        String key = "shopcart::" + String.valueOf(userId);
        String val = String.valueOf(item.getSkuId());


        Object o = redisTemplate.opsForHash().get(key, val);
        CartItem cartItem = (CartItem) (redisTemplate.opsForHash().get(key, val));
        if (cartItem == null){
            redisTemplate.opsForHash().put(key,val,item);
        }
        ShopCartVo shopCartVo = shopcartInfo(userId);
        return shopCartVo;

    }



    public ShopCartVo shopcartInfo(Long userId, Long[] skuids){
        ShopCartVo shopCartVo = shopcartInfo( userId);
        List<CartItem> items = shopCartVo.getItems();
        Map<Long, CartItem> map = items.stream().collect(Collectors.toMap(CartItem::getSkuId, CartItem->CartItem));
        items.clear();
        Integer num = 0;
        BigDecimal totalPrice = new BigDecimal(0);
        Integer typeNum = 0;

        for (Long id : skuids) {
            CartItem cartItem = map.get(id);
            items.add(cartItem);
            typeNum++;
            num = num + cartItem.getNum();
            totalPrice = totalPrice.add(cartItem.getTotalPrice()) ;
        }
        shopCartVo.setTotalNum(num);
        shopCartVo.setTotalPrice(totalPrice);
        shopCartVo.setTypeNum(typeNum);
        shopCartVo.setItems(items);
        return shopCartVo;
    }

    @Override
    public ShopCartVo shopcartInfo(Long userId) {
        ArrayList<Object> list = new ArrayList<>();
        List<Object> items = redisTemplate.opsForHash().values("shopcart::" + userId);
        ShopCartVo shopCartVo = new ShopCartVo();
        List<CartItem> items1 = shopCartVo.getItems();

        for (Object item : items) {
            CartItem tmp = (CartItem) item;

            //  TODO 远程调用查询价格
            items1.add(tmp);
        }
        return shopCartVo;
    }
}
