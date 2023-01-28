package com.example.service;

/**
 * @ClassName: minioService
 * @Description:
 * @Author: Administrator
 * @Date: 2023年01月28日 13:18
 * @Version: 1.0
 **/
public interface minioService {
    //判断桶是否存在
   boolean bucketExists();
    String uploadFile();
}
