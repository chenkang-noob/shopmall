package com.imnoob.shopmallorder.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.*;
import java.io.Serializable;

@Data
public class OrderConfirmVo implements Serializable {

    //    收货地址
   private List<MemberAddressVo> addressVoList;

   //商品信息
   private List<CartItem> orderItemVos;

   private BigDecimal totalPrice;

   private String orderToken;
}
