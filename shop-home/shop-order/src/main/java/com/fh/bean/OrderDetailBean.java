package com.fh.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
@Data
@TableName("t_shop_order_detail")
public class OrderDetailBean implements Serializable {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField("orderId")
    private String orderId;//订单id
    @TableField("userId")
    private Integer userId;//用户id
    @TableField("productId")
    private Integer productId;//商品id
    @TableField("productName")
    private String productName;//商品名称
    @TableField("price")
    private BigDecimal price;//商品价格
    @TableField("subTotalPrice")
    private BigDecimal subTotalPrice;//商品总价
    @TableField("image")
    private String image;//商品图片
    @TableField("count")
    private Integer count;//商品数量
}
