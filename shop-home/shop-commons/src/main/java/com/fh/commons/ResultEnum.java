package com.fh.commons;

public enum ResultEnum {
    USERNAME_IS_ERROR(1001,"用户名错误！！"),
    PASSWORD_IS_ERROR(1002,"密码错误！！"),
    USERNAME_PASSWORD_IS_NULL(1000,"用户名或密码为空！！"),
    LOGINERROR_IS_ERROR(1003,"密码错误已3次，账号已锁定，解锁时间：00:00"),
    USER_UPDATEPAW_ERROE_NULL(1004,"原密码、新密码、确认密码怒能为空！！"),
    USER_UPDATEPAW_ERROE_PASSWORD(1005,"新密码和确认密码不一致！！"),
    USER_UPDATEPAW_ERROR(1006,"老密码错误！！"),
    LOGIN_PHONE_NULL(1010,"手机号不存在！！"),
    USER_IS_NULL(1008,"用户不存在"),
    LEAVE_DAYS_NULL(1009,"请假天数错误"),
    RIGHT_NO_ACCESS(1007,"没有权限访问"),

    SERVER_CONNECT_ERROR(2001,"服务器连接超时"),
    SERVER_BUSYNESS(2002,"服务器繁忙"),
    SERVER_ERROR(2003,"服务器炸了，请稍等片刻~~"),
    LOGIN_PHONEANDCODE_NULL(2004,"手机或验证码为空"),
    REDIS_CODE_NULL(2005,"验证码已失效，请重新获取验证码"),
    TOKEN_ISNULL(2006,"token已失效，请重新登录"),
    TOKEN_OVERTIME(2007,"解析超时了"),
    TOKEN_RESOLVE_NULL(2007,"解析失败了"),
    PRODUCT_UNDERSTOCK_ALL_ERROR(2008,"商品数量都不够"),
    PAY_ORDER_NULL(3001,"支付订单不存在"),
    CRATER_PAY_ERROR(3002,"二维码生成失败"),
    PAY_TIMEOUT(3003,"二维码已过期，请刷新页面重新获取二维码。"),

    ;
    private int code;
    private String msg;
    private ResultEnum(int code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
