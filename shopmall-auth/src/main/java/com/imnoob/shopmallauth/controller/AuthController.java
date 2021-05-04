package com.imnoob.shopmallauth.controller;

import com.imnoob.shopmallauth.fegin.MemberFegin;
import com.imnoob.shopmallauth.service.TokenService;
import com.imnoob.shopmallauth.vo.LoginVo;
import com.imnoob.shopmallauth.vo.RegisterVo;
import com.imnoob.shopmallcommon.exception.BizCodeEnume;
import com.imnoob.shopmallcommon.to.AdminToken;
import com.imnoob.shopmallcommon.utils.AjaxResult;
import com.imnoob.shopmallcommon.utils.R;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final String keyPrefix = "Sms::";

    @Autowired
    StringRedisTemplate redisTemplate;

    @Resource
    MemberFegin memberFegin;

    @Resource
    TokenService tokenService;

    @Value("${token.tokenHeader}")
    private String access_token;

    @ResponseBody
    @PostMapping("/register")
    public R regist(@Validated RegisterVo registerVo){
        String key = keyPrefix + registerVo.getPhone();
        String s = redisTemplate.opsForValue().get(key);
        String tmp = s.split("_")[0];
        if (s == null || !registerVo.getCode().equals(tmp)){
            return R.error().put("msg", "密码错误");
        }
        R register = memberFegin.register(registerVo);
        System.out.println(register);
        return register;
    }

    @PostMapping("/login")
    public R login(@Validated LoginVo loginVo, HttpServletResponse response){

        AjaxResult<AdminToken> login = memberFegin.login(loginVo);
        AdminToken data = login.getData();
        System.out.println(login);
        if (login.getCode() == HttpStatus.SC_OK){
            String s = tokenService.publishToken(login.getData());
            response.setHeader(access_token,s);
        }

        return R.ok().put("admin",data);
    }


    @PostMapping("/sendSms")
    public R sendSms(String phone){

        String smsKey = keyPrefix+phone;
        String s = redisTemplate.opsForValue().get(smsKey);

        if (s != null){
            //短信防刷
            String[] strs = s.split("_");
            long now = System.currentTimeMillis();
            if (now - Long.parseLong(strs[1]) <60*1000)
                return R.error().put("errormsg", BizCodeEnume.SMS_EXCEPTION.getMsg());
        }

        String code = UUID.randomUUID().toString().substring(0, 5);
        System.out.println("验证码： "+ code);
        long l = System.currentTimeMillis();
        String val = code+"_"+l;
        redisTemplate.opsForValue().set(smsKey,val,5, TimeUnit.MINUTES);
        return R.ok().put("code",code);
    }
}
