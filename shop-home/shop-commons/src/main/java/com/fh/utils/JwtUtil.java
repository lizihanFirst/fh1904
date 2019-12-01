package com.fh.utils;

import com.alibaba.fastjson.JSON;
import com.fh.commons.ResultEnum;
import com.fh.commons.ServerResult;
import com.fh.commons.SystemConst;
import io.jsonwebtoken.*;
import sun.misc.BASE64Encoder;

import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    /**
     *加密 jwt token
     * @param map
     * @return
     */
    public static String encode(Map<String,Object> map){
        //设置头部信息
        Map<String,Object> headerMap=new HashMap<String,Object>();
        headerMap.put("alg","HS256");
        headerMap.put("typ","JWT");
        //设置负载信息
        Map<String,Object> payloadMap=new HashMap<String,Object>();
       payloadMap.putAll(map);
        //设置过期时间
        long millis = System.currentTimeMillis();
        payloadMap.put("exp",millis+1800000l);
        payloadMap.put("lat",millis);
        String token = Jwts.builder()
                .setHeader(headerMap)
                .setPayload(JSON.toJSONString(payloadMap))
                .signWith(SignatureAlgorithm.HS256, getSecretKey("phone"))
                .compact();
        return token;
    }

    /**
     *解密 jwt toke
     * @param token
     * @param key
     * @return
     */
    public static ServerResult decode(String token){
        Claims claims=null;
        try {
             claims = Jwts.parser().setSigningKey(getSecretKey("phone")).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            //超时
            System.out.println("超时了");
            return ServerResult.error(ResultEnum.TOKEN_OVERTIME);
        } catch (SignatureException e) {
            e.printStackTrace();
            //解析失败
            System.out.println("token解析失败");
            return ServerResult.error(ResultEnum.TOKEN_RESOLVE_NULL);
        }
        return ServerResult.success(claims);
    }
    private  static String getSecretKey(String key){
        return new BASE64Encoder().encode(key.getBytes());
    }

}
