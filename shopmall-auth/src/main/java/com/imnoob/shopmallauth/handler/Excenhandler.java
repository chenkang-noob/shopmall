package com.imnoob.shopmallauth.handler;

import com.imnoob.shopmallcommon.exception.BizCodeEnume;
import com.imnoob.shopmallcommon.utils.R;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@ControllerAdvice
public class Excenhandler {

    @ExceptionHandler(value = BindException.class)
    public R bindExceptionHandler(BindException e){
        /*注意：此处的BindException 是 Spring 框架抛出的Validation异常*/

        List<ObjectError> errors = e.getAllErrors();/*抛出异常可能不止一个 输出为一个List集合*/
        ObjectError error = errors.get(0);/*取第一个异常*/
        String errorMsg = error.getDefaultMessage(); /*获取异常信息*/
        return R.error(BizCodeEnume.VAILD_EXCEPTION.getCode(),errorMsg);
    }
}
