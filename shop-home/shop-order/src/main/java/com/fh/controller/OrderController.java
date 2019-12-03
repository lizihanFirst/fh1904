package com.fh.controller;

import com.fh.bean.PayLogBean;
import com.fh.commons.ServerResult;
import com.fh.login.LoginAnnotation;
import com.fh.service.IOrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("order")
@CrossOrigin(maxAge = 3600,origins = "http://localhost:8080",exposedHeaders = "NORIGHT")
public class OrderController {
    @Resource
    private IOrderService orderService;
    @PutMapping
    @LoginAnnotation
    public ServerResult submitOrder(Integer addressId,HttpServletRequest request){
        String phone= (String) request.getAttribute("phone");
        return orderService.submitOrder(addressId,phone);
    }
    @PostMapping
    @LoginAnnotation
    public ServerResult getPayOrder(String outTradeNoId,HttpServletRequest request){
        String phone= (String) request.getAttribute("phone");
        PayLogBean payLogBean=orderService.getPayOrder(outTradeNoId,phone);
        return ServerResult.success(payLogBean);
    }
}
