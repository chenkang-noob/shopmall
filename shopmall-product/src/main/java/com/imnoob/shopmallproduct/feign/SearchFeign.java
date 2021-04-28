package com.imnoob.shopmallproduct.feign;

import com.alibaba.fastjson.JSON;
import com.imnoob.shopmallcommon.to.SkuEsModel;
import com.imnoob.shopmallcommon.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@FeignClient(value = "shopmall-search")
public interface SearchFeign {

    @PostMapping("/search/upgoods")
    public R upgoods(@RequestBody List<SkuEsModel> skuEsModels);
}
