package com.imnoob.shopmallware.mapper;

import com.imnoob.shopmallware.model.WareSku;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * ÉÌÆ·¿â´æ Mapper 接口
 * </p>
 *
 * @author chenkang
 * @since 2021-04-22
 */
public interface WareSkuMapper extends BaseMapper<WareSku> {

    public Long selectHasStcok(Long id);

}
