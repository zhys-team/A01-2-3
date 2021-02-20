package com.zhys.rq;
import okhttp3.FormBody; 
import okhttp3.OkHttpClient; 
import okhttp3.Request; 
import okhttp3.Response; 
import org.apache.shiro.crypto.hash.Md5Hash;

import com.zhys.utils.Encoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.util.Base64;
import java.util.concurrent.TimeUnit; 
public class InvoiceRecognizeUtils {
	


	    public static String recognize(byte [] image_file) { 

	    	try {
				
	        String appKey = "5c6a35a1"; //这里输入提供的app_key  
	        String appSecret = "6d6a110eece5517abc10dcf856ba1534"; //这里输入提供的app_secret    

	        //String imageUrl = "http://fapiao.glority.cn/dist/img/sample.jpg";  
	        //String host = "http://fapiao.glority.cn/v1/item/get_item_info"; 
	        String host = "http://fapiao.glority.cn/v1/item/get_multiple_items_info";
	        long timestamp = System.currentTimeMillis() / 1000; 
	        String token = new Md5Hash(appKey + "+" + timestamp + "+" + appSecret).toString(); 

	        //OkHttpClient client = new OkHttpClient(); 
	        OkHttpClient client = new OkHttpClient.Builder()
	                .connectTimeout(60 * 1000, TimeUnit.MILLISECONDS)
	                .readTimeout(5 * 60 * 1000, TimeUnit.MILLISECONDS)
	                .writeTimeout(5 * 60 * 1000, TimeUnit.MILLISECONDS)
	                .build();

	        Request request = new Request.Builder() 
	                .url(host) 
	                .post(new FormBody.Builder() 
	                        .add("app_key", appKey) 
	                        .add("timestamp", String.valueOf(timestamp)) 
	                        .add("token", token) 
	                        //.add("image_url", imageUrl) 
	                        .add("image_data", Encoder.base64Encoding(image_file))
	                        .build())
	                .build(); 

	        Response response = client.newCall(request).execute(); 
	        //System.out.println(response.body().string()); 
	        return response.body().string();
	    	} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
	    } 
	    
	    public static void main(String[] args) {
	    	FileInputStream fis;
	    	ByteArrayOutputStream baos = null;
			try {
				fis = new FileInputStream(new File("D:\\1586222204374.png"));
				baos = new ByteArrayOutputStream();  
				byte[] buffer = new byte[1024];  
				int len;  
				while ((len = fis.read(buffer)) > -1 ) {  
				    baos.write(buffer, 0, len);  
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			String s = recognize(baos.toByteArray());
			System.out.println(s);
	    	
		}

}
