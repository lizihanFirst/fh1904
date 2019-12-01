package com.fh.exception;


import com.fh.commons.ResultEnum;

public class AuthenticateException extends RuntimeException {
    private Integer code;
    public AuthenticateException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code=resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }
}
