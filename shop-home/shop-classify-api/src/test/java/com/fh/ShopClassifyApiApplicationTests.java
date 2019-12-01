package com.fh;


import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.misc.BASE64Encoder;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopClassifyApiApplicationTests {

    @Test
    public void testJwt() {
        //设置头部信息
        Map<String,Object> headerMap=new HashMap<String,Object>();
        headerMap.put("alg","HS256");
        headerMap.put("typ","JWT");
        //设置负载信息
        Map<String,Object> payloadMap=new HashMap<String,Object>();
        payloadMap.put("userName","zhangsan");
        payloadMap.put("phone","13849957679");
        //设置过期时间
        long millis = System.currentTimeMillis();
        payloadMap.put("exp",millis+600000l);
        payloadMap.put("lat",millis);
        String token = Jwts.builder()
                .setHeader(headerMap)
                .setPayload(JSON.toJSONString(payloadMap))
                .signWith(SignatureAlgorithm.HS256, getSecretKey("zhangsan"))
                .compact();
        System.out.println(token);

    }
    @Test
    public void resolve(){
       String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NzQ3NzIwMjk5NzYsImxhdCI6MTU3NDc3MTQyOTk3NiwicGhvbmUiOiIxMzg0OTk1NzY3OSIsInVzZXJOYW1lIjoiemhhbmdzYW4ifQ.qoI0ciSrgT5FYbJ3KW3NF_YKmUau1N5VmZWkJZOvmbg";

       try {
            Claims zhangsan = Jwts.parser().setSigningKey(getSecretKey("zhangsan")).parseClaimsJws(token).getBody();
           System.out.println("11111111111111111"+zhangsan.get("userName"));
       } catch (ExpiredJwtException e) {
            e.printStackTrace();
            //超时
            System.out.println("超时了");
        } catch (SignatureException e) {
            e.printStackTrace();
            //解析失败
            System.out.println("token解析失败");
        }
    }
    private  static String getSecretKey(String key){
        return new BASE64Encoder().encode(key.getBytes());
    }
}
