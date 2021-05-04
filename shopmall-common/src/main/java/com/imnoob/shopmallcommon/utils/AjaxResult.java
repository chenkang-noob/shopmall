package com.imnoob.shopmallcommon.utils;

import lombok.Data;
import org.apache.http.HttpStatus;

import java.io.Serializable;

//基于 openfeign调用结果自动封装为LingkedHashmap的情况，必须指定调用结果的类型。因此用这个类来替换 R 类返回
@Data
public class AjaxResult<T>  implements Serializable {

    private static final long serialVersionUID = 1L;

    private String msg;
    private Integer code;
    private T data;

    public static  <A>  AjaxResult<A> ok(A obj){
        AjaxResult<A> res = new AjaxResult<>();
        res.setCode(HttpStatus.SC_OK);
        res.setMsg("成功");
        res.setData(obj);
        return res;
    }

    public static  AjaxResult ok(){
        AjaxResult<Object> res = new AjaxResult<>();
        res.setCode(HttpStatus.SC_OK);
        res.setMsg("成功");
        return res;
    }


    public static AjaxResult error() {
        AjaxResult<Object> res = new AjaxResult<>();
        res.setCode(HttpStatus.SC_EXPECTATION_FAILED);
        res.setMsg("失败");
        return res;
    }

    public static  <A>  AjaxResult<A> error(A obj){
        AjaxResult<A> res = new AjaxResult<>();
        res.setCode(HttpStatus.SC_EXPECTATION_FAILED);
        res.setMsg("失败");
        res.setData(obj);
        return res;
    }
}
