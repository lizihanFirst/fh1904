package com.fh.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class ProudctVo implements Serializable {

    private Integer id;

    private Integer brandId;//品牌外键
    private String brandName;

    private String productName;//商品名称

    private String subtitle;//宣传标题

    private String mainImg;//主图片

    private String subImgs;//副图片

    private String detail;//商品描述

    private Double price;//价格

    private Integer stock;//库存

    private Integer status;//状态
 //创建时间
    private Date createTime;

    private Date updateTime;//修改时间
}
