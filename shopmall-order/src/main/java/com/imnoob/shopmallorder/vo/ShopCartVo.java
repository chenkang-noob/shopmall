package com.imnoob.shopmallorder.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class ShopCartVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<CartItem> items;
    private BigDecimal totalPrice;
    private Integer typeNum;
    private Integer totalNum;

    public ShopCartVo() {
        items = new ArrayList<>();
        totalPrice = new BigDecimal(0);
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public BigDecimal getTotalPrice() {
        BigDecimal tmp = new BigDecimal(0);
        for (CartItem item : items) {
             tmp = tmp.add(item.getTotalPrice());
        }
        return tmp;
    }



    public Integer getTypeNum() {
        return items.size();
    }



    public Integer getTotalNum() {
        Integer tmp = 0;
        for (CartItem item : items) {
            tmp += item.getNum();
        }
        return tmp;
    }


}
