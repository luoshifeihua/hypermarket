package com.fastdfs;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@SpringBootTest
class FastdfsApplicationTests {
    @Autowired
    private FastFileStorageClient fileStorageClient;

    @Test
    void contextLoads() throws FileNotFoundException {
        File file=new File("d://mimg.jpg");
        String fileName = file.getName();
        String extName = fileName.substring(fileName.lastIndexOf(".") + 1);
        InputStream inputStream = new FileInputStream(file);
        StorePath storePath=fileStorageClient.uploadFile(inputStream,file.length(),extName,null);
        System.out.println(storePath.getGroup());
        System.out.println(storePath.getPath());
        System.out.println(storePath.getFullPath());
    }
}
