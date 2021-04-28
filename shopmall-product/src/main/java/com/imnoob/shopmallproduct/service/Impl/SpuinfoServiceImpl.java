package com.imnoob.shopmallproduct.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imnoob.shopmallcommon.to.SkuEsModel;
import com.imnoob.shopmallcommon.utils.R;
import com.imnoob.shopmallproduct.feign.SearchFeign;
import com.imnoob.shopmallproduct.feign.WaveFeign;
import com.imnoob.shopmallproduct.mapper.CategoryMapper;
import com.imnoob.shopmallproduct.mapper.SkuInfoMapper;
import com.imnoob.shopmallproduct.mapper.SpuInfoMapper;
import com.imnoob.shopmallproduct.model.Brand;
import com.imnoob.shopmallproduct.model.Category;
import com.imnoob.shopmallproduct.model.SkuInfo;
import com.imnoob.shopmallproduct.model.SpuInfo;
import com.imnoob.shopmallproduct.service.BrandService;
import com.imnoob.shopmallproduct.service.SpuinfoService;
import com.imnoob.shopmallproduct.vo.SkuStockVo;
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
    SkuInfoMapper skuInfoMapper;

    @Resource
    BrandService brandService;

    @Resource
    CategoryMapper categoryMapper;

    @Resource
    WaveFeign waveFeign;

    @Resource
    SearchFeign searchFeign;



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
}
