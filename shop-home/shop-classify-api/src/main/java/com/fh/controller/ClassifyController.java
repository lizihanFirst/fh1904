package com.fh.controller;

import com.fh.redis.IRedisService;
import com.fh.service.IClassifyService;
import com.fh.vo.ClassifyVo;
import com.fh.commons.ServerResult;
import com.fh.commons.SystemConst;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("classify")
@CrossOrigin(maxAge = 3600,origins = "http://localhost:8080")
public class ClassifyController {
    @Resource(name = "classifyService")
    private IClassifyService classifyService;
    @Resource(name = "redisService")
    private IRedisService redisService;
    //查询所有类型
    @GetMapping
    public ServerResult findClassifyAll(){
        //判断redis中是否有此条数据
        boolean b = redisService.hasKey(SystemConst.REDIS_CLASSIFY_LIST);
        if(b){
            Object o = redisService.getString(SystemConst.REDIS_CLASSIFY_LIST);
            return ServerResult.success(o);
        }else {
            List<ClassifyVo> classifyAll = classifyService.findClassifyAll();
            redisService.setString(SystemConst.REDIS_CLASSIFY_LIST, classifyAll);
            return ServerResult.success(classifyAll);
        }

    }

}
