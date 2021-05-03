package com.imnoob.shopmallsearch.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProSearchDTO {

    //检索条件
    private String keyword;
    private Long catalogId;


   //排序条件
    private String sort;

    //过滤条件
    private String skuprice;         //价格区间
    private List<Long> brandsId;      //品牌ID

    //分页条件
    private Integer pageNum=0;
    private Integer pagesize=20;

    

}
