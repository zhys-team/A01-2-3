/**
 * Project Name:backgroundManagementSystem
 * File Name:HttpGetUtil.java
 * Package Name:com.micropower.common
 * Date:Jun 12, 20166:30:34 PM
 * Copyright (c) 2016, 1173499611@qq.com All Rights Reserved.
 *
*/

package com.zhys.utils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;




/**
 * ClassName:HttpGetUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     Jun 12, 2016 6:30:34 PM <br/>
 * @author   lihui
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class HttpGetUtil {
	
	public static String get(String url) {
		HttpClient client = new HttpClient();
		GetMethod get = new GetMethod(url);
		get.getParams().setContentCharset("utf-8");
		// 发送http请求
		String replyData = null;
		try {
			client.executeMethod(get);
			replyData = get.getResponseBodyAsString();
			System.out.println(Encoder.decodeUnicode(replyData));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Encoder.decodeUnicode(replyData);

	}
//	public static void main(String[] args) {
//		String url;
//		try {
//			url = URLEncoder.encode("[{\"tranNo\": \"kz2016061716330226\",\"totalFee\": \"1\",\"refundFee\": \"1\"}]","utf-8");
//			String s = get("http://wxdev.51kzkz.com/wxpay/refund?data="+url);
//		} catch (UnsupportedEncodingException e) {
//			
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			
//		}
//		//JSONObject js = new JSONObject(s);
//		//System.out.println(js.get("code"));
//	}
	

}

