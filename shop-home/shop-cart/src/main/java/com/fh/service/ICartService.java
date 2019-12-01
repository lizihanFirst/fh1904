package com.fh.service;

import com.fh.commons.ServerResult;

import java.util.List;
import java.util.Map;

public interface ICartService {
    //新增购物车
    ServerResult addCart(Integer productId,String phone);
    //获取购物车中的数量
    ServerResult getCartCount(String phone);

    /**
     * 获取购物车的所有数据
     * @param phone
     * @return
     */
    Map<String,Object> findCartAll(String phone);

    /**
     * 改变复选框状态
     * @param phone
     * @param productId
     */
    void checkStrtus(String phone, Integer productId);

    /**
     * 改变商品的数量
     * @param phone
     * @param sum
     * @param productId
     */
    void changeProductSum(String phone, Integer sum, Integer productId);
    /**
     * 是否全选
     * @param productIds
     * @param
     * @return
     */
    void isCheckAll(String phone, String productIds);

    /**
     * 删除购物车中的商品
     * @param productId
     * @param phone
     */
    void deleteCart(Integer productId, String phone);
}
