package com.atguigu.gmall.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class PmsSearchCrumb implements Serializable {

    private String valueId;
    private String valueName;
    private String urlParam;

}
