package com.wqk.api.search;

import com.wqk.common.pojo.PageResultBean;
import com.wqk.common.pojo.ResultBean;
import com.wqk.entity.TProduct;

import java.util.List;

public interface ISearchService {
    ResultBean initAllData();

    ResultBean updateByID(Long id);

    List<TProduct> searchByKeyWord(String keyWord);

    PageResultBean<TProduct> searchByKeyWord(String keyWord,Integer pageIndex,Integer rows);
}
