package com.imnoob.shopmallauth.fegin;

import com.imnoob.shopmallauth.vo.LoginVo;
import com.imnoob.shopmallauth.vo.RegisterVo;
import com.imnoob.shopmallcommon.to.AdminToken;
import com.imnoob.shopmallcommon.utils.AjaxResult;
import com.imnoob.shopmallcommon.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Component
@FeignClient("shopmall-member")
public interface MemberFegin {

    @PostMapping("/member/register")
    public R register(@RequestBody RegisterVo registerVo);

    @PostMapping("/member/login")
    public AjaxResult<AdminToken> login(@RequestBody  LoginVo loginVo);
}
