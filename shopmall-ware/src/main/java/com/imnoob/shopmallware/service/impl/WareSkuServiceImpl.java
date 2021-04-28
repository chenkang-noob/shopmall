package com.imnoob.shopmallware.service.impl;

import com.imnoob.shopmallware.model.WareSku;
import com.imnoob.shopmallware.mapper.WareSkuMapper;
import com.imnoob.shopmallware.service.WareSkuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imnoob.shopmallware.vo.SkuStockVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * ÉÌÆ·¿â´æ 服务实现类
 * </p>
 *
 * @author chenkang
 * @since 2021-04-22
 */
@Service
public class WareSkuServiceImpl extends ServiceImpl<WareSkuMapper, WareSku> implements WareSkuService {

    @Resource
    WareSkuMapper wareSkuMapper;

    @Override
    public List<SkuStockVo> gethasStock(List<Long> skuids) {

        List<SkuStockVo> res = skuids.stream().map(sku -> {
            SkuStockVo skuStockVo = new SkuStockVo();
            skuStockVo.setSkuId(sku);
            Long stock = wareSkuMapper.selectHasStcok(sku);
            if (stock == null) skuStockVo.setHasStock(false);
            else if (stock > 0) skuStockVo.setHasStock(true);
            return skuStockVo;
        }).collect(Collectors.toList());

        return res;

    }
}
