package com.imnoob.shopmallproduct.controller;

import com.imnoob.shopmallcommon.utils.R;
import com.imnoob.shopmallproduct.service.SpuinfoService;
import com.imnoob.shopmallproduct.vo.SkuInfoVo;
import com.imnoob.shopmallproduct.vo.SpuInfoDetailVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("product/spuinfo")
public class SpuInfoController {

    @Resource
    SpuinfoService spuinfoService;

//    商品上架
    @PostMapping("/upgoods/{spuId}")
    public R spuUp(@PathVariable("spuId")Long spuId){

        return spuinfoService.up(spuId);

    }

    //spu商品详情
    @GetMapping("/spudetail/{spuId}")
    public R spuDetailInfo(@PathVariable("spuId")Long spuId){
        SpuInfoDetailVo data = spuinfoService.getDetailInfo(spuId);
        return R.ok().put("data", data);
    }

    //获取spu下的sku详情
    @GetMapping("/skudetail/{spuId}")
    public R skusInfo(@PathVariable("spuId")Long spuId){
        List<SkuInfoVo> skuInfos = spuinfoService.getSkuInfos(spuId);

        return R.ok().put("data", skuInfos);
    }
}
