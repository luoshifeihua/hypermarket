package com.wqk.hypermarketbackground.controller;

import com.github.pagehelper.PageInfo;
import com.wqk.api.detail.IDetailService;
import com.wqk.api.product.IProductService;
import com.wqk.api.search.ISearchService;
import com.wqk.api.vo.ProductVO;
import com.wqk.common.constant.RabbitMQConstant;
import com.wqk.common.pojo.ResultBean;
import com.wqk.entity.TProduct;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("product")
public class ProductController {
    @Reference
    private IProductService productService;
    @Reference
    private ISearchService searchService;
    @Reference
    private IDetailService detailService;
    @Autowired
    private RabbitTemplate template;

    @GetMapping("list")
    public String list(Model model){
        List<TProduct> list=productService.list();
        model.addAttribute("list",list);
        return "product/list";
    }

    @GetMapping("page/{pageIndex}/{pageSize}")
    public String page(Model model, @PathVariable("pageIndex") Integer pageIndex,@PathVariable("pageSize") Integer pageSize){
        PageInfo<TProduct> pageInfo=productService.page(pageIndex,pageSize);
        model.addAttribute("pageInfo",pageInfo);
        return "product/list";
    }

    @PostMapping("addProduct")
    public String add(ProductVO productVO){
        /*返回商品id*/
        Long id=productService.addProduct(productVO);
        template.convertAndSend(RabbitMQConstant.BACKGROUND_EXCHANGE,"product.add",id);
        //调用solr服务的增量更新接口
        //searchService.updateByID(id);
        //生成静态详情页
        //detailService.createHtmlById(id);
        return "redirect:/product/page/1/1";
    }
}
