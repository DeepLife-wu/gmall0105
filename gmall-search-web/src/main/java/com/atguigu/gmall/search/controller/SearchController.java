package com.atguigu.gmall.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.annotations.LoginRequired;
import com.atguigu.gmall.bean.PmsBaseAttrInfo;
import com.atguigu.gmall.bean.PmsBaseAttrValue;
import com.atguigu.gmall.bean.PmsSearchCrumb;
import com.atguigu.gmall.bean.PmsSearchParam;
import com.atguigu.gmall.bean.PmsSearchSkuInfo;
import com.atguigu.gmall.bean.PmsSkuAttrValue;
import com.atguigu.gmall.service.AttrService;
import com.atguigu.gmall.service.SearchService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class SearchController {

    @Reference
    SearchService searchService;
    @Reference
    AttrService attrService;

    @RequestMapping("index")
    @LoginRequired(loginSuccess = false)
    public String index() {
        return "index";
    }

    @RequestMapping("list")
    public String list(PmsSearchParam pmsSearchParam, ModelMap modelMap) {
        //调用搜索服务，返回结果
        List<PmsSearchSkuInfo> pmsSearchSkuInfoList = searchService.list(pmsSearchParam);
        modelMap.put("skuLsInfoList",pmsSearchSkuInfoList);

        Set<String> valueIdSet = new HashSet<>();
        for (PmsSearchSkuInfo pmsSearchSkuInfo : pmsSearchSkuInfoList) {
            List<PmsSkuAttrValue> skuAttrValueList = pmsSearchSkuInfo.getSkuAttrValueList();
            for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
                String valueId = pmsSkuAttrValue.getValueId();
                valueIdSet.add(valueId);
            }
        }

        List<PmsBaseAttrInfo> pmsBaseAttrInfoList = attrService.getAttrValueListByValueId(valueIdSet);
        modelMap.put("attrList",pmsBaseAttrInfoList);

        Map<String,String> cacheMap = new HashMap<>();
        //对平台属性集合进一步处理，去掉当前条件中valudId所在属性组
        String[] delValueIds = pmsSearchParam.getValueId();
        if(delValueIds != null) {
            Iterator<PmsBaseAttrInfo> iterator = pmsBaseAttrInfoList.iterator();
            while(iterator.hasNext()) {
                PmsBaseAttrInfo pmsBaseAttrInfo = iterator.next();
                List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
                for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
                    String valueId = pmsBaseAttrValue.getId();
                    cacheMap.put(valueId,pmsBaseAttrValue.getValueName());
                    for (String delValueId : delValueIds) {
                        if(delValueId.equals(valueId)) {
                            iterator.remove();
                        }
                    }
                }
            }
        }

        String urlParam = getUrlParam(pmsSearchParam);
        modelMap.put("urlParam",urlParam);

        String keyword = pmsSearchParam.getKeyword();
        if(StringUtils.isNotBlank(keyword)) {
            modelMap.put("keyword",keyword);
        }

        //面包屑
        List<PmsSearchCrumb> pmsSearchCrumbList = new ArrayList<>();
        if(delValueIds != null) {
            //当前请示训包含属性的参数，每一个属性参数，都会生成一个面包屑
            for (String delValueId : delValueIds) {
                PmsSearchCrumb pmsSearchCrumb = new PmsSearchCrumb();
                pmsSearchCrumb.setValueId(delValueId);
                pmsSearchCrumb.setValueName(cacheMap.get(delValueId));
                pmsSearchCrumb.setUrlParam(getUrlParamForCrumb(pmsSearchParam,delValueId));
                pmsSearchCrumbList.add(pmsSearchCrumb);
            }
        }
        modelMap.put("attrValueSelectedList",pmsSearchCrumbList);
        return "list";
    }

    private String getUrlParam(PmsSearchParam pmsSearchParam) {
        String keyword = pmsSearchParam.getKeyword();
        String catalog3Id = pmsSearchParam.getCatalog3Id();
//        List<PmsSkuAttrValue> pmsSkuAttrValueList = pmsSearchParam.getPmsSkuAttrValueList();
        String[] pmsSkuAttrValueList = pmsSearchParam.getValueId();

        String urlParam = "";
        if(StringUtils.isNotBlank(keyword)) {
            if(StringUtils.isNotBlank(urlParam)) {
                urlParam += "&";
            }
            urlParam = urlParam + "keyword=" + keyword;
        }
        if(StringUtils.isNotBlank(catalog3Id)) {
            if(StringUtils.isNotBlank(urlParam)) {
                urlParam += "&";
            }
            urlParam = urlParam + "catalog3Id=" + catalog3Id;
        }

        if(pmsSkuAttrValueList != null/*CollectionUtils.isNotEmpty(pmsSkuAttrValueList)*/) {
            /*for (PmsSkuAttrValue pmsSkuAttrValue : pmsSkuAttrValueList) {
                String valueId = pmsSkuAttrValue.getValueId();
                urlParam = urlParam + "&valueId=" + valueId;
            }*/
            for (String valueId : pmsSkuAttrValueList) {
                urlParam = urlParam + "&valueId=" + valueId;
            }
        }

        return urlParam;
    }

    private String getUrlParamForCrumb(PmsSearchParam pmsSearchParam,String delValueId) {
        String keyword = pmsSearchParam.getKeyword();
        String catalog3Id = pmsSearchParam.getCatalog3Id();
        String[] pmsSkuAttrValueList = pmsSearchParam.getValueId();

        String urlParam = "";
        if(StringUtils.isNotBlank(keyword)) {
            if(StringUtils.isNotBlank(urlParam)) {
                urlParam += "&";
            }
            urlParam = urlParam + "keyword=" + keyword;
        }
        if(StringUtils.isNotBlank(catalog3Id)) {
            if(StringUtils.isNotBlank(urlParam)) {
                urlParam += "&";
            }
            urlParam = urlParam + "catalog3Id=" + catalog3Id;
        }

        if(pmsSkuAttrValueList != null) {
            for (String valueId : pmsSkuAttrValueList) {
                if(!valueId.equals(delValueId)) {
                    urlParam = urlParam + "&valueId=" + valueId;
                }
            }
        }

        return urlParam;
    }
}
