package com.fh.service;

import com.fh.bean.BrandBean;
import com.fh.mapper.IBrandMapper;
import com.fh.commons.ServerResult;
import com.fh.commons.SystemConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("brandService")
public class IBrandServiceImpl implements IBrandService {
    @Autowired
    private IBrandMapper brandMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    //查询品牌
    @Override
    public ServerResult findBrandAll(Integer typeId) {
        //查看redis中是否有品牌缓存
        Boolean aBoolean = redisTemplate.hasKey(SystemConst.REDIS_BRAND_LIST);
        List<BrandBean> brandBeanList=new ArrayList<>();

        if(aBoolean){
            brandBeanList  = (List<BrandBean>) redisTemplate.opsForValue().get(SystemConst.REDIS_BRAND_LIST);
        }else {
            //查询所有品牌
            brandBeanList = brandMapper.findBrandAll();
            redisTemplate.opsForValue().set(SystemConst.REDIS_BRAND_LIST,brandBeanList);
        }
        //进行过滤
       List<BrandBean> returnList = brandBeanList.stream().filter(brand -> brand.getTypeId().equals(typeId)).collect(Collectors.toList());
        return ServerResult.success(returnList);
    }
}
