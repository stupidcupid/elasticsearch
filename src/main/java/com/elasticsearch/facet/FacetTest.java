package com.elasticsearch.facet;

import com.elasticsearch.ESUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.search.facet.Facet;
import org.elasticsearch.search.facet.FacetBuilder;
import org.elasticsearch.search.facet.FacetBuilders;
import org.elasticsearch.search.facet.Facets;
import org.elasticsearch.search.facet.terms.TermsFacet;

import java.util.Map;

/**
 * Created by nanzhou on 2017/9/30.
 */
public class FacetTest {

    public static void main(String[] args) {

        Client client = ESUtils.getClient();
        //过滤器
        BoolFilterBuilder filter = FilterBuilders.boolFilter();
        filter.must(FilterBuilders.matchAllFilter());
        filter.must(FilterBuilders.rangeFilter("age").from(0).to(100));
        //请求
        SearchRequestBuilder searchBuilder = client.prepareSearch(ESUtils.INDEX_NAME).setTypes(ESUtils.USER_TYPE);
        //分组条件
        FacetBuilder marriedFacet = FacetBuilders.termsFacet("married").field("married").allTerms(false);

        marriedFacet.facetFilter(filter);
        searchBuilder.addFacet(marriedFacet);
        long beginTime = System.currentTimeMillis();
        SearchResponse response = searchBuilder //设置过滤条件
                .execute()
                .actionGet();
        Facets facets = response.getFacets();
        Map<String, Facet> map = facets.getFacets();
        Facet facet = map.get("married");
        TermsFacet mFacet = (TermsFacet) facet;
        for (TermsFacet.Entry entry : mFacet.getEntries()) {
            System.out.println("key:" + entry.getTerm().toString() + " count:" + entry.getCount());
        }
        long total = System.currentTimeMillis() - beginTime;
        System.out.println("useTime:" + total);

    }
}
