package com.imnoob.shopmallorder.fegin;

import com.imnoob.shopmallcommon.utils.R;
import com.imnoob.shopmallorder.vo.LockWareVo;
import com.imnoob.shopmallorder.vo.SkuStockVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Component
@FeignClient("shopmall-ware")
public interface WareFeign {

    @PostMapping("/ware/wareSku/hasStock")
    public List<SkuStockVo> gethasStock(@RequestBody List<Long> skuids);

    @PostMapping("/ware/wareSku/LockStock")
    public R lockStock(@RequestBody List<LockWareVo> list);

}
