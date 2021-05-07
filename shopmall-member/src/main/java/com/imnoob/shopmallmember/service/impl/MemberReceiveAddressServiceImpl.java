package com.imnoob.shopmallmember.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imnoob.shopmallmember.model.MemberReceiveAddress;
import com.imnoob.shopmallmember.mapper.MemberReceiveAddressMapper;
import com.imnoob.shopmallmember.service.MemberReceiveAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imnoob.shopmallmember.vo.MemberAdressVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * »áÔ±ÊÕ»õµØÖ· 服务实现类
 * </p>
 *
 * @author chenkang
 * @since 2021-05-03
 */
@Service
public class MemberReceiveAddressServiceImpl extends ServiceImpl<MemberReceiveAddressMapper, MemberReceiveAddress> implements MemberReceiveAddressService {

    @Override
    public List<MemberAdressVo> getAddressInfo(Long memberId) {
        List<MemberReceiveAddress> res = this.baseMapper.selectList(new QueryWrapper<MemberReceiveAddress>().eq("member_id", memberId));

        ArrayList<MemberAdressVo> list = new ArrayList<>();
        res.stream().forEach(item -> {
            MemberAdressVo tmp = new MemberAdressVo();
            BeanUtils.copyProperties(item,tmp);
            list.add(tmp);
        });

        return list;
    }
}
