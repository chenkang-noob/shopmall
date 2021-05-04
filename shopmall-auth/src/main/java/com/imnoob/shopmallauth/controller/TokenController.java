package com.imnoob.shopmallauth.controller;

import com.imnoob.shopmallauth.service.TokenService;
import com.imnoob.shopmallcommon.utils.AjaxResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/auth")
@RestController
public class TokenController {

    @Resource
    TokenService tokenService;

    @PostMapping("/checkToken")
    public AjaxResult checkToken(String token){
        Boolean checkres = tokenService.checkToken(token);
        if (checkres){
            return AjaxResult.ok();
        } else return AjaxResult.error();

    }

}
