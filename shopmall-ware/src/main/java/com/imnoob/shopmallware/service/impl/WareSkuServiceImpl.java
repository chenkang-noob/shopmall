package com.imnoob.shopmallware.service.impl;

import com.imnoob.shopmallcommon.exception.BizCodeEnume;
import com.imnoob.shopmallcommon.exception.CustomizeException;
import com.imnoob.shopmallware.model.WareSku;
import com.imnoob.shopmallware.mapper.WareSkuMapper;
import com.imnoob.shopmallware.service.WareSkuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imnoob.shopmallware.vo.LockWareVo;
import com.imnoob.shopmallware.vo.SkuStockVo;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            skuStockVo.setStorageNum(stock);
            return skuStockVo;
        }).collect(Collectors.toList());

        return res;

    }

    @GlobalTransactional
    @Transactional
    @Override
    public Boolean lockStock(List<LockWareVo> list) {
        for (LockWareVo item : list) {
            Integer integer = wareSkuMapper.lockStock(item.getSkuId(), item.getNeedNum());
            if (integer == 0) {
                //库存不足
                throw new CustomizeException(BizCodeEnume.WARE_SHORTAGE);
            }
        }
        return true;

    }
}
