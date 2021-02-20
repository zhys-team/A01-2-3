package com.zhys.bw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtil {

	public static Map<String,Object> post(String url, Map<String, String> params) {
		CloseableHttpClient cilent = HttpClients.createDefault();
		Map<String,Object> reponseMap = new HashMap<String,Object>();
		try {
			HttpPost post = postForm(url, params);
			reponseMap = invoke(cilent, post);		
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				cilent.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return reponseMap;
	}
	
	public static Map<String,Object> get(String url, Map<String, String> params) {
		CloseableHttpClient cilent = HttpClients.createDefault();
		Map<String,Object> reponseMap = new HashMap<String,Object>();
		List<NameValuePair> newParam = new ArrayList<NameValuePair>();
		if(params != null){
			for(String key:params.keySet()){
				newParam.add(new BasicNameValuePair(key, params.get(key))); 
			}
		}
		try {
			String str = EntityUtils.toString(new UrlEncodedFormEntity(newParam, Charsets.UTF_8));
			HttpGet httpGet;
			if(StringUtils.isBlank(str)){
				httpGet = new HttpGet(url);
			}else{
				httpGet = new HttpGet(url+"?"+str);
			}
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(120000).setConnectionRequestTimeout(120000)
					.setSocketTimeout(120000).build();
			httpGet.setConfig(requestConfig);
			reponseMap = invoke(cilent, httpGet);		
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				cilent.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return reponseMap;
	}

	
	private static HttpPost postForm(String url, Map<String, String> params) {

		HttpPost httpost = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(120000).setConnectionRequestTimeout(120000)
				.setSocketTimeout(120000).build();
		httpost.setConfig(requestConfig);

		if(params!=null){
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			Set<String> keySet = params.keySet();
			for (String key : keySet) {
				nvps.add(new BasicNameValuePair(key, params.get(key)));
			}
			httpost.setEntity(new UrlEncodedFormEntity(nvps, Charsets.UTF_8));
		}
		return httpost;
	}

	private static Map<String,Object> invoke(CloseableHttpClient httpclient,
			HttpUriRequest httpost) {

		Map<String,Object> returnMap = new HashMap<String,Object>();
		HttpResponse response = sendRequest(httpclient, httpost);
		String body = paseResponse(response);
		returnMap.put("statusCode", response.getStatusLine().getStatusCode()); // 请求返回结果状态
		returnMap.put("response", body);
		return returnMap;
	}

	private static HttpResponse sendRequest(CloseableHttpClient httpclient,
			HttpUriRequest httpost) {

		HttpResponse response = null;
		try {
			response = httpclient.execute(httpost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	private static String paseResponse(HttpResponse response) {

		HttpEntity entity = response.getEntity();

		String body = null;
		try {
			body = EntityUtils.toString(entity, "UTF-8");
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return body;
	}
	
}
