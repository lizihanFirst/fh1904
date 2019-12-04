package com.fh.service;

import com.fh.bean.PayLogBean;
import com.fh.commons.ServerResult;

import java.util.Map;

public interface IOrderService {
    /**
     * 生成订单
     * @param addressId
     * @param phone
     * @return
     */
    ServerResult submitOrder(Integer addressId, String phone);


}
