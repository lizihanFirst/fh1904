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
public class ShopUserApiApplicationTests {

    @Test
    public void testJwt() {
        //设置头部
        Map<String,Object> headerMap=new HashMap<>();
                headerMap.put("alg","HS256");
                headerMap.put("typ","JWT");
                //设置负载
        Map<String,Object> payloadMap=new HashMap<>();
        payloadMap.put("userName","wangwu");
        payloadMap.put("phone","123456789");
        //设置失效时间
        long l = System.currentTimeMillis();
        payloadMap.put("exp",l+60000l);
        payloadMap.put("iat",l);
        String token = Jwts.builder()
                .setHeader(headerMap)
                .setPayload(JSON.toJSONString(payloadMap))
                .signWith(SignatureAlgorithm.HS256, getSecretKey("wangwu")).compact();
        System.out.println(token);
    }
    @Test
    public void resolve(){
        String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NzQ3NzM3MTY2MjcsImlhdCI6MTU3NDc3MzY1NjYyNywicGhvbmUiOiIxMjM0NTY3ODkiLCJ1c2VyTmFtZSI6Indhbmd3dSJ9.WP0OR0lY0LfpujIYPqCewUAX1z-GaXyoXqbqAxiUrZs";
        try {
            Claims wangwu = Jwts.parser()
                    .setSigningKey(getSecretKey("wangwu"))
                    .parseClaimsJws(token)
                    .getBody();
            System.out.println(wangwu.get("userName"));
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            System.out.println("超时");
        } catch (SignatureException e) {
            e.printStackTrace();
            System.out.println("失败了");
        }
    }
    private  static String getSecretKey(String key){
        return new BASE64Encoder().encode(key.getBytes());
    }

}
