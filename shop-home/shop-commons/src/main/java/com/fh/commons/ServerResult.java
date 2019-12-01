package com.fh.commons;

import java.io.Serializable;

public class ServerResult implements Serializable {
    private int code;
    private String msg;
    private Object data;
    private ServerResult(){

    }
    private ServerResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public static ServerResult success(){
        return new ServerResult(200,"ok",null);
    }
    public static ServerResult success(Object object){
        return new ServerResult(200,"ok",object);
    }

    public static ServerResult error(){
        return new ServerResult(-1,"操作失败",null);
    }
    public static ServerResult error(int code, String msg){
        return new ServerResult(code,msg,null);
    }
    public static ServerResult error(ResultEnum resultEnum){
        return new ServerResult(resultEnum.getCode(),resultEnum.getMsg(),null);
    }
    public int getCode() {

        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
