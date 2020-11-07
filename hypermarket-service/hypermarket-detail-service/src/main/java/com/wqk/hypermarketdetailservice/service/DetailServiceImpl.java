package com.wqk.hypermarketdetailservice.service;

import com.wqk.api.detail.IDetailService;
import com.wqk.common.pojo.ResultBean;
import com.wqk.entity.TProduct;
import com.wqk.mapper.TProductMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;

@Component
@Service
public class DetailServiceImpl implements IDetailService {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private TProductMapper productMapper;
    @Autowired
    private Configuration configuration;
    @Value("${html.locations}")
    private String htmlLocations;
    //jdk提供了线程池
    //1.单例线程池，特点：只有线程，保证任务按顺序执行
    //存在隐患，队列太长Integer.MAX_VALUE,OOM内存溢出
    //private ExecutorService pool1=Executors.newSingleThreadExecutor();
    //2.定长线程池，特点：只有线程，保证任务按顺序执行
    //存在隐患，队列太长Integer.MAX_VALUE,OOM内存溢出
    //private ExecutorService pool2=Executors.newFixedThreadPool(10);
    //3.线程的数量仅限于内存
    //存在隐患，线程数量最大为Integer.MAX_VALUE,OOM内存溢出
    //private ExecutorService pool3=Executors.newCachedThreadPool();
    //线程池的关键参数
    //初始化线程数4，最大线程数8，等待时间3000，等待队列100
    //初始线程4，多余的任务排队，超过100后开启最大线程，任务高峰后，等待时间过后恢复初始化线程数。

    //结合真是服务器硬件条件来设置(内核数)
    private int corePoolSize=Runtime.getRuntime().availableProcessors();
    //由于上述jdk线程池的缺点，建议自己创建线程池
    private ExecutorService pool=new ThreadPoolExecutor(corePoolSize,corePoolSize*2,0L, TimeUnit.SECONDS,new LinkedBlockingDeque<>(100));

    @Override
    public ResultBean createHtmlById(Long id){
        //1.根据id获取数据
        TProduct product = productMapper.selectByPrimaryKey(id);
        try {
            //2.数据结合模板生成详情页
            Template template = configuration.getTemplate("detail.ftl");
            HashMap<String, Object> data = new HashMap<>();
            data.put("product",product);
            FileWriter writer = new FileWriter(htmlLocations + id + ".html");
            template.process(data,writer);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
            return ResultBean.error("生成静态页面失败！");
        }
        return ResultBean.success("生成静态页面成功！");
    }

    @Override
    public ResultBean batchCreateHtml(List<Long> idList) {
        List<Future> list=new ArrayList<>(idList.size());
        for (Long id : idList) {
            Future<Boolean> submit = pool.submit(new CreateHtmlTask(id));
            list.add(submit);
        }
        //查看执行结果
        for (Future future : list) {
            try {
                System.out.println(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return ResultBean.success("批量生成详情页成功");
    }

    private class CreateHtmlTask implements Callable<Boolean>{
        private Long id;
        public CreateHtmlTask(Long id){
            this.id=id;
        }

        @Override
        public Boolean call() throws Exception {
            TProduct product = productMapper.selectByPrimaryKey(id);
            try {
                //2.数据结合模板生成详情页
                Template template = configuration.getTemplate("detail.ftl");
                HashMap<String, Object> data = new HashMap<>();
                data.put("product",product);
                FileWriter writer = new FileWriter(htmlLocations + id + ".html");
                template.process(data,writer);
            } catch (IOException | TemplateException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }
}
