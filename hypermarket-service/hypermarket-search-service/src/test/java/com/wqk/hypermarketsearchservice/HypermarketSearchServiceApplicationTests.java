package com.wqk.hypermarketsearchservice;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class HypermarketSearchServiceApplicationTests {
    @Autowired
    private SolrClient solrClient;

    @Test
    void add() throws IOException, SolrServerException {
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id","100");
        document.setField("product_name","商品名称");
        document.setField("product_price","9999");
        document.setField("product_sale_point","歌神");
        document.setField("product_images","暂无");
        solrClient.add(document);
        solrClient.commit();
    }

    @Test
    void query() throws IOException, SolrServerException {
        SolrQuery queryCondition = new SolrQuery();
        queryCondition.setQuery("*:*");
        QueryResponse query = solrClient.query(queryCondition);
        SolrDocumentList results = query.getResults();
        for (SolrDocument result : results) {
            System.out.println(result.get("product_name"));
            System.out.println(result.get("product_price"));
            System.out.println(result.get("product_sale_point"));
            System.out.println(result.get("product_images"));
        }
    }

}
