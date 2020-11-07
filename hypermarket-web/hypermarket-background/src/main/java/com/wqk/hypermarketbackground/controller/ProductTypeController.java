package com.wqk.hypermarketbackground.controller;

import com.wqk.api.product.IProductTypeService;
import com.wqk.entity.TProductType;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("productType")
public class ProductTypeController {
    @Reference
    private IProductTypeService productTypeService;

    @RequestMapping("list")
    public List<TProductType> list(){
        return productTypeService.list();
    }
}
