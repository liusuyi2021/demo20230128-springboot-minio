package com.example.util;

/**
 * @ClassName: MinioUtil
 * @Description:
 * @Author: Administrator
 * @Date: 2023年01月28日 13:28
 * @Version: 1.0
 **/

import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;



/**
 * minio 工具类，提供上传、下载方法
 */
@Slf4j
@Component

@RequiredArgsConstructor
public class MinioUtil {

    private final MinioClient minioClient;

    /**
     * 判断bulk桶是否存在
     *
     * @param bulkName
     * @return
     */
    public  boolean isBuckExist(String bulkName) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bulkName).build());
        } catch (Exception e) {
            log.error("bulkname is not exist,error " + e.getMessage());
            return false;
        }
    }

    /**
     * 上传对象
     *
     * @param bulkName
     * @param objectName
     * @param localFilePathName
     * @return
     */
    public  boolean uploadObject(String bulkName, String objectName, String localFilePathName) {
        if (isBuckExist(bulkName)) {
            try {
                minioClient.uploadObject(UploadObjectArgs.builder().bucket(bulkName).object(objectName).filename(localFilePathName).build());
                return true;
            } catch (Exception e) {
                log.error("minio upload object file error " + e.getMessage());
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 下载对象
     *
     * @param bulkName
     * @param objectName
     * @param localFilePathName
     * @return
     */
    public  boolean downloadObject(String bulkName, String objectName, String localFilePathName) {
        if (isBuckExist(bulkName)) {
            try {
                minioClient.downloadObject(DownloadObjectArgs.builder().bucket(bulkName).object(objectName).filename(localFilePathName).build());
                return true;
            } catch (Exception e) {
                log.error("minio download object file error " + e.getMessage());
                return false;
            }

        } else {
            return false;
        }
    }

    /**
     * 删除对象
     *
     * @param bulkName
     * @param objectName
     * @return
     */
    public  boolean deleteObject(String bulkName, String objectName) {
        if (isBuckExist(bulkName)) {
            try {
                minioClient.removeObject(RemoveObjectArgs.builder().bucket(bulkName).object(objectName).build());
                return true;
            } catch (Exception e) {
                log.error("minio delete object file error " + e.getMessage());
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 上传文件
     * 返回可以直接预览文件的URL
     */
    public  String uploadFile(String bucketName,MultipartFile file) {
        try {
            //如果存储桶不存在则创建
            if (!isBuckExist(bucketName)) {
               // createBucket(bulkName);
                System.out.println("桶不存在");
            }
            PutObjectArgs objectArgs = PutObjectArgs.builder().object(file.getOriginalFilename())
                    .bucket(bucketName)
                    .contentType(file.getContentType())
                    .stream(file.getInputStream(), file.getSize(), -1).build();

            ObjectWriteResponse objectWriteResponse = minioClient.putObject(objectArgs);
            System.out.println(objectWriteResponse.etag());
            return "";

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    /**
     * 生成一个GET请求的分享链接。
     * 失效时间默认是7天。
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param expires    失效时间（以秒为单位），默认是7天，不得大于七天
     * @return
     */
    public  String presignedGetObject(String bucketName, String objectName, Integer expires) {

        boolean bucketExists = isBuckExist(bucketName);
        String url = "";
        if (bucketExists) {
            try {
                if (expires == null){
                    expires = 604800;
                }
                GetPresignedObjectUrlArgs getPresignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucketName)
                        .object(objectName)
                        .expiry(expires)
                        .build();
                url = minioClient.getPresignedObjectUrl(getPresignedObjectUrlArgs);
                log.info("*******url:{}",url);
            } catch (Exception e) {
                log.info("presigned get object fail:{}",e);
            }
        }
        return url;
    }

}

