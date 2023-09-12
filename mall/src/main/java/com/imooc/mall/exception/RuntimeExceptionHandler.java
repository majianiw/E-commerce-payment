package com.imooc.mall.exception;

import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.vo.ResponseVo;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

/**
 * @Date: create in 11:51 2023/2/15
 * @describe:
 */
@ControllerAdvice
public class RuntimeExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
//    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseVo handler(RuntimeException e) {
        return ResponseVo.error(ResponseEnum.ERROR, e.getMessage());
    }

    @ExceptionHandler(UserLoginException.class)
    @ResponseBody
//    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseVo userLoginHandle() {
        return ResponseVo.error(ResponseEnum.NEED_LOGIN);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
//    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseVo notValidExceptionHandle(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        Objects.requireNonNull(bindingResult.getFieldError()).getField();
        return ResponseVo.error(ResponseEnum.PARAM_ERROR,
                    bindingResult.getFieldError().getField()
                        + " "
                        + bindingResult.getFieldError().getDefaultMessage());
    }
    
}
