package com.fh.controller;

import com.fh.commons.ServerResult;
import com.fh.login.LoginAnnotation;
import com.fh.service.IPayService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RequestMapping("pays")
@RestController
@CrossOrigin(maxAge = 3600,origins = "http://localhost:8080",exposedHeaders = "NORIGHT")
public class PayController {
    @Resource(name = "payService")
    private IPayService payService;

    /**
     * 生成支付二维码
     * @param outTradeNo
     * @param request
     * @return
     */
    @GetMapping("/{outTradeNo}")
    @LoginAnnotation
    public ServerResult createPayCode(@PathVariable  String outTradeNo, HttpServletRequest request){
        String phone = (String) request.getAttribute("phone");
        return payService.createPayCode(outTradeNo,phone);
    }

    /**
     * 查询支付订单是否被支付
     * @param outTradeNo
     * @param request
     * @return
     */
    @PutMapping
    @LoginAnnotation
    public ServerResult checkPayStatus( String outTradeNo,HttpServletRequest request){
        String phone = (String) request.getAttribute("phone");
        return payService.checkPayStatus(outTradeNo,phone);
    }
}
