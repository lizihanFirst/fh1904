package com.fh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.bean.ProductBean;
import com.fh.param.ProductSearchParam;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface IProductMapper extends BaseMapper<ProductBean> {
    List<ProductBean> getProductByTypeId(ProductSearchParam productSearchParam);
    //查询总条数
    int findCount(ProductSearchParam productSearchParam);
    //根据id查询商品
    ProductBean getProductById(Integer productId);
}
