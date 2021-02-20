package net.micropower.weixin.util;

import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
import org.apache.commons.httpclient.HttpClient;  
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;  
import org.apache.commons.httpclient.params.HttpMethodParams;  
  
public class SimpleClient {  
	
	public static String sendPost(String sfzh,String key) throws HttpException, IOException { 
        HttpClient client = new HttpClient();     
        PostMethod method = new PostMethod("http://10.120.193.12/webserver/jxczrk.ashx");   
       
          
       
         ((PostMethod) method).addParameter("data", "sfzh="+sfzh+"&key="+key);  
           
        HttpMethodParams param = method.getParams();  
        param.setContentCharset("UTF-8");  
       
     client.executeMethod(method);  
        //打印服务器返回的状态  
     System.out.println(method.getStatusLine());  
       //打印返回的信息  
     System.out.println();  
     InputStream stream = method.getResponseBodyAsStream();  
       
     BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));  
     StringBuffer buf = new StringBuffer();  
     String line;  
     while (null != (line = br.readLine())) {  
         buf.append(line).append("\n");  
     }  
   //释放连接  
     method.releaseConnection();
     return buf.toString();  
         
    }  
}  