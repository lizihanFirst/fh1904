package com.fh.redis;

import com.fh.vo.ClassifyVo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("redisService")
public class IRedisServiceImpl implements IRedisService {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public boolean hasKey(String redisClassifyList) {

        return redisTemplate.hasKey(redisClassifyList);
    }

    @Override
    public Object getString(String redisClassifyList) {

        return redisTemplate.opsForValue().get(redisClassifyList);
    }

    @Override
    public void setString(String redisClassifyList, List<ClassifyVo> classifyAll) {
        redisTemplate.opsForValue().set(redisClassifyList,classifyAll);
    }
}
