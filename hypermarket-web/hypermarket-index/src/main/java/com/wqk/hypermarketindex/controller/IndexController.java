package com.wqk.hypermarketindex.controller;

import com.wqk.api.product.IProductTypeService;
import com.wqk.entity.TProductType;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("index")
@Controller
public class IndexController {
    @Reference
    private IProductTypeService productTypeService;

    @RequestMapping("show")
    public String showIndex(Model model){
        List<TProductType> list = productTypeService.list();
        model.addAttribute("list",list);
        return "index";
    }


}
