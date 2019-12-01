package com.fh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.bean.UserBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface IUserMapper extends BaseMapper<UserBean> {
    UserBean getUserByPhone(String phone);
    //注册用户
    void insertUser(String phone);
}
