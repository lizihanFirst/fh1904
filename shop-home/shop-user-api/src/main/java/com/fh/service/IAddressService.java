package com.fh.service;

import com.fh.bean.AddressBean;

import java.util.List;

public interface IAddressService {
    /**
     * 查询用户的收货地址
     * @param phone
     * @return
     */
    List<AddressBean> findAddressByUserId(String phone);

    /**
     * 新增收货地址
     * @param addressBean
     */
    void saveAddress(AddressBean addressBean,String phone);

    /**
     * 根据id查询地址
     * @param id
     * @return
     */
    AddressBean getAddressById(Integer id);

    /**
     * 删除
     * @param id
     */
    void deleteAddress(Integer id);
}
