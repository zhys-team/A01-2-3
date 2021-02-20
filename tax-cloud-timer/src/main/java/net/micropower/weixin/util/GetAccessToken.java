package net.micropower.weixin.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import lombok.extern.java.Log;


/**
 * @author li.hui
 *
 */
@Log
public class GetAccessToken
{
	
	
	/**
	 * 发送get请求获得access_token
	 * 
	 * @param url
	 *            请求路径
	 * @return access_token
	 */
	public static String getAccessToken(String accessTokenUrl) {
		HttpClient client = new HttpClient();
		GetMethod get = new GetMethod(accessTokenUrl);
		get.getParams().setContentCharset("utf-8");
		// 发送http请求
		String replyData = null;
		try {
			client.executeMethod(get);
			replyData = get.getResponseBodyAsString().split(",")[0].split(":")[1].split("\"")[1];
			log.info("获取access_token成功。");
		} catch (Exception e) {
			log.info("获取access_token失败："+e.getMessage());
			e.printStackTrace();
		}
		return replyData;

	}
}
