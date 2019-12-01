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
public class ShopBrandApiApplicationTests {
    /**
     * 加密
     */
    @Test
    public void demoJwt() {
        //设置头部
        Map<String,Object> headerMap=new HashMap<String,Object>();
        headerMap.put("alg","HS256");
        headerMap.put("typ","JWT");
        //设置负载
        Map<String,Object> payloadMap=new HashMap<String,Object>();
        //设置过期时间
        long time = System.currentTimeMillis();
        payloadMap.put("exp",time+50000l);
        payloadMap.put("iat",time);
        payloadMap.put("userName","lisi");
        payloadMap.put("phone","13849957679");
        String token = Jwts.builder()
                .setHeader(headerMap)
                .setPayload(JSON.toJSONString(payloadMap))
                .signWith(SignatureAlgorithm.HS256, getSecretKey("lisi")).compact();
        System.out.println(token);
    }
    @Test
    public void resolve(){
        String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NzQ3NzMwMDkyMjksImlhdCI6MTU3NDc3Mjk1OTIyOSwicGhvbmUiOiIxMzg0OTk1NzY3OSIsInVzZXJOYW1lIjoibGlzaSJ9.rXQAONuhcPWpGLAFkdjb99w6_qJXer_bF7VVvWrkXrc";
        try {
            Claims lisi = Jwts.parser().setSigningKey(getSecretKey("lisi")).parseClaimsJws(token).getBody();
            System.out.println(lisi.get("phone"));
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            System.out.println("超时了");
        }  catch (SignatureException e) {
            e.printStackTrace();
            System.out.println("失败了");
        }
    }
    private  static String getSecretKey(String key){
        return new BASE64Encoder().encode(key.getBytes());
    }

}
