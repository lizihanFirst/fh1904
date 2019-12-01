package com.fh.service;

import com.fh.mapper.IUserMapper;
import com.fh.bean.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service("userService")
public class IUserServiceImpl implements IUserService {
    @Autowired
    private IUserMapper userMapper;



    @Override
    @Transactional
    public UserBean isRegister(String phone) {
        //判断手机是否已被注册
        UserBean userByPhone = userMapper.getUserByPhone(phone);
        if(userByPhone==null){
            UserBean userBean=new UserBean();
            userBean.setCreateTime(new Date());
            userBean.setLoginTime(new Date());
            userBean.setPhone(phone);
            userBean.setCartId(UUID.randomUUID().toString());
           userMapper.insert(userBean);
            return userBean;
        }else {
            userByPhone.setLoginTime(new Date());
            userMapper.updateById(userByPhone);
            return userByPhone;
        }

    }
}
