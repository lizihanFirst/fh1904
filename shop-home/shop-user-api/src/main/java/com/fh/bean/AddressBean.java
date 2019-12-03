package com.fh.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_address")
public class AddressBean implements Serializable {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField("consignee")
    private String consignee;
    @TableField("address")
    private String address;
    @TableField("phone")
    private String phone;
    @TableField("mailbox")
    private String mailbox;
    @TableField("addressByName")
    private String addressByName;
    @TableField("userId")
    private Integer userId;
    @TableField("checkTime")
    private Date checkTime;
    @TableField("isDefault")
    private Integer isDefault;

}
