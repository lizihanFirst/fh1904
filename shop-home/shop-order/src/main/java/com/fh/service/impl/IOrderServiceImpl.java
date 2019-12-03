package com.fh.service.impl;

import com.fh.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("orderService")
public class IOrderServiceImpl implements IOrderService {
    @Autowired
    private RedisTemplate redisTemplate;


}
