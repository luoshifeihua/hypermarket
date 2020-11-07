package com.wqk.hypermarketproductservice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wqk.api.product.IProductService;
import com.wqk.api.vo.ProductVO;
import com.wqk.common.base.BaseServiceImpl;
import com.wqk.common.base.IBaseDao;
import com.wqk.entity.TProduct;
import com.wqk.entity.TProductDesc;
import com.wqk.mapper.TProductDescMapper;
import com.wqk.mapper.TProductMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Service
public class ProductService extends BaseServiceImpl<TProduct> implements IProductService {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private TProductMapper productMapper;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private TProductDescMapper productDescMapper;

    @Override
    public IBaseDao<TProduct> getBaseDao() {
        return productMapper;
    }

    @Override
    public List<TProduct> list(){
        return productMapper.list();
    }

    @Override
    public PageInfo<TProduct> page(Integer pageIndex, Integer pageSize) {
        PageHelper.startPage(pageIndex,pageSize);
        List<TProduct> list = productMapper.list();
        PageInfo<TProduct> pageInfo=new PageInfo<TProduct>(list,3);
        return pageInfo;
    }

    @Override
    public Long addProduct(ProductVO productVO) {
        /*添加商品基本信息*/
        TProduct tProduct=productVO.getProduct();
        /*设置属性值*/
        tProduct.setFlag(true);
        tProduct.setCreateTime(new Date());
        tProduct.setUpdateTime(tProduct.getCreateTime());
        tProduct.setCreateUser(1L);
        tProduct.setUpdateUser(tProduct.getCreateUser());
        System.err.println(tProduct);
        /*设置主键信息回填*/
        productMapper.insertSelective(tProduct);
        /*添加商品描述*/
        TProductDesc productDesc=new TProductDesc();
        productDesc.setProductId(tProduct.getId());
        productDesc.setpDesc(productVO.getProductDesc());
        productDescMapper.insertSelective(productDesc);

        return tProduct.getId();
    }
}
