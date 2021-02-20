package net.micropower.weixin.util;

import java.io.IOException;
import java.util.Properties;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author li.hui
 *
 */
public class GetAccessToken
{
	private static String accessTokenUrl;
	private static final Logger logger = LoggerFactory.getLogger(GetAccessToken.class);
	static {
		Properties pro = new Properties();
		try {
			pro.load(GetAccessToken.class.getClassLoader().getResourceAsStream(
					"wechat.properties"));
			accessTokenUrl = pro.getProperty("accessTokenUrl");
			
			logger.info("获取配置文件成功--weichat.properties");
		} catch (IOException e) {
			logger.error("获取配置文件失败-weichat.properties");
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送get请求获得access_token
	 * 
	 * @param url
	 *            请求路径
	 * @return access_token
	 */
	public static String getAccessToken() {
		HttpClient client = new HttpClient();
		GetMethod get = new GetMethod(accessTokenUrl);
		get.getParams().setContentCharset("utf-8");
		// 发送http请求
		String replyData = null;
		try {
			client.executeMethod(get);
			replyData = get.getResponseBodyAsString().split(",")[0].split(":")[1].split("\"")[1];
			logger.info("获取access_token成功。");
		} catch (Exception e) {
			logger.error("获取access_token失败："+e.getMessage());
			e.printStackTrace();
		}
		return replyData;

	}
	public static void main(String[] args)
	{
		System.out.println(getAccessToken());
//System.out.println(URLEncoder.encode("http://www.hzwgkj.com/mallapp/messageAction/invoice.action"));
	}
}
