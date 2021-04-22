package com.imnoob.shopmallproduct.service;

import com.imnoob.shopmallcommon.utils.PageUtils;

import java.util.List;
import java.util.Map;

public interface AttrGroupService {

    public List queryPage(Map<String, Object> params, Long catelogId);
}
