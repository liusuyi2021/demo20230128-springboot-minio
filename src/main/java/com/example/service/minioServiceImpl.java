package com.example.service;

import com.example.util.MinioUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @ClassName: minioServiceImpl
 * @Description:
 * @Author: Administrator
 * @Date: 2023年01月28日 13:18
 * @Version: 1.0
 **/
@Service
public class minioServiceImpl implements minioService {
    @Resource
    private MinioUtil minioUtil;

    @Override
    public boolean bucketExists() {
        boolean found = minioUtil.isBuckExist("pic");
        if (found) {
            System.out.println("pic exists");
        } else {
            System.out.println("pic does not exist");
        }
        return found;
    }

    @Override
    public String uploadFile() {
        minioUtil.uploadObject("record", "1674878800020(0).mp4", "D:\\record\\1674878800020(0).mp4");
        return minioUtil.presignedGetObject("record", "1674878800020(0).mp4", 7);
    }
}
