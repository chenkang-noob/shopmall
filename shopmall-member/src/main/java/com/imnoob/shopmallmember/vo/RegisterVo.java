package com.imnoob.shopmallmember.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class RegisterVo {

    private String phone;
    private String email;
    private String password;
    private String code;
}
