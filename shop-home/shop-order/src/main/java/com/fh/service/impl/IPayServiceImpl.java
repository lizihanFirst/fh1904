package com.fh.service.impl;

import com.fh.bean.OrderBean;
import com.fh.bean.PayLogBean;
import com.fh.commons.ResultEnum;
import com.fh.commons.ServerResult;
import com.fh.commons.SystemConst;
import com.fh.mapper.IOrderMapper;
import com.fh.mapper.IPayLogMapper;
import com.fh.service.IPayService;
import com.fh.util.DateUtil;
import com.fh.util.MyWxConfig;
import com.fh.utils.RedisKeyUtil;
import com.github.wxpay.sdk.WXPay;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("payService")
public class IPayServiceImpl implements IPayService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IPayLogMapper payLogMapper;
    @Autowired
    private IOrderMapper orderMapper;
    @Override
    public ServerResult createPayCode(String outTradeNo, String phone) {
        PayLogBean payLogBean= (PayLogBean) redisTemplate.opsForHash().get(RedisKeyUtil.getWaitPayKey(phone),outTradeNo);
        if(payLogBean==null){
            return ServerResult.error(ResultEnum.PAY_ORDER_NULL);
        }
        //调用支付接口
        MyWxConfig myWxConfig=new MyWxConfig();
        try {
            WXPay wxPay=new WXPay(myWxConfig);
            //创建map存入接口用的参数
            Map<String,String> data=new HashMap<>();
            data.put("body","飞狐1904B电商支付--平台");
            //设置订单号
            data.put("out_trade_no",outTradeNo);
            //设置币种
            data.put("fee_type","CNY");
            //设置支付失效时间
            Date date = DateUtils.addMinutes(new Date(), 2);
            data.put("time_expire",DateUtil.getYyyyMMhhmmss(date,DateUtil.yyyyMMhhmmss));
            //设置支付价格
            int payMoney=1;
            data.put("total_fee",payMoney+"");
            //设置接口的调用路径
            data.put("notify_url","http://www.example.com/wxpay/notify");
            //指定为扫码支付
            data.put("trade_type","NATIVE");
            //调用微信支付接口，取出返回的数据
            Map<String, String> returnMap = wxPay.unifiedOrder(data);
            String returnCode = returnMap.get("return_code");
            String returnMsg = returnMap.get("return_msg");
            //判断接口是否连接成功
            if(!"SUCCESS".equalsIgnoreCase(returnCode)){
                return ServerResult.error(500,returnMsg);
            }
            String resultCode = returnMap.get("result_code");
            String errorCodeDes = returnMap.get("err_code_des");
            //判断业务是否成功
            if(!"SUCCESS".equalsIgnoreCase(resultCode)){
                return ServerResult.error(500,errorCodeDes);
            }
            String codeUrl = returnMap.get("code_url");
            //创建返回的map集合
            Map<String,Object> map=new HashMap<>();
            map.put("outTradeNo",outTradeNo);
            map.put("payMoney",payLogBean.getPayMoney());
            map.put("codeUrl",codeUrl);
            return ServerResult.success(map);
        }catch (Exception e){
            return ServerResult.error(ResultEnum.CRATER_PAY_ERROR);
        }
    }

    /**
     * 查询支付订单是否被支付
     * @param outTradeNo
     * @param phone
     * @return
     */
    @Override
    @Transactional
    public ServerResult checkPayStatus(String outTradeNo, String phone) {
        PayLogBean payLogBean= (PayLogBean) redisTemplate.opsForHash().get(RedisKeyUtil.getWaitPayKey(phone),outTradeNo);
        if(payLogBean==null){
            return ServerResult.error(ResultEnum.PAY_ORDER_NULL);
        }
        //调用支付接口
        MyWxConfig myWxConfig=new MyWxConfig();
        int count=0;
        while (true) {
            try {
                WXPay wxPay = new WXPay(myWxConfig);
                //创建map存入接口用的参数
                Map<String, String> data = new HashMap<String, String>();
                //设置订单号
                data.put("out_trade_no", outTradeNo);
                //查询支付订单状态
                Map<String, String> returnMap = wxPay.orderQuery(data);
                String returnCode = returnMap.get("return_code");
                String returnMsg = returnMap.get("return_msg");
                //判断接口是否连接成功
                if (!"SUCCESS".equalsIgnoreCase(returnCode)) {
                    return ServerResult.error(500, returnMsg);
                }
                String resultCode = returnMap.get("result_code");
                String errorCodeDes = returnMap.get("err_code_des");
                //判断业务是否成功
                if (!"SUCCESS".equalsIgnoreCase(resultCode)) {
                    return ServerResult.error(500, errorCodeDes);
                }
                String tradeState = returnMap.get("trade_state");
                if ("SUCCESS".equalsIgnoreCase(tradeState)) {
                    //支付成功修改支付订单状态
                    PayLogBean payLog=new PayLogBean();
                    payLog.setPayStatus(SystemConst.PAY_SUCCESS);
                    payLog.setOutTradeNo(outTradeNo);
                    payLog.setPayTime(new Date());
                    //获取微信生成的订单号，并存入数据库
                    String transactionId = returnMap.get("transaction_id");
                    payLog.setTransactionId(transactionId);
                    payLogMapper.updateById(payLog);
                    //修改订单状态
                    OrderBean orderBean=new OrderBean();
                    orderBean.setId(payLogBean.getOrderId());
                    orderBean.setStatus(SystemConst.PAY_SUCCESS);
                    orderBean.setPayTime(new Date());
                    orderMapper.updateById(orderBean);
                    //删除支付订单
                    redisTemplate.opsForHash().delete(RedisKeyUtil.getWaitPayKey(phone),outTradeNo);
                    return ServerResult.success();
                }
                count++;
                //设置循环休息时间
                Thread.sleep(3000l);
                //超过两分钟就返回
                if(count>40){
                    return ServerResult.error(ResultEnum.PAY_TIMEOUT);
                }
            } catch (Exception e) {
                return ServerResult.error(ResultEnum.CRATER_PAY_ERROR);
            }
        }

    }
}
