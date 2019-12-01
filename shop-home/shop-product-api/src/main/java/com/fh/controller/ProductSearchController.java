package com.fh.controller;

import com.fh.commons.ServerResult;
import com.fh.service.IProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("productSearch")
@RestController
public class ProductSearchController {
    @Resource(name = "productService")
    private IProductService productService;
    //根据id查询商品
    @GetMapping("/{productId}")
    public ServerResult getProductById(@PathVariable Integer productId){
        return productService.getProductById(productId);
    }
}
