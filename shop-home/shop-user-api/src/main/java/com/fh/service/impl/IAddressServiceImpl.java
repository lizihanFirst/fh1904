package com.fh.service.impl;

import com.fh.bean.AddressBean;
import com.fh.bean.UserBean;
import com.fh.mapper.IAddressMapper;
import com.fh.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service("addressService")
public class IAddressServiceImpl implements IAddressService {
    @Autowired
    private IAddressMapper addressMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询用户的收货地址
     * @param phone
     * @return
     */
    @Override
    public List<AddressBean> findAddressByUserId(String phone) {
        //获取用户id
        UserBean userBean = (UserBean) redisTemplate.opsForValue().get("user_" + phone);
        List<AddressBean> addressBeanList=addressMapper.findAddressByUserId(userBean.getId());
        return addressBeanList;
    }
    //新增或修改收货地址
    @Override
    public void saveAddress(AddressBean addressBean,String phone) {


        //获取用户id
        UserBean userBean = (UserBean) redisTemplate.opsForValue().get("user_" + phone);
        TimeZone zone = TimeZone.getTimeZone("GMT+8"); //获取中国时区
        TimeZone.setDefault(zone);
        TimeZone.getDefault();
        addressBean.setCheckTime(new Date());
        if(addressBean.getId()!= null){
            addressMapper.updateById(addressBean);
        }else {
            addressBean.setUserId(userBean.getId());
            addressMapper.insert(addressBean);
        }

    }

    /**
     * 根据id查询地址
     * @param id
     * @return
     */
    @Override
    public AddressBean getAddressById(Integer id) {
        return addressMapper.selectById(id);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void deleteAddress(Integer id) {
        addressMapper.deleteById(id);
    }
}
