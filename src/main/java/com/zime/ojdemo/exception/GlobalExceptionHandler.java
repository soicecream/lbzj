package com.zime.ojdemo.exception;

import com.zime.ojdemo.modle.vo.base.JsonResult;
import com.zime.ojdemo.modle.vo.base.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.text.ParseException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //负责集中处理错误,此处处理了MethodArgumentTypeMismatchException错误
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public JsonResult handleMethodArgumentNotValidException(MethodArgumentTypeMismatchException e) {
        log.error("参数转换失败:" + e.getMessage(), e);
        return JsonResult.error(ResultCode.REQUEST_NOT_SUPPORT.code,e.getMessage());
    }


    @ExceptionHandler(ParseException.class)
    public JsonResult handleIncorrectCredentialsException(ParseException e){
        log.error("格式错误");
        return JsonResult.error(ResultCode.REQUEST_NOT_SUPPORT.code,e.getMessage());
    }
}
