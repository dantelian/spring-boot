package com.example.springbootmy.common.core.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileNotFoundException;

/**
 * @program: spring-boot-my
 * @description: 全局异常处理
 * 如果接口代码中没有try catch 异常的话,Exception类型的异常信息则会被ExceptionHndler方法处理
 * @author: ddd
 * @create: 2023-02-10 09:32
 **/
@Slf4j
@ControllerAdvice
public class GlobalExceptionControllerAdvice {
    private static final String ERROR_MESSAGE = "系统内部错误,请联系管理员！";

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String ExceptionHndler(Exception e) {
        if (e instanceof FileNotFoundException) {
            //可以自定义根据不同的异常类型,做一些代码处理
        }
        log.error("global error:{}", e);
        return ERROR_MESSAGE;
    }


}
