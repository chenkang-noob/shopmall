package com.imnoob.shopmallproduct.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imnoob.shopmallcommon.utils.PageUtils;
import com.imnoob.shopmallproduct.mapper.AttrMapper;
import com.imnoob.shopmallproduct.model.Attr;
import com.imnoob.shopmallproduct.service.AttrService;
import com.imnoob.shopmallproduct.vo.AttrRespVo;
import com.imnoob.shopmallproduct.vo.AttrVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class AttrServiceImpl extends ServiceImpl<AttrMapper, Attr> implements AttrService {

    @Resource
    AttrMapper attrMapper;

    @Override
    public List queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type) {
        List<Attr> catelog_id = attrMapper.selectList(new QueryWrapper<Attr>().eq("catelog_id", catelogId));
        return catelog_id;
    }

    @Override
    public List queryPage(Map<String, Object> params) {
        QueryWrapper<Attr> wrap = new QueryWrapper<>();
        for (Map.Entry<String,Object> item : params.entrySet()){
            wrap.eq(item.getKey(), item.getValue());
        }
        List<Attr> attrs = attrMapper.selectList(wrap);
        return attrs;
    }

    @Override
    public AttrRespVo getAttrInfo(Long attrId) {
        AttrRespVo respVo = new AttrRespVo();
        Attr attrEntity = this.getById(attrId);
        BeanUtils.copyProperties(attrEntity,respVo);

        return respVo;
    }

    @Transactional
    @Override
    public void saveAttr(AttrVo attrVo) {
        Attr attr = new Attr();
        BeanUtils.copyProperties(attrVo,attr);
        //1、保存基本数据
        this.save(attr);
        //TODO 保存关联表数据
    }

    @Override
    public void updateAttr(AttrVo attr) {
        Attr attrEntity = new Attr();
        BeanUtils.copyProperties(attr,attrEntity);
        this.updateById(attrEntity);
    }

    @Override
    public void removeByIds(List<Long> asList) {
        attrMapper.deleteBatchIds(asList);
    }
}
