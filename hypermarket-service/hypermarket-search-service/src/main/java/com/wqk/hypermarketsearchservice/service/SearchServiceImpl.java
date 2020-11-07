package com.wqk.hypermarketsearchservice.service;

import com.wqk.api.search.ISearchService;
import com.wqk.common.pojo.PageResultBean;
import com.wqk.common.pojo.ResultBean;
import com.wqk.entity.TProduct;
import com.wqk.mapper.TProductMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Service
public class SearchServiceImpl implements ISearchService {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private TProductMapper productMapper;
    @Autowired
    private SolrClient solrClient;

    @Override
    public ResultBean initAllData() {
        List<TProduct> list = productMapper.list();
        for (TProduct product : list) {
            SolrInputDocument document = new SolrInputDocument();
            document.setField("id",product.getId());
            document.setField("product_name",product.getName());
            document.setField("product_images",product.getImage());
            document.setField("product_sale_point",product.getSalePoint());
            document.setField("product_price",product.getPrice().intValue());
            try {
                solrClient.add(document);
            } catch (SolrServerException |IOException e) {
                e.printStackTrace();
                return ResultBean.error("添加到索引库失败！");
            }
        }
        try {
            solrClient.commit();
        } catch (SolrServerException |IOException e) {
            e.printStackTrace();
            return ResultBean.error("添加到索引库失败！");
        }
        return ResultBean.success("数据同步成功！");
    }

    @Override
    public ResultBean updateByID(Long id) {
        //数据库查询变更数据
        TProduct product = productMapper.selectByPrimaryKey(id);
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id",product.getId());
        document.setField("product_name",product.getName());
        document.setField("product_images",product.getImage());
        document.setField("product_sale_point",product.getSalePoint());
        document.setField("product_price",product.getPrice().intValue());
        try {
            solrClient.add(document);
            solrClient.commit();
        } catch (SolrServerException |IOException e) {
            e.printStackTrace();
            return ResultBean.error("添加到索引库失败！");
        }
        return ResultBean.success("数据同步成功！");
    }

    @Override
    public List<TProduct> searchByKeyWord(String keyWord) {
        //组装查询条件
        SolrQuery solrQuery=new SolrQuery();
        if (!StringUtils.isAllEmpty(keyWord)){
            solrQuery.setQuery("product_keywords:"+keyWord);
        }else {
            solrQuery.setQuery("product_keywords:华为");
        }
        //添加高亮效果
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("product_name");
        solrQuery.addHighlightField("product_sale_point");
        solrQuery.setHighlightSimplePre("<font color='red'>");
        solrQuery.setHighlightSimplePost("</font>");
        //执行查询
        List<TProduct> list= null;
        try {
            QueryResponse query = solrClient.query(solrQuery);
            //将结果List<document>转换成List<TProduct>
            SolrDocumentList results = query.getResults();
            //获取高亮信息
            Map<String, Map<String, List<String>>> highlighting = query.getHighlighting();
            list=new ArrayList<>(results.size());
            for (SolrDocument document : results) {
                TProduct product=new TProduct();
                product.setId(Long.parseLong(document.getFieldValue("id").toString()));
                //product.setName(document.getFieldValue("product_name").toString());
                product.setImage(document.getFieldValue("product_images").toString());
                product.setPrice(new BigDecimal(document.getFieldValue("product_price").toString()));
                //product.setSalePoint(document.getFieldValue("product_sale_point").toString());
                //获取商品名称的高亮信息
                //获取某条高亮记录
                Map<String, List<String>> id = highlighting.get(document.getFieldValue("id").toString());
                //获取某个高亮字段
                //按照商品名称查找
                List<String> product_name = id.get("product_name");
                if (product_name!=null&&product_name.size()>0){
                    product.setName(product_name.get(0));
                }else {
                    product.setName(document.getFieldValue("product_name").toString());
                }
                //按照商品卖点查找
                List<String> product_sale_point = id.get("product_sale_point");
                if (product_sale_point!=null&&product_sale_point.size() > 0){
                    product.setSalePoint(product_sale_point.get(0));
                }else {
                    product.setSalePoint(document.getFieldValue("product_sale_point").toString());
                }
                list.add(product);
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public PageResultBean<TProduct> searchByKeyWord(String keyWord, Integer pageIndex, Integer rows) {
        PageResultBean<TProduct> pageResultBean=new PageResultBean<>();
        //组装查询条件
        SolrQuery solrQuery=new SolrQuery();
        if (!StringUtils.isAllEmpty(keyWord)){
            solrQuery.setQuery("product_keywords:"+keyWord);
        }else {
            solrQuery.setQuery("product_keywords:华为");
        }
        //添加高亮效果
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("product_name");
        solrQuery.addHighlightField("product_sale_point");
        solrQuery.setHighlightSimplePre("<font color='red'>");
        solrQuery.setHighlightSimplePost("</font>");
        //分页
        solrQuery.setStart((pageIndex-1)*rows);
        solrQuery.setRows(rows);
        //执行查询
        List<TProduct> list= null;
        //获取查询总条数
        long totalCount=0L;
        try {
            QueryResponse query = solrClient.query(solrQuery);
            //将结果List<document>转换成List<TProduct>
            SolrDocumentList results = query.getResults();
            totalCount = results.getNumFound();
            //获取高亮信息
            Map<String, Map<String, List<String>>> highlighting = query.getHighlighting();
            list=new ArrayList<>(results.size());
            for (SolrDocument document : results) {
                TProduct product=new TProduct();
                product.setId(Long.parseLong(document.getFieldValue("id").toString()));
                //product.setName(document.getFieldValue("product_name").toString());
                product.setImage(document.getFieldValue("product_images").toString());
                product.setPrice(new BigDecimal(document.getFieldValue("product_price").toString()));
                //product.setSalePoint(document.getFieldValue("product_sale_point").toString());
                //获取商品名称的高亮信息
                //获取某条高亮记录
                Map<String, List<String>> id = highlighting.get(document.getFieldValue("id").toString());
                //获取某个高亮字段
                //按照商品名称查找
                List<String> product_name = id.get("product_name");
                if (product_name!=null&&product_name.size()>0){
                    product.setName(product_name.get(0));
                }else {
                    product.setName(document.getFieldValue("product_name").toString());
                }
                //按照商品卖点查找
                List<String> product_sale_point = id.get("product_sale_point");
                if (product_sale_point!=null&&product_sale_point.size() > 0){
                    product.setSalePoint(product_sale_point.get(0));
                }else {
                    product.setSalePoint(document.getFieldValue("product_sale_point").toString());
                }
                list.add(product);
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pageResultBean.setList(list);
        pageResultBean.setPageNum(pageIndex);
        pageResultBean.setPageSize(rows);
        pageResultBean.setTotal(totalCount);
        pageResultBean.setPages((int) (totalCount%rows==0?totalCount/rows:(totalCount/rows)+1));
        return pageResultBean;
    }
}
