package com.imnoob.shopmallproduct.handler;

import com.imnoob.shopmallcommon.exception.CustomizeException;
import com.imnoob.shopmallcommon.utils.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(basePackages = "com.imnoob.gulimallproduct.controller")
public class AdviceHandler {

    @ExceptionHandler(value = CustomizeException.class)
    public R handleVaildException(CustomizeException e){
        
        Map<String,String> errorMap = new HashMap<>();
        return R.error(e.getCode(),e.getMessage());
    }

}
