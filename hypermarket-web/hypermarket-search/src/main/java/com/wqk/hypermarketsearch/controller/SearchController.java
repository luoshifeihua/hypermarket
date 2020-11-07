package com.wqk.hypermarketsearch.controller;

import com.wqk.api.search.ISearchService;
import com.wqk.common.pojo.ResultBean;
import com.wqk.entity.TProduct;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("search")
@Controller
public class SearchController {
    @Reference
    private ISearchService searchService;

    @RequestMapping("initAllData")
    @ResponseBody
    public ResultBean initAllData(){
        return searchService.initAllData();
    }

    @RequestMapping("searchByKeyWord")
    @ResponseBody
    public List<TProduct> searchByKeyWord(String keyWord, Model model){
        List<TProduct> list=searchService.searchByKeyWord(keyWord);
        //model.addAttribute("list",list);
        return list;
    }
}
