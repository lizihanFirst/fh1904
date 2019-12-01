package com.fh.controller;

import com.fh.commons.ServerResult;
import com.fh.login.LoginAnnotation;
import com.fh.service.ICartService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("cart")
@CrossOrigin(maxAge = 3600,origins = "http://localhost:8080",exposedHeaders = "NORIGHT")
public class CartController {
    @Resource(name = "cartService")
    private ICartService cartService;
    //添加购物车
    @PostMapping
    @LoginAnnotation
    public ServerResult addCart(Integer productId, HttpServletRequest request){
        String phone = (String) request.getAttribute("phone");
        System.out.println(phone);
        return cartService.addCart(productId,phone);
    }
    //获取购物车的数量
    @GetMapping
    @LoginAnnotation
    public ServerResult getCartCount(HttpServletRequest request){
        //获取手机号
        String phone = (String) request.getAttribute("phone");
       return cartService.getCartCount(phone);
    }
    /**
     * 跳转到我的购物车页面
     * @return
     */
    @GetMapping("/toCartHtml")
    @LoginAnnotation
    public ServerResult toCartHtml(){
        return ServerResult.success();
    }
}
