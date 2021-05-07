package com.imnoob.shopmallproduct.controller;

import com.imnoob.shopmallcommon.utils.AjaxResult;
import com.imnoob.shopmallcommon.utils.R;
import com.imnoob.shopmallproduct.model.SkuInfo;
import com.imnoob.shopmallproduct.service.SkuinfoService;
import com.imnoob.shopmallproduct.service.SpuinfoService;
import com.imnoob.shopmallproduct.vo.SkuInfoVo;
import com.imnoob.shopmallproduct.vo.SpuInfoDetailVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("product/spuinfo")
public class SpuInfoController {

    @Resource
    SpuinfoService spuinfoService;

    @Resource
    SkuinfoService skuinfoService;

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


    //获取sku 和 spu信息
    @GetMapping("/skuInfo")
    public AjaxResult<Map<String,Object>> spuInfo(@RequestParam("skuId")Long skuId){
        SkuInfo skuInfo = skuinfoService.querySkuinfo(skuId);
        SpuInfoDetailVo spuinfo = spuinfoService.getDetailInfo(skuInfo.getSpuId());
        HashMap<String, Object> hmap = new HashMap<String, Object>();
        hmap.put("spuinfo", spuinfo);
        hmap.put("skuinfo", skuInfo);

        return AjaxResult.ok(hmap);
    }
}
