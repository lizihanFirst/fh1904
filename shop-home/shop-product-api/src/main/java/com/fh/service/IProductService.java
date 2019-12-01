package com.fh.service;

import com.fh.commons.DataTableResult;
import com.fh.commons.LayuiTableResult;

import com.fh.commons.ServerResult;
import com.fh.param.ProductParamer;
import com.fh.param.ProductSearchParam;

public interface IProductService {
    //查询商品表
    LayuiTableResult getProductByTypeId(ProductSearchParam productSearchParam);
    //dataable分页查询
    DataTableResult findProductList(ProductParamer productParamer);
    //根据id查询商品
    ServerResult getProductById(Integer productId);
}
