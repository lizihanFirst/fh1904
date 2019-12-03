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
@TableName("t_order")
public class OrderBean implements Serializable {
    @TableId(value = "id",type = IdType.UUID)
    private String id;
    @TableField("userId")
    private Integer userId;//用户id
    @TableField("status")
    private Integer status;//状态
    @TableField("totalPrice")
    private BigDecimal totalPrice;//总价格
    @TableField("totalCount")
    private Integer totalCount;//商品总个数
    @TableField("payType")
    private Integer payType;//付款类型
    @TableField("addressId")
    private Integer addressId;//地址id
    @TableField("payTime")
    private Date payTime;//付款时间
    @TableField("createTime")
    private Date createTime;//创建时间
}
