package com.imnoob.shopmallsearch.dto;

import com.imnoob.shopmallcommon.to.SkuEsModel;
import lombok.Data;

import java.util.List;

@Data
public class ProResultDTO {

    private List<SkuEsModel> products;

    //分页信息
    private Integer pageNum;
    private Long total;
    private Integer totalPage;

    
}
