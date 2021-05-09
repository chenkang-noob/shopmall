package com.imnoob.shopmallware.controller;


import com.imnoob.shopmallcommon.utils.R;
import com.imnoob.shopmallware.service.WareSkuService;
import com.imnoob.shopmallware.vo.LockWareVo;
import com.imnoob.shopmallware.vo.SkuStockVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * ÉÌÆ·¿â´æ 前端控制器
 * </p>
 *
 * @author chenkang
 * @since 2021-04-22
 */
@RestController
@RequestMapping("ware/wareSku")
public class WareSkuController {

    @Resource
    WareSkuService wareSkuService;

    @PostMapping("/hasStock")
    public List<SkuStockVo> gethasStock(@RequestBody List<Long> skuids){

        List<SkuStockVo> res = wareSkuService.gethasStock(skuids);
        return  res;
    }

    @PostMapping("/LockStock")
    public R lockStock(@RequestBody List<LockWareVo> list){
        Boolean res = wareSkuService.lockStock(list);
        if (res) return R.ok();
        else return R.error("库存锁定失败");
    }



}

