package com.imnoob.shopmallauth.controller;

import com.imnoob.shopmallauth.service.TokenService;
import com.imnoob.shopmallauth.utils.IPUtils;
import com.imnoob.shopmallcommon.utils.AjaxResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/auth")
@RestController
public class TokenController {

    @Resource
    TokenService tokenService;

    @PostMapping("/checkToken")
    public AjaxResult checkToken(@RequestBody String token, HttpServletRequest request, HttpServletResponse response){
        String ip = IPUtils.getIPAddr(request);
        Boolean checkres = tokenService.checkToken(token,ip);
        if (checkres){
            //TODO 常量的抽取
            response.setHeader("access_token",token);
            return AjaxResult.ok();
        } else return AjaxResult.error();

    }

}
