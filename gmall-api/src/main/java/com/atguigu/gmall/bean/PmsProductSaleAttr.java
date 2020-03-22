package com.atguigu.gmall.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

@Data
public class PmsProductSaleAttr implements Serializable {
    @Id
    @Column
    private String id;

    @Column
    private String productId;

    @Column
    private String saleAttrId;

    @Column
    private String saleAttrName;

    @Transient
    List<PmsProductSaleAttrValue> spuSaleAttrValueList;

}