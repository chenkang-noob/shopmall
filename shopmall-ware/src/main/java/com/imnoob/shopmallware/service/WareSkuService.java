package com.imnoob.shopmallware.service;

import com.imnoob.shopmallware.model.WareSku;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imnoob.shopmallware.vo.SkuStockVo;

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
}
