package com.zhys.hh;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

public class OCRTest {

	/**
	 * @param args
	 */
	
	public static void main1(String[] args) {
		// TODO Auto-generated method stub
		String filenamePathString = "C:/Users/11734/Desktop/111.jpg";
		// 将上传的文件转化为 base64字符串
		File file = new File(filenamePathString);
		FileInputStream inputFile;
		byte[] buffer = null;
		try {
			inputFile = new FileInputStream(file);

			buffer = new byte[(int) file.length()];
			inputFile.read(buffer);
			inputFile.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String base64FileString = Base64.getEncoder().encodeToString(buffer);
//
//
//		//String url = "http://192.168.135.129:9023/icr/recognize_table";
		//String url = "https://api-intcloud.camcard.com/recognize/ocr/ocr_data";
		String url = "https://api-intcloud.camcard.com/recognize/ocr";
//		// 将post的参数 转成json字符

		
		Map<String,String> map=new HashMap<String ,String >();
		map.put("user", "zhonghanyunshui ");//用户名
		map.put("api_key", "zhonghanyunshui ");//密码
		map.put("recognize_service", "huochepiao");//id  fox_ocr huochepiao

		map.put("image_data", base64FileString);//image data
		//map.put("task_type", "image");//image or pdf

		

		String dataString = new Gson().toJson(map);

		
		// 转成 ResultOfReportAll 带error_code 和error_msg的对象
		//System.out.println("传入的参数："+dataString);
		System.out.println("正在上传数据调用接口，请稍等");

		String resultOfReportAll = IcrRecognizeHttpUtil.sendPostPath(
				url, dataString);
	//System.out.println(resultOfReportAll);

	}
	
	
	public static JSONObject fp(byte[] bytes) throws IOException {
		
		String url = "https://sh-imgs-sandbox.intsig.net/icr/recognize_vat_invoice";
		byte [] res= new HttpHelper().post(url, bytes);
		JSONObject jsonObject = JSONObject.parseObject(new String (res,"utf-8"));
		return jsonObject;
	}
	
      public static JSONObject pj(byte[] bytes) throws IOException {
		
    	  String base64FileString = Base64.getEncoder().encodeToString(bytes);
    	  		String url = "https://api-intcloud.camcard.com/recognize/ocr";
    	  		Map<String,String> map=new HashMap<String ,String >();
    	  		map.put("user", "zhonghanyunshui ");//用户名
    	  		map.put("api_key", "zhonghanyunshui ");//密码
    	  		map.put("recognize_service", "huochepiao");//id  fox_ocr huochepiao
    	  		map.put("image_data", base64FileString);//image data
    	  		String dataString = new Gson().toJson(map);
    	  		// 转成 ResultOfReportAll 带error_code 和error_msg的对象
    	  		//System.out.println("传入的参数："+dataString);
    	  		System.out.println("正在上传数据调用接口，请稍等");
    	  		String resultOfReportAll = IcrRecognizeHttpUtil.sendPostPath(
    	  				url, dataString);
    	  	return JSONObject.parseObject(resultOfReportAll);
	}
	
	public static void main2(String[] args) throws IOException {
		//这个地方根据自己的实际情况做更改，比如你自己的IP地址，以及你在tomcat中的工程部署
		//这里的地址要和web.xml当中的设置匹配
		String url = "https://sh-imgs-sandbox.intsig.net/icr/recognize_vat_invoice";
		
//		String result = doPost(url);
//		System.out.println(result);
		
		byte [] res= new HttpHelper().post(url, toByteArray("C:/Users/11734/Desktop/4444.png"));
		System.out.println(new String (res,"utf-8"));
		JSONObject jsonObject = JSONObject.parseObject(new String (res,"utf-8"));
		System.out.println(jsonObject.get("vat_invoice_issue_date"));
	}
		
	public static String doPost(String urlString)
			throws IOException {
		URL url = new URL(urlString);
		URLConnection connection = url.openConnection();
		connection.setDoOutput(true);
		connection.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 6.2; " +
				"WOW64; rv:20.0) ");
		//try里面拿到输出流，输出端就是服务器端
		try (BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream())) {
			
			//我java代码是在Windows上运行的，图片路径就是下面这个			
			InputStream is = new FileInputStream("C:/Users/11734/Desktop/4444.png");
			BufferedInputStream bis = new BufferedInputStream(is);
			
			byte[] buf= new byte[1024];
			int length = 0;
			length = bis.read(buf);
			while(length!=-1) {
				bos.write(buf, 0, length);
				length = bis.read(buf);
			}
			
			//下面是服务器端如果有返回数据的话，做接收用的
			BufferedReader br = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "UTF-8"));
			String line;
			String returninfo = null;
			String resultOfReport = null;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				returninfo = line;
				sb.append(line);
				System.out.print(returninfo);
			}
			System.out.println("\n");

			resultOfReport = sb.toString();
			
			bis.close();
			is.close();
			bos.close();
			return resultOfReport;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
		
 
		
	}
	
	
	public static byte[] toByteArray(String filename) throws IOException {
		 
        File f = new File(filename);
        if (!f.exists()) {
            throw new FileNotFoundException(filename);
        }
 
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bos.close();
        }
    }

}
