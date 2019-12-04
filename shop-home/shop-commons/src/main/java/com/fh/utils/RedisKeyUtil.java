package com.fh.utils;

public class RedisKeyUtil {
    public static final String getCartKey(String phone){
        return "cartId_"+phone;
    }
    public static final String getUserKey(String phone){
        return "user_"+phone;
    }
    public static final String getWaitPayKey(String phone){
        return "pay_"+phone;
    }

}
