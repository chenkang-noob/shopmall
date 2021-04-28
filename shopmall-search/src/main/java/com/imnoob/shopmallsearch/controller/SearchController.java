package com.imnoob.shopmallsearch.controller;

import com.alibaba.fastjson.JSON;
import com.imnoob.shopmallcommon.to.SkuEsModel;
import com.imnoob.shopmallcommon.utils.R;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("search")
public class SearchController {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @PostMapping("/upgoods")
    public R upgoods(@RequestBody List<SkuEsModel> skuEsModels){

        System.out.println("商品上架中----------");
        BulkRequest bulkRequest = new BulkRequest();
        for (SkuEsModel item : skuEsModels) {
            IndexRequest indexRequest = new IndexRequest("product");
            indexRequest.id(item.getSkuId().toString());
            String s = JSON.toJSONString(item);
            indexRequest.source(s, XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        try {
            restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            return R.error();

        }
        return R.ok();
    }
}
