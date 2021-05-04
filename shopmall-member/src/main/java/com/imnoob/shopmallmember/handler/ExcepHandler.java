package com.imnoob.shopmallmember.handler;

import com.imnoob.shopmallcommon.utils.R;
import com.imnoob.shopmallmember.exception.CustomizeExcep;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ExcepHandler {

    @ExceptionHandler(value = CustomizeExcep.class)
    public R CustomizeExcepHandler(CustomizeExcep excep){
        return R.error(excep.getCode(), excep.getMsg());
    }
}
