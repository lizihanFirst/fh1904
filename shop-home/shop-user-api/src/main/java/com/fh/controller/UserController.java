package com.fh.controller;

import com.fh.commons.ResultEnum;
import com.fh.commons.ServerResult;
import com.fh.bean.UserBean;
import com.fh.commons.SystemConst;
import com.fh.service.IUserService;
import com.fh.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("user")
@CrossOrigin(maxAge = 3600,origins = "http://localhost:8080")
public class UserController {
    @Resource(name = "userService")
    private IUserService userService;
    @Autowired
    private RedisTemplate redisTemplate;
    //发送验证码
    @PostMapping()
    public ServerResult getValidate(String phone){
       /* try {
            boolean b = SMSUtils.sendCode(phone, "14832372");
            return ServerResult.success();
        } catch (IOException e) {
            e.printStackTrace();
            return ServerResult.error(500,"发送失败");
        }*/
            String code="123456";
        redisTemplate.opsForValue().set("code_"+phone,"123456",300,TimeUnit.SECONDS);
        return ServerResult.success();
    }
    //登录
    @PostMapping("/login")
    public ServerResult login(String phone,String yaZhengId){
        if(phone==null || yaZhengId==null){
            return ServerResult.error(ResultEnum.LOGIN_PHONEANDCODE_NULL);
        }
            //判断redis中是否有验证码
        String code = (String) redisTemplate.opsForValue().get("code_"+phone);
        //判断验证码是否正确
        if(!yaZhengId.equals(code)){
            return ServerResult.error(ResultEnum.REDIS_CODE_NULL);
        }
        UserBean userBean=userService.isRegister(phone);
        redisTemplate.opsForValue().set("cartId_"+phone,userBean.getCartId());
        redisTemplate.opsForValue().set("user_"+phone,userBean);
        redisTemplate.delete("code_" + phone);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put(SystemConst.USER_PHONE,phone);
        String token = JwtUtil.encode(map);
        return ServerResult.success(token);
       /* try {
            boolean b = SMSUtils.verifyCode(phone, yaZhengId);
            UserBean userBean=userService.getUserByPhone(phone);
            //如果没有此用户就注册
            if(userBean==null){
                userService.insertUser(phone);
            }
            Map<String,Object> map=new HashMap<String,Object>();
            map.put(SystemConst.USER_PHONE,phone);
            String token = JwtUtil.encode(map);
            return ServerResult.success(token);
        } catch (IOException e) {
            e.printStackTrace();
            return ServerResult.error(500,"验证码错误");
        }*/

    }
}
