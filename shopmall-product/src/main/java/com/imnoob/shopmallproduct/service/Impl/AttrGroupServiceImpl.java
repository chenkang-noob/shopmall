package com.imnoob.shopmallproduct.service.Impl;

import com.imnoob.shopmallproduct.mapper.AttrMapper;
import com.imnoob.shopmallproduct.model.Attr;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imnoob.shopmallcommon.utils.PageUtils;
import com.imnoob.shopmallcommon.utils.Query;

import com.imnoob.shopmallproduct.mapper.AttrGroupMapper;
import com.imnoob.shopmallproduct.model.AttrGroup;
import com.imnoob.shopmallproduct.service.AttrGroupService;
import org.springframework.util.StringUtils;

import java.util.Map;
@Service
public class AttrGroupServiceImpl  implements AttrGroupService {
    @Autowired
    AttrGroupMapper attrGroupMapper;
    @Override
    public List queryPage(Map<String, Object> params, Long catelogId) {
        String key = (String) params.get("key");
        QueryWrapper<AttrGroup> wrapper = new QueryWrapper<AttrGroup>();
        if(!StringUtils.isEmpty(key)){
            wrapper.and((obj)->{
                obj.eq("attr_group_id",key).or().like("attr_group_name",key);
            });
        }
        wrapper.eq("catelog_id",catelogId);
        List<AttrGroup> attrGroups = attrGroupMapper.selectList(wrapper);
        return attrGroups;
//        if( catelogId == 0){
//            IPage<AttrGroup> page = this.page(new Query<AttrGroup>().getPage(params),
//                    wrapper);
//            return new PageUtils(page);
//        }else {
//            wrapper.eq("catelog_id",catelogId);
//            IPage<AttrGroup> page = this.page(new Query<AttrGroup>().getPage(params),
//                    wrapper);
//            return new PageUtils(page);
//        }
    }
}
