package com.fh.controller;

import com.fh.commons.ServerResult;
import com.fh.login.LoginAnnotation;
import com.fh.service.ICartService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("cartShow")
@CrossOrigin(maxAge = 3600,origins = "http://localhost:8080",exposedHeaders = "NORIGHT")
public class CartShowController {
    @Resource(name = "cartService")
    private ICartService cartService;


    /**
     * 查询我的购物车的所有商品信息
     * @return
     */
    @GetMapping
    @LoginAnnotation
  public ServerResult findCartAll(HttpServletRequest request){
        String phone = (String) request.getAttribute("phone");
        Map<String,Object> cartMap=cartService.findCartAll(phone);
        return ServerResult.success(cartMap);
  }
    /**
     * 改变复选框状态
     * @param
     * @param productId
     */
  @PostMapping
  @LoginAnnotation
    public ServerResult checkStrtus(Integer productId,HttpServletRequest request){
      String phone = (String) request.getAttribute("phone");
      cartService.checkStrtus(phone,productId);
      return ServerResult.success();
    }
    /**
     * 改变商品的数量
     * @param
     * @param sum
     * @param productId
     */
    @PostMapping("/changeProductSum")
    @LoginAnnotation
    public ServerResult changeProductSum(Integer sum,Integer productId,HttpServletRequest request){
        String phone = (String) request.getAttribute("phone");
        cartService.changeProductSum(phone,sum,productId);
        return ServerResult.success();
    }

    /**
     * 是否全选
     * @param productIds
     * @param request
     * @return
     */
    @PostMapping("/isCheckAll")
    @LoginAnnotation
    public ServerResult isCheckAll(String productIds,HttpServletRequest request){
        String phone = (String) request.getAttribute("phone");
        cartService.isCheckAll(phone,productIds);
        return ServerResult.success();
    }

    /**
     * 删除购物车中的商品
     * @param
     * @param request
     * @return
     */
    @PostMapping("/deleteCart")
    @LoginAnnotation
    public ServerResult deleteCart(Integer productId,HttpServletRequest request){
        String phone = (String) request.getAttribute("phone");
        cartService.deleteCart(productId,phone);
        return ServerResult.success();
    }
    /**
     * 查询被选中的商品信息
     */
    @GetMapping("/findProductList")
    @LoginAnnotation
    public ServerResult findProductList(HttpServletRequest request){
        String phone = (String) request.getAttribute("phone");
        Map<String,Object> cartMap=cartService.findProductList(phone);
        return ServerResult.success(cartMap);
    }
}
