package com.imnoob.shopmallproduct.vo;

import com.imnoob.shopmallproduct.model.AttrGroup;
import com.imnoob.shopmallproduct.model.ProductAttrValue;
import lombok.Data;

import java.util.List;

@Data
public class AttrGroupVo {

    private String GroupName;
    private List<ProductAttrValue> attrs;
}
