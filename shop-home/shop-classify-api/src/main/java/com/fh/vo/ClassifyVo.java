package com.fh.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class ClassifyVo implements Serializable {
    private Integer id;
    private String classifyName;
    private Integer fatherId;
    private List classifyVolist;

}
