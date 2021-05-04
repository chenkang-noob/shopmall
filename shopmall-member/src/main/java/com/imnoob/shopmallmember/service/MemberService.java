package com.imnoob.shopmallmember.service;

import com.imnoob.shopmallmember.vo.LoginVo;
import com.imnoob.shopmallmember.vo.RegisterVo;
import com.imnoob.shopmallmember.model.Member;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * »áÔ± 服务类
 * </p>
 *
 * @author chenkang
 * @since 2021-05-03
 */
public interface MemberService extends IService<Member> {
    public Member register(RegisterVo registerVo);
    public Member login(LoginVo loginVo);

}
