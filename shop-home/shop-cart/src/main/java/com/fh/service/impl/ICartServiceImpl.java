package com.fh.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fh.bean.CartBean;
import com.fh.commons.ServerResult;
import com.fh.service.ICartService;
import com.fh.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service("cartService")
public class ICartServiceImpl implements ICartService {
    @Autowired
    private RedisTemplate redisTemplate;
    //新增购物车
    @Override
    public ServerResult addCart(Integer productId,String phone) {
        ///获取购物车id
        String cartId = (String) redisTemplate.opsForValue().get("cartId_" + phone);
        //获取商品信息
        String url="http://localhost:8092/productSearch/"+productId;
        String result = HttpClientUtil.doGet(url);
        JSONObject jsonObject = JSON.parseObject(result);
        JSONObject data = JSON.parseObject(jsonObject.get("data").toString());
        //将数据存入redis中
        CartBean cartBean=new CartBean();
        cartBean.setProductId(productId);
        cartBean.setProductName(data.getString("productName"));
        cartBean.setMainImg(data.getString("mainImg"));
        cartBean.setPrice(data.getBigDecimal("price"));
        cartBean.setDetail(data.getString("detail"));
        cartBean.setSubtitle(data.getString("subtitle"));
        if(redisTemplate.opsForHash().hasKey(cartId,productId)){
        CartBean cart= (CartBean) redisTemplate.opsForHash().get(cartId,productId);
            cartBean.setCount(cart.getCount()+1);
        }else {
            cartBean.setCount(1);
        }
        //计算小计金额
        BigDecimal bigDecimal=BigDecimal.valueOf(0.00);
        BigDecimal count=new BigDecimal(cartBean.getCount());
        BigDecimal subtotal=bigDecimal.add(cartBean.getPrice()).multiply(count);
        cartBean.setSubtotal(subtotal);
        cartBean.setIsChecked(true);
        //查询商品是否有货
        String stock = data.getString("stock");
        if(Integer.valueOf(stock)>cartBean.getCount()){
            cartBean.setIsStock(true);
        }else {
            cartBean.setIsStock(false);
        }
        redisTemplate.opsForHash().put(cartId,productId,cartBean);
        Long size = redisTemplate.opsForHash().size(cartId);
        return ServerResult.success(size);
    }

    //获取购物车中的数量
    @Override
    public ServerResult getCartCount(String phone) {
        ///获取购物车id
        String cartId = (String) redisTemplate.opsForValue().get("cartId_" + phone);
        Long size = redisTemplate.opsForHash().size(cartId);
        return ServerResult.success(size);
    }
    /**
     * 获取购物车的所有数据
     * @param phone
     * @return
     */
    @Override
    public Map<String, Object> findCartAll(String phone) {
        ///获取购物车id
        String cartId = (String) redisTemplate.opsForValue().get("cartId_" + phone);
        List<CartBean> cartBeanList = redisTemplate.opsForHash().values(cartId);
        //计算总金额
        BigDecimal bigDecimal=BigDecimal.valueOf(0.00);
        for (CartBean cartBean : cartBeanList) {
            if(cartBean.getIsChecked()){
               bigDecimal= bigDecimal.add(cartBean.getSubtotal());
            }
        }
        Map<String,Object> cartMap=new HashMap<>();
        cartMap.put("cartList",cartBeanList);
        cartMap.put("total",bigDecimal);
        return cartMap;
    }
    /**
     * 改变复选框状态
     * @param phone
     * @param productId
     */
    @Override
    public void checkStrtus(String phone, Integer productId) {
        ///获取购物车id
        String cartId = (String) redisTemplate.opsForValue().get("cartId_" + phone);
        CartBean cartBean = (CartBean) redisTemplate.opsForHash().get(cartId, productId);
        cartBean.setIsChecked(!cartBean.getIsChecked());
        redisTemplate.opsForHash().put(cartId,productId,cartBean);
    }
    /**
     * 改变商品的数量
     * @param phone
     * @param sum
     * @param productId
     */
    @Override
    public void changeProductSum(String phone, Integer sum, Integer productId) {
        ///获取购物车id
        String cartId = (String) redisTemplate.opsForValue().get("cartId_" + phone);
        CartBean cartBean = (CartBean)redisTemplate.opsForHash().get(cartId, productId);
        cartBean.setCount(sum);
        BigDecimal bigDecimal=BigDecimal.valueOf(0.00);
        BigDecimal count=new BigDecimal(cartBean.getCount());
        BigDecimal subtotal=bigDecimal.add(cartBean.getPrice()).multiply(count);
        cartBean.setSubtotal(subtotal);
        //查询商品是否有货
        //获取商品信息
        String url="http://localhost:8092/productSearch/"+productId;
        String result = HttpClientUtil.doGet(url);
        JSONObject jsonObject = JSON.parseObject(result);
        JSONObject data = JSON.parseObject(jsonObject.get("data").toString());
        String stock = data.getString("stock");
        if(Integer.valueOf(stock)>cartBean.getCount()){
            cartBean.setIsStock(true);
        }else {
            cartBean.setIsStock(false);
        }
        redisTemplate.opsForHash().put(cartId,productId,cartBean);
    }

    /**
     * 是否全选
     * @param phone
     * @param productIds
     */
    @Override
    public void isCheckAll(String phone,String productIds) {
        String cartId = (String) redisTemplate.opsForValue().get("cartId_" + phone);
        List<String> list = Arrays.asList(productIds.split(","));
        List<CartBean> cartBeanList = redisTemplate.opsForHash().values(cartId);
        for (CartBean cartBean : cartBeanList) {
            if(list.contains(String.valueOf(cartBean.getProductId()))){
                cartBean.setIsChecked(!cartBean.getIsChecked());
                redisTemplate.opsForHash().put(cartId,cartBean.getProductId(),cartBean);
            }
        }
    }
    /**
     * 删除购物车中的商品
     * @param productId
     * @param phone
     */
    @Override
    public void deleteCart(Integer productId, String phone) {
        String cartId = (String) redisTemplate.opsForValue().get("cartId_" + phone);
        redisTemplate.opsForHash().delete(cartId,productId);
    }

    /**
     * 查询所有被选中的商品
     * @param phone
     * @return
     */
    @Override
    public Map<String, Object> findProductList(String phone) {
        ///获取购物车id
        String cartId = (String) redisTemplate.opsForValue().get("cartId_" + phone);
        List<CartBean> cartBeanList = redisTemplate.opsForHash().values(cartId);
        List<CartBean> cartBeans=new ArrayList<>();
        //计算总金额
        BigDecimal bigDecimal=BigDecimal.valueOf(0.00);
        for (CartBean cartBean : cartBeanList) {
            if(cartBean.getIsChecked()){
                bigDecimal= bigDecimal.add(cartBean.getSubtotal());
                cartBeans.add(cartBean);
            }
        }
        Map<String,Object> cartMap=new HashMap<>();
        cartMap.put("cartList",cartBeans);
        cartMap.put("total",bigDecimal);
        return cartMap;
    }
}
