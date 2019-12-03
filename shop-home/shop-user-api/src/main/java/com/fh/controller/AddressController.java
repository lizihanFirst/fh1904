package com.fh.controller;

import com.fh.bean.AddressBean;
import com.fh.commons.ServerResult;
import com.fh.login.LoginAnnotation;
import com.fh.service.IAddressService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("address")
@RestController
@CrossOrigin(maxAge = 3600,origins = "http://localhost:8080",exposedHeaders = "NORIGHT")
public class AddressController {
    @Resource(name = "addressService")
    private IAddressService addressService;

    /**
     * 查询用户对应的地址
     * @param request
     * @return
     */
    @LoginAnnotation
    @GetMapping
    public ServerResult findAddressByUserId(HttpServletRequest request){
        String phone = (String) request.getAttribute("phone");
        List<AddressBean> addressBeanList=addressService.findAddressByUserId(phone);
        return ServerResult.success(addressBeanList);
}

    /**
     * 新增地址
     * @param addressBean
     * @param request
     * @return
     */
    @PutMapping
    @LoginAnnotation
    public ServerResult insertAddress(AddressBean addressBean,HttpServletRequest request){
        String phone = (String) request.getAttribute("phone");
        addressService.saveAddress(addressBean,phone);
        return ServerResult.success();
    }

    /**
     * 根据id查询地址
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @LoginAnnotation
    public ServerResult getAddressById(@PathVariable Integer id){
     AddressBean addressBean=addressService.getAddressById(id);
        return ServerResult.success(addressBean);
    }

    /**
     * 删除地址
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @LoginAnnotation
    public ServerResult deleteAddress(@PathVariable  Integer id){
        addressService.deleteAddress(id);
        return ServerResult.success();
    }

}
