package com.imnoob.shopmallproduct.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imnoob.shopmallcommon.to.SkuEsModel;
import com.imnoob.shopmallcommon.utils.R;
import com.imnoob.shopmallproduct.feign.SearchFeign;
import com.imnoob.shopmallproduct.feign.WaveFeign;
import com.imnoob.shopmallproduct.mapper.*;
import com.imnoob.shopmallproduct.model.*;
import com.imnoob.shopmallproduct.service.BrandService;
import com.imnoob.shopmallproduct.service.SpuinfoService;
import com.imnoob.shopmallproduct.vo.SkuInfoVo;
import com.imnoob.shopmallproduct.vo.SkuStockVo;
import com.imnoob.shopmallproduct.vo.SpuInfoDetailVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SpuinfoServiceImpl implements SpuinfoService {

    @Resource
    SpuInfoMapper spuInfoMapper;

    @Resource
    SkuInfoMapper skuInfoMapper;

    @Resource
    BrandService brandService;

    @Resource
    CategoryMapper categoryMapper;

    @Resource
    WaveFeign waveFeign;

    @Resource
    SearchFeign searchFeign;

    @Resource
    ProductAttrValueMapper productAttrValueMapper;

    @Resource
    SkuSaleAttrValueMapper skuSaleAttrValueMapper;



    @Override
    public R up(Long spuId) {
        List<SkuEsModel> list = new ArrayList<>();

        QueryWrapper<SkuInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("spu_id", spuId);
        List<SkuInfo> res = skuInfoMapper.selectList(queryWrapper);
        List<Long> skuids = res.stream().map(SkuInfo::getSkuId).collect(Collectors.toList());

        Map<Long, Boolean> stockmap = null;

        try {
            List<SkuStockVo> skustocks = waveFeign.gethasStock(skuids);
            stockmap = skustocks.stream().collect(Collectors.toMap(SkuStockVo::getSkuId, SkuStockVo::getHasStock));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<Long, Boolean> finalStockmap = stockmap;
        List<SkuEsModel> skuEsModels = res.stream().map(sku -> {
            SkuEsModel skumodel = new SkuEsModel();
            BeanUtils.copyProperties(sku, skumodel);

            skumodel.setSkuPrice(sku.getPrice());
            skumodel.setSkuImg(sku.getSkuDefaultImg());

            Long brandId = skumodel.getBrandId();
            Brand brand = brandService.getById(brandId);

            skumodel.setBrandImg(brand.getLogo());
            skumodel.setBrandName(brand.getName());

            Category category = categoryMapper.selectById(skumodel.getCatalogId());
            skumodel.setCatalogName(category.getName());

            if (finalStockmap != null)
                skumodel.setHasStock(finalStockmap.get(skumodel.getSkuId()));
            else
                skumodel.setHasStock(true);

            return skumodel;
        }).collect(Collectors.toList());

        //TODO 接口幂等性 异常处理
        //skuEsModels
        return searchFeign.upgoods(skuEsModels);
    }

    @Override
    public SpuInfoDetailVo getDetailInfo(Long spuId) {
        SpuInfoDetailVo info = new SpuInfoDetailVo();

        //添加商品信息
        SpuInfo skuInfo = spuInfoMapper.selectById(spuId);
        info.setSpuInfo(skuInfo);

        //添加属性信息；
        List<ProductAttrValue> attrinfos = productAttrValueMapper.selectList(new QueryWrapper<ProductAttrValue>().eq("spu_id", spuId));
        info.setGroupVos(attrinfos);

        return info;
    }

    @Override
    public List<SkuInfoVo> getSkuInfos(Long spuId) {
        List<SkuInfoVo> res = new ArrayList<>();
        List<SkuInfo> spus = skuInfoMapper.selectList(new QueryWrapper<SkuInfo>().eq("spu_id", spuId));
        for (SkuInfo skuInfo : spus) {
            SkuInfoVo skuInfoVo = new SkuInfoVo();
            skuInfoVo.setSkuInfo(skuInfo);
            List<SkuSaleAttrValue> sku_id = skuSaleAttrValueMapper.selectList(new QueryWrapper<SkuSaleAttrValue>().eq("sku_id", skuInfo.getSkuId()));
            skuInfoVo.setAttrs(sku_id);
            res.add(skuInfoVo);
        }

        return res;
    }
}
