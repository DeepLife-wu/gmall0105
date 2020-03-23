package com.atguigu.gmall.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.PmsProductSaleAttr;
import com.atguigu.gmall.bean.PmsSkuInfo;
import com.atguigu.gmall.service.SkuService;
import com.atguigu.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ItemController {

    @Reference
    SkuService skuService;
    @Reference
    SpuService spuService;

    @RequestMapping("{skuId}.html")
    public String item(@PathVariable String skuId,ModelMap modelMap) {
        PmsSkuInfo pmsSkuInfo = skuService.getSkuById(skuId);
        //sku对象
        modelMap.put("skuInfo",pmsSkuInfo);

        List<PmsProductSaleAttr> pmsProductSaleAttrList = spuService.spuSaleAttrListCheckBySku(pmsSkuInfo.getProductId(),skuId);
        //销售属性列表
        modelMap.put("spuSaleAttrListCheckBySku",pmsProductSaleAttrList);

        return "item";
    }

    @RequestMapping("index")
    public String index(ModelMap modelMap) {
        List<String> list = new ArrayList<>();
        for(int i = 0;i < 5; i++) {
            list.add("循环数据：" + i);
        }
        modelMap.put("list",list);
        modelMap.put("hello","hello themeleaf");
        return "index";
    }

}
