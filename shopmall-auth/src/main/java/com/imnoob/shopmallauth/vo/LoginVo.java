package com.imnoob.shopmallauth.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginVo {
    @NotNull(message = "用户名不能为空")
    private String username;

    @NotNull(message = "密码不能为空")
    private String password;
}
