package com.imnoob.shopmallauth.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Validated
@Data
public class RegisterVo {

    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$",message = "手机号格式错误")
    private String phone;

    @NotNull(message = "邮箱不为空")
    @Email(message = "邮箱格式错误")
    private String email;

    @NotNull(message = "密码不为空")
    @Length(min = 6,max = 16,message = "长度在6到16之间")
    private String password;

    @NotNull(message = "验证码不为空")
    private String code;
}
