package com.imnoob.shopmallmember.service;

import com.imnoob.shopmallmember.model.MemberReceiveAddress;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imnoob.shopmallmember.vo.MemberAdressVo;

import java.util.List;

/**
 * <p>
 * »áÔ±ÊÕ»õµØÖ· 服务类
 * </p>
 *
 * @author chenkang
 * @since 2021-05-03
 */
public interface MemberReceiveAddressService extends IService<MemberReceiveAddress> {

    List<MemberAdressVo> getAddressInfo(Long memberId);
}
