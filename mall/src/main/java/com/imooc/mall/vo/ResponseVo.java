package com.imooc.mall.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.imooc.mall.enums.ResponseEnum;
import org.springframework.validation.BindingResult;

/**
 * @Date: create in 14:17 2023/2/14
 * @describe: 返回格式
 */

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ResponseVo<T> {
    private Integer status;

    private String msg;

    private T data;

    public ResponseVo(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }
    public ResponseVo(Integer status, T data) {
        this.status = status;
        this.data = data;
    }
    public static <T> ResponseVo<T> success() {
        return new ResponseVo<>(ResponseEnum.SUCCESS.getCode(),ResponseEnum.SUCCESS.getDesc());
    }
    public static <T> ResponseVo<T> success(T data) {
        return new ResponseVo<>(ResponseEnum.SUCCESS.getCode(),data);
    }
    public static <T> ResponseVo<T> successByMsg(String msg) {
        return new ResponseVo<>(ResponseEnum.SUCCESS.getCode(),msg);
    }

    public static <T> ResponseVo<T> error(ResponseEnum responseEnum) {
        return new ResponseVo<>(responseEnum.getCode(), responseEnum.getDesc());
    }

    public static <T> ResponseVo<T> error(ResponseEnum responseEnum,String msg) {
        return new ResponseVo<>(responseEnum.getCode(),msg);
    }

    public static <T> ResponseVo<T> error(ResponseEnum responseEnum, BindingResult bindingResult) {
        return new ResponseVo<>(responseEnum.getCode(),
                bindingResult.getFieldError().getField() + " " +bindingResult.getFieldError().getDefaultMessage());
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
