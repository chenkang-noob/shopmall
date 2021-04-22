package com.imnoob.shopmallproduct.service;

import com.imnoob.shopmallproduct.vo.AttrRespVo;
import com.imnoob.shopmallproduct.vo.AttrVo;

import java.util.List;
import java.util.Map;

public interface AttrService {
    List queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type);

    List queryPage(Map<String, Object> params);

    AttrRespVo getAttrInfo(Long attrId);

    void saveAttr(AttrVo attr);

    void updateAttr(AttrVo attr);

    void removeByIds(List<Long> asList);
}
