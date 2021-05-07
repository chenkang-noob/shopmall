package com.imnoob.shopmallorder.service;


import com.imnoob.shopmallorder.vo.CartItem;
import com.imnoob.shopmallorder.vo.ShopCartVo;
import org.springframework.stereotype.Service;


public interface ShopCartService {

    public ShopCartVo shopcartInfo(Long userId,Long[] skuids);
    public ShopCartVo shopcartInfo(Long userId);

    public ShopCartVo addShop(Long userId, CartItem item);




}
