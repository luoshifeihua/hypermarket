package com.wqk.hypermarketproductservice.service;

import com.wqk.api.product.IProductTypeService;
import com.wqk.common.base.BaseServiceImpl;
import com.wqk.common.base.IBaseDao;
import com.wqk.entity.TProductType;
import com.wqk.mapper.TProductTypeMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service
public class ProductTypeService extends BaseServiceImpl<TProductType> implements IProductTypeService {
    @Autowired
    private TProductTypeMapper tProductTypeMapper;

    @Override
    public IBaseDao<TProductType> getBaseDao() {
        return tProductTypeMapper;
    }

    @Override
    public List<TProductType> list() {
        return tProductTypeMapper.list();
    }
}
