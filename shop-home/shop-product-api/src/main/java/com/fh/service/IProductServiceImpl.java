package com.fh.service;

import com.fh.commons.DataTableResult;
import com.fh.commons.LayuiTableResult;
import com.fh.commons.ServerResult;
import com.fh.param.ProductParamer;
import com.fh.bean.ProductBean;
import com.fh.mapper.IProductMapper;
import com.fh.param.ProductSearchParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productService")
public class IProductServiceImpl implements IProductService {
    @Autowired
    private IProductMapper productMapper;
    //查询商品表
    @Override
    public LayuiTableResult getProductByTypeId(ProductSearchParam productSearchParam) {
        int count=productMapper.findCount(productSearchParam);
        List<ProductBean> productByTypeId = productMapper.getProductByTypeId(productSearchParam);
        LayuiTableResult layuiTableResult=new LayuiTableResult(0,"正常",count,productByTypeId);
        return layuiTableResult;
    }

    @Override
    public DataTableResult findProductList(ProductParamer productParamer) {
        ProductSearchParam productSearchParam=new ProductSearchParam();
        productSearchParam.setBrandId(productParamer.getBrandId());
        productSearchParam.setTypeId(productParamer.getTypeId());
        productSearchParam.setPage(productParamer.getDraw());
        productSearchParam.setSize(productParamer.getLength());
        productSearchParam.setStart(productParamer.getStart());
        int count=productMapper.findCount(productSearchParam);
        List<ProductBean> productByTypeId = productMapper.getProductByTypeId(productSearchParam);
        DataTableResult dataTableResult=new DataTableResult(productParamer.getDraw(),count,count,productByTypeId);
        return dataTableResult;
    }
    //根据id查询商品
    @Override
    public ServerResult getProductById(Integer productId) {
       ProductBean productBean=productMapper.getProductById(productId);
        return ServerResult.success(productBean);
    }
}
