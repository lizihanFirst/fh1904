package com.fh.redis;

import com.fh.vo.ClassifyVo;

import java.util.List;

public interface IRedisService {

    boolean hasKey(String redisClassifyList);

    Object getString(String redisClassifyList);

    void setString(String redisClassifyList, List<ClassifyVo> classifyAll);
}
