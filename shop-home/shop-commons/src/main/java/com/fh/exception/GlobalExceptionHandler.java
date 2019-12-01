package com.fh.exception;



import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AuthenticateException.class)
    public void authenticateException(AuthenticateException ex, HttpServletRequest request, HttpServletResponse response){
        response.setHeader("NORIGHT",ex.getCode().toString());
}
    @ExceptionHandler(Exception.class)
    public void handlerException(Exception ex,HttpServletResponse response){
        response.setHeader("NORIGHT",ex.getMessage());
    }
}
