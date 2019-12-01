package com.fh.controller;

import com.fh.commons.DataTableResult;
import com.fh.commons.LayuiTableResult;
import com.fh.param.ProductParamer;
import com.fh.param.ProductSearchParam;
import com.fh.service.IProductService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
@RestController
@RequestMapping("product")
@CrossOrigin(maxAge = 3600,origins = "http://localhost:8080")
public class ProductController {
    @Resource(name = "productService")
    private IProductService productService;
  //根据类型查询商品
    @PostMapping("/getProductByTypeId")
    public LayuiTableResult getProductByTypeId(ProductSearchParam productSearchParam, HttpServletRequest request){
        String token = request.getHeader("token");
        System.out.println(token);
        return productService.getProductByTypeId(productSearchParam);
    }
    //datTable分页查询
    @PostMapping("/findProductList")
    public DataTableResult findProductList(ProductParamer productParamer){

        return productService.findProductList(productParamer);
    }
}
