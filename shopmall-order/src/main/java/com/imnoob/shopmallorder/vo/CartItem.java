package com.imnoob.shopmallorder.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class CartItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "CartItem{" +
                "skuId=" + skuId +
                ", price=" + price +
                ", num=" + num +
                ", totalPrice=" + totalPrice +
                '}';
    }

    private Long skuId;
    private BigDecimal price;

    private Integer num;
    private BigDecimal totalPrice;

    public CartItem() {
    }

    public CartItem(Long skuId, BigDecimal price,  Integer num, BigDecimal totalPrice) {
        this.skuId = skuId;
        this.price = price;

        this.num = num;
        this.totalPrice = totalPrice;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }



    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getTotalPrice() {
        return price.multiply(new BigDecimal(num));
    }


}
