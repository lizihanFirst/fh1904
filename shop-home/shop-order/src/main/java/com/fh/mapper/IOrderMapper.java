package com.fh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.bean.OrderBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface IOrderMapper extends BaseMapper<OrderBean> {
    /**
     * 商品库存减去购买数量
     * @param productId
     * @param count
     * @return
     */
    Integer updateProductStock(@Param("productId") Integer productId,@Param("count") Integer count);
}
