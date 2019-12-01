package com.fh.param;

import com.fh.commons.PageBean;

public class ProductParamer extends PageBean {
    private Integer typeId;
    private Integer brandId;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }
}

