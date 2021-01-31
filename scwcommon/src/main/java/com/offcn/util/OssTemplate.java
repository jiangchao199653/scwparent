package com.offcn.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class OssTemplate {

    private String endpoint;
    private String bucketDomain;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;


    public String upload(InputStream inputStream,String fileName){
        //1、加工文件夹和文件名
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String folderName = sdf.format(new Date());
        fileName = UUID.randomUUID().toString().replace("-","")+"_"+fileName;
        //2、创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
        //3、// 上传文件流，指定bucket的名称
        ossClient.putObject(bucketName,"pic/"+folderName+"/"+fileName,inputStream);

        //4、关闭资源
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ossClient.shutdown();
        String url= "https://"+bucketDomain+"/pic/"+folderName+"/"+fileName;
        System.out.println("上传文件访问路径:"+url);
        return url;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getBucketDomain() {
        return bucketDomain;
    }

    public void setBucketDomain(String bucketDomain) {
        this.bucketDomain = bucketDomain;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
