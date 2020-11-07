package com.wqk.api.product;

import com.github.pagehelper.PageInfo;
import com.wqk.api.vo.ProductVO;
import com.wqk.common.base.IBaseService;
import com.wqk.entity.TProduct;

public interface IProductService extends IBaseService<TProduct> {
    PageInfo<TProduct> page(Integer pageIndex, Integer pageSize);

    Long addProduct(ProductVO productVO);
}
