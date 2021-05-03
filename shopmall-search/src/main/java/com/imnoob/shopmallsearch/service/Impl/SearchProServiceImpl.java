package com.imnoob.shopmallsearch.service.Impl;

import com.alibaba.fastjson.JSON;
import com.imnoob.shopmallcommon.exception.BizCodeEnume;
import com.imnoob.shopmallcommon.exception.CustomizeException;
import com.imnoob.shopmallcommon.to.SkuEsModel;
import com.imnoob.shopmallcommon.utils.R;
import com.imnoob.shopmallsearch.dto.ProResultDTO;
import com.imnoob.shopmallsearch.dto.ProSearchDTO;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.util.QueryBuilder;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.ArrayList;

@Service
public class SearchProServiceImpl {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    public ProResultDTO searchProducts(ProSearchDTO proSearchDTO)  {
//        构建查询、
        ProResultDTO result = null;

        SearchRequest searchRequest = buildSearchRequest(proSearchDTO);
        //执行查询

        try {
            SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            result = buildSearchResult(search,proSearchDTO);
        } catch (IOException e) {
             new CustomizeException(BizCodeEnume.SEARCH_EXCEPTION).printStackTrace();
        }
        //封装结果
        return result;

    }

    private ProResultDTO buildSearchResult(SearchResponse search,ProSearchDTO proSearchDTO) {
        ProResultDTO result = new ProResultDTO();
        //封装分页信息
        Long total = search.getHits().getTotalHits().value;
        result.setTotal(total);

        Long pages = total/20;
        if (pages*20 != total) pages++;
        result.setTotalPage(pages.intValue());
        result.setPageNum(proSearchDTO.getPageNum());

        SearchHit[] hits = search.getHits().getHits();
        if (hits != null){
            ArrayList<SkuEsModel> list = new ArrayList<>();
            result.setProducts(list);
            for (SearchHit hit : hits) {
                String tmp = hit.getSourceAsString();
                SkuEsModel item = JSON.parseObject(tmp, SkuEsModel.class);
                list.add(item);
            }
        }
        return result;
    }

    private SearchRequest buildSearchRequest(ProSearchDTO proSearchDTO) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        if (!StringUtils.isEmpty(proSearchDTO.getKeyword())){
            boolQuery.must(QueryBuilders.matchQuery("skuTitle", proSearchDTO.getKeyword()));
        }

        if (proSearchDTO.getCatalogId()!=null){
            boolQuery.filter(QueryBuilders.termQuery("catalogId", proSearchDTO.getCatalogId()));
        }

        if (proSearchDTO.getBrandsId()!=null){
            boolQuery.filter(QueryBuilders.termQuery("brandId", proSearchDTO.getBrandsId()));
        }

        if (proSearchDTO.getSkuprice()!=null && proSearchDTO.getSkuprice() != ""){
            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("skuPrice");

            String[] s = proSearchDTO.getSkuprice().split("_");
            if (s.length == 2){
                rangeQuery.gte(s[0]).lte(s[1]);
            }else if (s.length==1){
                if (proSearchDTO.getSkuprice().startsWith("_")){
                    rangeQuery.lte(s[0]);
                } else rangeQuery.gte(s[0]);
            }
        }

        if (!StringUtils.isEmpty(proSearchDTO.getSort())){
            String sort = proSearchDTO.getSort();
            String[] s = sort.split("_");

            SortOrder order = s[1].equalsIgnoreCase("asc") ? SortOrder.ASC : SortOrder.DESC;
            searchSourceBuilder.sort(s[0], order);
        }
        searchSourceBuilder.from(proSearchDTO.getPageNum());
        searchSourceBuilder.size(proSearchDTO.getPagesize());
        searchSourceBuilder.query(boolQuery);

        System.out.println("构建的DSL："+searchSourceBuilder.toString());
        SearchRequest searchRequest = new SearchRequest(new String[]{"product"}, searchSourceBuilder);
        return searchRequest;
    }
}
