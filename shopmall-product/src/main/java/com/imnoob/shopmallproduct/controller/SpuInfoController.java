package com.imnoob.shopmallproduct.controller;

import com.imnoob.shopmallcommon.utils.R;
import com.imnoob.shopmallproduct.service.SpuinfoService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("product/spuinfo")
public class SpuInfoController {

    @Resource
    SpuinfoService spuinfoService;

    @PostMapping("/upgoods/{spuId}")
    public R spuUp(@PathVariable("spuId")Long spuId){

        return spuinfoService.up(spuId);

    }
}
