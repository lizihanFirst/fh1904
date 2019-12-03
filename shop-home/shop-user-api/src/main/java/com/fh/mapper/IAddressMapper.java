package com.fh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.bean.AddressBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface IAddressMapper extends BaseMapper<AddressBean> {
    List<AddressBean> findAddressByUserId(Integer userId);
}
