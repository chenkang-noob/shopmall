package com.imnoob.shopmallcommon.exception;

public class CustomizeException extends Exception {
    private Integer code;

    public CustomizeException(Integer code,String msg) {
        super(msg);
        this.code = code;
    }

    public CustomizeException(BizCodeEnume en) {
        super(en.getMsg());
        this.code = en.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
