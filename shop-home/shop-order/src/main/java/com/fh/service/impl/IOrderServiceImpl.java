package com.fh.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fh.bean.*;
import com.fh.commons.ResultEnum;
import com.fh.commons.ServerResult;
import com.fh.commons.SystemConst;
import com.fh.mapper.IOrderDetailMapper;
import com.fh.mapper.IOrderMapper;
import com.fh.mapper.IPayLogMapper;
import com.fh.service.IOrderService;
import com.fh.util.IdUtil;
import com.fh.utils.HttpClientUtil;
import com.fh.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service("orderService")
public class IOrderServiceImpl implements IOrderService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IOrderMapper orderMapper;
    @Autowired
    private IOrderDetailMapper orderDetailMapper;
    @Autowired
    private IPayLogMapper payLogMapper;
    /**
     * 生成订单
     * @param addressId
     * @param phone
     * @return
     */
    @Override
    @Transactional
    public ServerResult submitOrder(Integer addressId, String phone) {
        ///获取购物车id
        String cartId = (String) redisTemplate.opsForValue().get("cartId_" + phone);
        //获取所有商品
        List<CartBean> cartBeanList = redisTemplate.opsForHash().values(cartId);
        List<CartBean> payCartList=cartBeanList.stream().filter(cartBean -> cartBean.getIsChecked()).collect(Collectors.toList());
        //获取用户id
        UserBean userBean= (UserBean) redisTemplate.opsForValue().get(RedisKeyUtil.getUserKey(phone));
        //生成唯一的订单编号
        String orderId = IdUtil.getOrderId();
        //获取商品总价格
        BigDecimal totalPrice=new BigDecimal(0.00);
        //获取商品总数量
        BigDecimal totalCount=new BigDecimal(0);
        //取出没有库存的商品
        List<CartBean> underStockList=new ArrayList<>();
        //取出购买商品的id
        List<Integer> productIds=new ArrayList<>();
        for (CartBean cartBean : payCartList) {
                Integer productId=cartBean.getProductId();
                //获取商品信息
                String url="http://localhost:8092/productSearch/"+productId;
                String result = HttpClientUtil.doGet(url);
                JSONObject jsonObject = JSON.parseObject(result);
                JSONObject data = JSON.parseObject(jsonObject.get("data").toString());
                int stock = data.getIntValue("stock");
                if(cartBean.getCount()>stock){
                    underStockList.add(cartBean);
                }else {
                    //商品库存减去购买数量是否成功
                Integer successCount=orderMapper.updateProductStock(productId,cartBean.getCount());
                if(successCount==0){
                    underStockList.add(cartBean);
                }else {
                    productIds.add(cartBean.getProductId());
                    //增加商品总价
                    totalPrice=totalPrice.add(cartBean.getSubtotal());
                    //增加商品总数
                    totalCount=totalCount.add(new BigDecimal(cartBean.getCount()));
                    //创建订单详情
                    OrderDetailBean orderDetailBean=new OrderDetailBean();
                    orderDetailBean.setCount(cartBean.getCount());
                    orderDetailBean.setImage(cartBean.getMainImg());
                    orderDetailBean.setPrice(cartBean.getPrice());
                    orderDetailBean.setProductName(cartBean.getProductName());
                    orderDetailBean.setProductId(cartBean.getProductId());
                    orderDetailBean.setUserId(userBean.getId());
                    orderDetailBean.setOrderId(orderId);
                    orderDetailBean.setSubTotalPrice(cartBean.getSubtotal());
                    orderDetailMapper.insert(orderDetailBean);
                }
                }
        }
        //商品库存都不够
        if(totalCount.longValue()==0){
            return ServerResult.error(ResultEnum.PRODUCT_UNDERSTOCK_ALL_ERROR);
        }
        //生成订单数据
        OrderBean orderBean=new OrderBean();
        orderBean.setAddressId(addressId);
        orderBean.setCreateTime(new Date());
        orderBean.setId(orderId);
        orderBean.setPayType(1);
        orderBean.setStatus(SystemConst.ORDER_STATUS_WAIT_PAY);
        orderBean.setTotalPrice(totalPrice);
        orderBean.setTotalCount(Integer.valueOf(String.valueOf(totalCount)));
        orderBean.setUserId(userBean.getId());
        orderMapper.insert(orderBean);
        //生成支付记录表
        PayLogBean payLogBean=new PayLogBean();
        payLogBean.setCreateTime(new Date());
        payLogBean.setOrderId(orderId);
        payLogBean.setOutTradeNo(IdUtil.getOrderId());
        payLogBean.setPayMoney(totalPrice);
        payLogBean.setPayStatus(SystemConst.ORDER_STATUS_WAIT_PAY);
        payLogBean.setPayType(1);
        payLogBean.setUserId(userBean.getId());
        payLogMapper.insert(payLogBean);
        //把支付记录存入redis中订单号作为key
        redisTemplate.opsForHash().put(RedisKeyUtil.getWaitPayKey(phone),payLogBean.getOutTradeNo(),payLogBean);
        //把购买的商品从redis中删除
        for(Integer productId:productIds){
            redisTemplate.opsForHash().delete(cartId,productId);
        }
        //把订单号给返回到前台，
        Map<String,Object> map=new HashMap<>();
        map.put("orderId",orderId);
        map.put("underStockList",underStockList);
        map.put("outTradeNoId",payLogBean.getOutTradeNo());
        return ServerResult.success(map);
    }

    /**
     * 查询支付订单
     * @param outTradeNoId
     * @param phone
     * @return
     */
    @Override
    public PayLogBean getPayOrder(String outTradeNoId, String phone) {
        //获取存入redis的支付订单数据
        PayLogBean cartId = (PayLogBean) redisTemplate.opsForHash().get(RedisKeyUtil.getWaitPayKey(phone),outTradeNoId);
        return cartId;
    }
}
