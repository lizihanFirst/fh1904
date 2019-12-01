package com.fh.login;

import com.alibaba.fastjson.JSONObject;
import com.fh.commons.ResultEnum;
import com.fh.commons.ServerResult;
import com.fh.commons.SystemConst;
import com.fh.exception.AuthenticateException;
import com.fh.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Order(5)
public class LoginAuthenticate {
    @Around(value = "execution(* com.fh.controller.*.*(..))&&@annotation(LoginAnnotation)")
    public Object loginAuthenticate(ProceedingJoinPoint joinPoint){
        HttpServletRequest request=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");
        System.out.println(token);
        if(token==null|| token.equals("null")){
                throw new AuthenticateException(ResultEnum.TOKEN_ISNULL);
        }
        ServerResult serverResult = JwtUtil.decode(token);
        if(serverResult.getCode()!=200){
            throw new AuthenticateException(ResultEnum.TOKEN_ISNULL);
        }

        Claims data = (Claims) serverResult.getData();
            String phone= (String) data.get(SystemConst.USER_PHONE);
            request.setAttribute("phone",phone);
        Object obj=null;
        try {
            obj=joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return obj;
    }
}
