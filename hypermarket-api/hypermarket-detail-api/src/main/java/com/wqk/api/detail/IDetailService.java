package com.wqk.api.detail;

import com.wqk.common.pojo.ResultBean;

import java.util.List;


public interface IDetailService {
    ResultBean createHtmlById(Long id);

    ResultBean batchCreateHtml(List<Long> idList);
}
