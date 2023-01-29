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
    void uploadPic() {
        String BucketName="pic";
        String ObjectName="1674878681716(0).jpeg";
        String localFilePath="D:\\pic\\1674878681716(0).jpeg";
        String ContentType="image/JPEG";
        String b = minio.uploadFile(BucketName,ObjectName,localFilePath,ContentType);
        System.out.println(b);
    }
    @Test
    void uploadRecord() {
        String BucketName="record";
        String ObjectName="1674878800020(0).mp4";
        String localFilePath="D:\\record\\1674878800020(0).mp4";
        String ContentType="video/MP4";
        String b = minio.uploadFile(BucketName,ObjectName,localFilePath,ContentType);
        System.out.println(b);
    }
}
