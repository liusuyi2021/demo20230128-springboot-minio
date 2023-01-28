package com.example;

import com.example.service.minioService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class Demo20230128SpringbootMinioApplicationTests {

    @Resource
    minioService minio;
    @Test
    void contextLoads() {
        String b = minio.uploadFile();
        System.out.println(b);
    }

}
