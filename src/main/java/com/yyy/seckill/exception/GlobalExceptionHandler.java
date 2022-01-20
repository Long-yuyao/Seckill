package com.yyy.seckill.exception;
import com.yyy.seckill.result.CodeMsg;
import com.yyy.seckill.result.Result;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindException;

import java.util.List;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public Result<CodeMsg> ExceptionHandler(HttpServletRequest httpServletRequest, Exception exception){
        exception.printStackTrace();
        if(exception instanceof GlobalException){
            GlobalException ex = (GlobalException) exception;
            return Result.Fail(ex.getCm());
        }else if(exception instanceof BindException){
            BindException ex = (BindException)exception;
            List<ObjectError> errors= ex.getAllErrors();
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();
            return Result.Fail(CodeMsg.BIND_ERROR.fillArgs(msg));
        }else {
            return Result.Fail(CodeMsg.SERVER_ERROR);
        }
    }
}
