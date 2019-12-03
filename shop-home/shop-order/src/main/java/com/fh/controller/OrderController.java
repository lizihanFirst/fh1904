package com.fh.controller;

import com.fh.commons.ServerResult;
import com.fh.login.LoginAnnotation;
import com.fh.service.IOrderService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("order")
@CrossOrigin(maxAge = 3600,origins = "http://localhost:8080",exposedHeaders = "NORIGHT")
public class OrderController {
    @Resource
    private IOrderService orderService;

}
