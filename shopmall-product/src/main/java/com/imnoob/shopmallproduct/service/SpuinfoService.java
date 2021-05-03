package com.imnoob.shopmallproduct.service;

import com.imnoob.shopmallcommon.utils.R;
import com.imnoob.shopmallproduct.vo.SkuInfoVo;
import com.imnoob.shopmallproduct.vo.SpuInfoDetailVo;

import java.util.List;

public interface SpuinfoService {


    R up(Long spuId);

    SpuInfoDetailVo getDetailInfo(Long spuId);

    List<SkuInfoVo> getSkuInfos(Long spuId);
}
