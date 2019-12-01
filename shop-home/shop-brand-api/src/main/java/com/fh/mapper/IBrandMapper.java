package com.fh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.bean.BrandBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface IBrandMapper extends BaseMapper<BrandBean> {

    //查询所有品牌
    List<BrandBean> findBrandAll();
}
