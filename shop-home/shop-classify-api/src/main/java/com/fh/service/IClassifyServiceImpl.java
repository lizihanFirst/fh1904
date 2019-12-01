package com.fh.service;

import com.fh.bean.ClassifyBean;
import com.fh.mapper.IClassifyMapper;
import com.fh.vo.ClassifyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("classifyService")
public class IClassifyServiceImpl implements IClassifyService {
    @Autowired
    private IClassifyMapper classifyMapper;
    //查询所有类型
    @Override
    public List<ClassifyVo> findClassifyAll() {
        List<ClassifyBean> classifyBeans = classifyMapper.selectList(null);
        //获取一级
        List<ClassifyVo> classifyVos = getClassifyVos(classifyBeans,0);
        for (ClassifyVo classifyVo : classifyVos) {
            //获取二级
            List<ClassifyVo> classifyVoList = getClassifyVos(classifyBeans,classifyVo.getId());
          classifyVo.setClassifyVolist(classifyVoList);
        }
        return classifyVos;
    }

    private List<ClassifyVo> getClassifyVos(List<ClassifyBean> classifyBeans,Integer pid) {
        List<ClassifyVo> classifyVos=new ArrayList<>();
        for (ClassifyBean classifyBean : classifyBeans) {
            if(classifyBean.getFatherId()==pid){
                ClassifyVo classifyVo=new ClassifyVo();
                classifyVo.setFatherId(classifyBean.getFatherId());
                classifyVo.setClassifyName(classifyBean.getClassifyName());
                classifyVo.setId(classifyBean.getId());
                classifyVos.add(classifyVo);
            }
        }
        return classifyVos;
    }
}
