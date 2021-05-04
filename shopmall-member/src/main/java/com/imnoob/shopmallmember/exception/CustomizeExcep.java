package com.imnoob.shopmallmember.exception;

import lombok.Data;

@Data
public class CustomizeExcep extends RuntimeException {

    private Integer code;
    private String msg;

    public CustomizeExcep(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public CustomizeExcep(CustomizeEnum customizeEnum) {
        super(customizeEnum.getMsg());
        this.code = customizeEnum.getCode();
        this.msg = customizeEnum.getMsg();
    }
}
