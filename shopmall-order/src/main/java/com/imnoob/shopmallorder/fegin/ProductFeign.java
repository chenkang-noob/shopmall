package com.imnoob.shopmallorder.fegin;

import com.imnoob.shopmallcommon.utils.AjaxResult;
import com.imnoob.shopmallcommon.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient("shopmall-product")
@Component
public interface ProductFeign {

    @GetMapping("/product/spuinfo/skuInfo")
    public AjaxResult<Map<String,Object>> spuInfo(@RequestParam("skuId")Long skuId);
}
