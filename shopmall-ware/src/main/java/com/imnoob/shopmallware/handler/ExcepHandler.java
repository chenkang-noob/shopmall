package com.imnoob.shopmallware.handler;

import com.imnoob.shopmallcommon.exception.CustomizeException;
import com.imnoob.shopmallcommon.utils.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(basePackages = "com.imnoob.shopmallware.controller")
public class ExcepHandler {

    @ResponseBody
    @ExceptionHandler({CustomizeException.class})
    public R exceptionhandler(Exception e){

        if(e instanceof CustomizeException){
            CustomizeException exc = (CustomizeException) e;
            Integer code = exc.getCode();
            String message = exc.getMessage();
            return R.error(code, message);
        } else return R.error();

    }
}
