package com.fh.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@TableName("t_shop_product")
@Data
public class ProductBean implements Serializable {
        @TableId(value = "id",type =IdType.AUTO )
         private Integer id;
         @TableField("brand_id")
         private Integer brandId;//品牌外键
         @TableField("productName")
         private String productName;//商品名称
         @TableField("subtitle")
         private String subtitle;//宣传标题
         @TableField("main_img")
         private String mainImg;//主图片
         @TableField("sub_imgs")
         private String subImgs;//副图片
        @TableField("detail")
         private String detail;//商品描述
        @TableField("price")
        private BigDecimal price;//价格
        @TableField("stock")
        private Integer stock;//库存
        @TableField("status")
        private Integer status;//状态
        @TableField("create_time")//创建时间
        private Date createTime;
        @TableField("update_time")
        private Date updateTime;//修改时间
}
