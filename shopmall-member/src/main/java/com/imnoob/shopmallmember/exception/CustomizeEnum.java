package com.imnoob.shopmallmember.exception;

import lombok.Data;


public enum CustomizeEnum {
    PHONE_ERROR(10001,"手机号码重复"),
    EMAIL_ERROR(10002,"邮箱重复"),
    USER_ACCOUNT_ERROR(10003,"用户名错误"),
    USER_PASSWORD_ERROR(10004,"密码错误");

    private Integer code;
    private String msg;

    CustomizeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
