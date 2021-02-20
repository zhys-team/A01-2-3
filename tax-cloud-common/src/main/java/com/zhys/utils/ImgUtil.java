package com.zhys.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.StorageClass;
import com.qcloud.cos.region.Region;

public class ImgUtil {
	public static void main1(String[] args) throws FileNotFoundException {
//		// 1 初始化用户身份信息(secretId, secretKey)
//		COSCredentials cred = new BasicCOSCredentials("AKIDRo01yoR0Obhmv4xyeBi8OTOBqLjx894o", " lMnCgEKI4f4ILDtQHzW0JQK2Zx99zeAj");
//		// 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
//		ClientConfig clientConfig = new ClientConfig(new Region("ap-shanghai"));
//		// 3 生成cos客户端
//		COSClient cosclient = new COSClient(cred, clientConfig);
//		// bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
//		String bucketName = "huisircos-1257168761";
//		
//		// 简单文件上传, 最大支持 5 GB, 适用于小文件上传, 建议 20M以下的文件使用该接口
//		// 大文件上传请参照 API 文档高级 API 上传
//		File localFile = new File("C:\\Users\\11734\\Desktop\\4444.png");
//		// 指定要上传到 COS 上对象键
//		// 对象键（Key）是对象在存储桶中的唯一标识。例如，在对象的访问域名 `bucket1-1250000000.cos.ap-guangzhou.myqcloud.com/doc1/pic1.jpg` 中，对象键为 doc1/pic1.jpg, 详情参考 [对象键](https://cloud.tencent.com/document/product/436/13324)
//		String key = "444.png";
//		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
//		PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);
//        System.out.println("");
        
     // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials("AKIDRo01yoR0Obhmv4xyeBi8OTOBqLjx894o", "lMnCgEKI4f4ILDtQHzW0JQK2Zx99zeAj");
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-shanghai"));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        // bucket名需包含appid
        String bucketName = "huisircos-1257168761";
        
        String key = "4449.png";
        File localFile = new File("C:\\\\Users\\\\11734\\\\Desktop\\\\4444.png");
        InputStream input = new FileInputStream(localFile);//new ByteArrayInputStream(new byte[10]);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        // 从输入流上传必须制定content length, 否则http客户端可能会缓存所有数据，存在内存OOM的情况
        objectMetadata.setContentLength(localFile.length());
        // 默认下载时根据cos路径key的后缀返回响应的contenttype, 上传时设置contenttype会覆盖默认值
        objectMetadata.setContentType("image/jpeg");
        
        PutObjectRequest putObjectRequest =
                new PutObjectRequest(bucketName, key, input, objectMetadata);
        //PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        // 设置存储类型, 默认是标准(Standard), 低频(standard_ia)
        putObjectRequest.setStorageClass(StorageClass.Standard);
        try {
            PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);
            // putobjectResult会返回文件的etag
            String etag = putObjectResult.getETag();
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }
        
        // 关闭客户端
        cosclient.shutdown();
        
		Date expiration = new Date(new Date().getTime() + 1000*60*60*24*365*100);
		URL url = cosclient.generatePresignedUrl(bucketName, key, expiration);
		System.out.println(url.toString());

	}
}
