package com.fh.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("t_shop_user")
@Data
public class UserBean implements Serializable {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField("loginName")
    private String loginName;
    @TableField("loginPwd")
    private String loginPwd;
    @TableField("realName")
    private String realName;
    @TableField("phone")
    private String phone;
    @TableField("credentials")
    private String credentials;//身份证
    @TableField("mail")
    private String mail;
    @TableField("sex")
    private Integer sex;
    @TableField("createTime")
    private Date createTime;
    @TableField("loginTime")
    private Date loginTime;
    @TableField("cartId")
    private String cartId;
}
