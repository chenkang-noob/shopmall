package com.imnoob.shopmallmember.controller;


import com.imnoob.shopmallcommon.to.AdminToken;
import com.imnoob.shopmallcommon.utils.AjaxResult;
import com.imnoob.shopmallcommon.utils.R;
import com.imnoob.shopmallmember.model.Member;
import com.imnoob.shopmallmember.service.impl.MemberServiceImpl;
import com.imnoob.shopmallmember.vo.LoginVo;
import com.imnoob.shopmallmember.vo.RegisterVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * »áÔ± 前端控制器
 * </p>
 *
 * @author chenkang
 * @since 2021-05-03
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberServiceImpl memberService;

    @PostMapping("/register")
    public R register(@RequestBody  RegisterVo registerVo){
        Member register = memberService.register(registerVo);
        return R.ok().put("member",register);
    }

    @PostMapping("/login")
    public AjaxResult<AdminToken> login(@RequestBody LoginVo loginVo){
        Member login = memberService.login(loginVo);
        AdminToken adminToken = new AdminToken();
        BeanUtils.copyProperties(login,adminToken);
        adminToken.setLoginTime(System.currentTimeMillis());
        AjaxResult<AdminToken> res = AjaxResult.ok(adminToken);

        System.out.println(res);
        return res;
    }
}

