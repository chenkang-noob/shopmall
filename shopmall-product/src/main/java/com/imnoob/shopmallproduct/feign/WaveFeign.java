package com.imnoob.shopmallproduct.feign;

import com.imnoob.shopmallproduct.vo.SkuStockVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "shopmall-ware")
@Service
public interface WaveFeign {

    @PostMapping("/ware/wareSku/hasStock")
    public List<SkuStockVo> gethasStock(@RequestBody List<Long> skuids);
}
