package com.fh.service;


import com.fh.vo.ClassifyVo;

import java.util.List;

public interface IClassifyService {
    //查询所有类型
    List<ClassifyVo> findClassifyAll();
}
