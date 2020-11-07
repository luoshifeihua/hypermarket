package com.wqk.hypermarketbackground.controller;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.wqk.common.pojo.MultiUploadResultBean;
import com.wqk.common.pojo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("file")
@Controller
public class FileController {
    @Value("${image.server}")
    private String image_server;

    @Autowired
    private FastFileStorageClient fileStorageClient;

    @RequestMapping("upload")
    @ResponseBody
    public ResultBean upload(MultipartFile file){
        System.out.println(file+"------->>>");
        //1.获取到文件对象，将文件对象上传到FastDFS上
        String originalFilename = file.getOriginalFilename();
        String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        try {
            StorePath storePath=fileStorageClient.uploadFile(file.getInputStream(),file.getSize(),extName,null);
            //2.把服务器的文件保存地址返回给客户端
            String fullPath = storePath.getFullPath();
            String path=new StringBuilder(image_server).append(fullPath).toString();
            return ResultBean.success(path);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultBean.error("您的网络异常！");
        }
        //3.客户端将这个地址回显到浏览器
    }
    @RequestMapping("multiUpload")
    @ResponseBody
    public MultiUploadResultBean multiUpload(MultipartFile[] files){
        MultiUploadResultBean resultBean=new MultiUploadResultBean();
        String[] data=new String[files.length];
        for (int i = 0; i < files.length; i++) {
            //1.获取到文件对象，将文件对象上传到FastDFS上
            String originalFilename = files[i].getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            try {
                StorePath storePath=fileStorageClient.uploadFile(files[i].getInputStream(),files[i].getSize(),extName,null);
                //2.把服务器的文件保存地址返回给客户端
                String fullPath = storePath.getFullPath();
                String path=new StringBuilder(image_server).append(fullPath).toString();
                data[i]=path;
            } catch (IOException e) {
                e.printStackTrace();
                resultBean.setErrno("-1");
                return resultBean;
            }
        }
        resultBean.setErrno("0");
        resultBean.setData(data);
        return resultBean;
    }
}
