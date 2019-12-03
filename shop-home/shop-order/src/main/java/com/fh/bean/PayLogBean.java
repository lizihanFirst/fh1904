package com.fh.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("t_paylog")
public class PayLogBean implements Serializable {
    //支付单号
    @TableId(value = "outTradeNo",type = IdType.UUID)
    private String outTradeNo;
    @TableField("orderId")
    private String orderId;//订单id
    @TableField("userId")
    private Integer userId;//用户id
    @TableField("transactionId")
    private Integer transactionId;//事务id
    @TableField("createTime")
    private Date createTime;//创建时间
    @TableField("payTime")
    private Date payTime;//付款时间
    @TableField("payMoney")
    private BigDecimal payMoney;//付款价格
    @TableField("payType")
    private Integer payType;//付款类型
    @TableField("payStatus")
    private Integer payStatus;//付款状态
}
