package com.jt.solr.service;

import com.jt.dubbo.pojo.Item;
import com.jt.dubbo.service.DubboSearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class DubboSearchServiceImpl implements DubboSearchService {

    @Autowired
    private HttpSolrServer httpSolrServer;

    @Override
    public List<Item> findItemByKey(String keyWord) {
        // 1. 定义查询的检索对象
        SolrQuery solrQuery = new SolrQuery(keyWord);
        // 2. 设定分页查询
        solrQuery.setStart(0);
        solrQuery.setRows(20);
        // 3. 设定查询
        try {
            QueryResponse response = httpSolrServer.query(solrQuery);
            // 4. 将返回值转化对象
            List<Item> itemList = response.getBeans(Item.class);
            return itemList;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
