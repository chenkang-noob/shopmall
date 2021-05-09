package com.imnoob.shopmallware.service;

import com.imnoob.shopmallware.model.WareSku;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imnoob.shopmallware.vo.LockWareVo;
import com.imnoob.shopmallware.vo.SkuStockVo;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * ÉÌÆ·¿â´æ 服务类
 * </p>
 *
 * @author chenkang
 * @since 2021-04-22
 */
public interface WareSkuService extends IService<WareSku> {

    List<SkuStockVo> gethasStock(List<Long> skuids);

    Boolean lockStock(List<LockWareVo> list);

    void unlockWare(Long skuId, Integer needNum);

    void unlockWare(ArrayList<LockWareVo> lockWareVos);
}
