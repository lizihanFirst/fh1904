package com.fh.commons;


import java.util.List;


public class LayuiTableResult {
    private Integer code;
    private String mag;
    private Integer count;
    private List data;
    public LayuiTableResult(){}
    public LayuiTableResult(Integer code, String mag, Integer count, List data){
        this.code=code;
        this.count=count;
        this.mag=mag;
        this.data=data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMag() {
        return mag;
    }

    public void setMag(String mag) {
        this.mag = mag;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}
