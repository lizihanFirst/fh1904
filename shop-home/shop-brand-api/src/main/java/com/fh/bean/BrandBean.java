package com.fh.bean;

import lombok.Data;
import java.io.Serializable;

@Data
public class BrandBean implements Serializable {

    private Integer id;

    private String brandName;

    private String brandPicture;

    private Integer typeId;
}
