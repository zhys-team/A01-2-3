package net.micropower.weixin.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MenuTest {
	public static void createMenu(String params, String accessToken) {

		StringBuffer bufferRes = new StringBuffer();

		try {

			URL realUrl = new URL(
					"http://api.weixin.qq.com/cgi-bin/menu/create?access_token="
							+ accessToken);

			HttpURLConnection conn = (HttpURLConnection) realUrl
					.openConnection();

			// 连接超时

			conn.setConnectTimeout(25000);

			// 读取超时 --服务器响应比较慢,增大时间

			conn.setReadTimeout(25000);

			HttpURLConnection.setFollowRedirects(true);

			// 请求方式

			conn.setRequestMethod("GET");

			conn.setDoOutput(true);

			conn.setDoInput(true);

			conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:21.0) Gecko/20100101 Firefox/21.0");

			conn.setRequestProperty("Referer", "https://api.weixin.qq.com/");

			conn.connect();

			// 获取URLConnection对象对应的输出流

			OutputStreamWriter out = new OutputStreamWriter(conn
					.getOutputStream());

			// 发送请求参数

			out.write(URLEncoder.encode(params,"UTF-8"));

			//out.write(params);

			out.flush();

			out.close();

			InputStream in = conn.getInputStream();

			BufferedReader read = new BufferedReader(new InputStreamReader(in,
					"UTF-8"));

			String valueString = null;

			while ((valueString = read.readLine()) != null) {

				bufferRes.append(valueString);

			}

			System.out.println(bufferRes.toString());

			in.close();

			if (conn != null) {

				// 关闭连接

				conn.disconnect();

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	/**
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		/**
		 * 取得accessToken的url
		 * https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx0dcf0b4690cc4ace&secret=52c02252337f804c76a0278eb584e1ed
		 */
		
		String s = "{\"button\":[" +
				                  "{\"name\":\"工作笔记\",\"sub_button\":[" +
				                                                           "{\"type\":\"click\",\"name\":\"录入笔记\",\"key\":\"【录入笔记】\"}," +
				                                                           "{\"type\":\"click\",\"name\":\"我的笔记\",\"key\":\"【我的笔记】\"}," +
				                                                           "{\"type\":\"click\",\"name\":\"笔记查询\",\"key\":\"【笔记查询】\"}," +
				                                                           "{\"type\":\"click\",\"name\":\"我的信息\",\"key\":\"【我的信息】\"}," +
				                                                           "{\"type\":\"click\",\"name\":\"通讯录\",\"key\":\"【通讯录】\"}" +
				                                                      "]}," +
//				                  "{\"name\":\"关于云灵\",\"sub_button\":[" +
//				                                                          "{\"type\":\"view\",\"name\":\"公司简介\",\"url\":\"http://dev.yunling.net/yl-note-wap/page/companyProfile.jsf\"}" +
//				                                                      "]}," +
				                  "{\"name\":\"关于微工\",\"sub_button\":[" +
				                                                          "{\"type\":\"view\",\"name\":\"公司简介\",\"url\":\"http://www.hzwgkj.com/html/AboutAMT/AMTGroup/\"}" +
				                                                      "]}" +
				              "]" +
				  "}";
		
//		String s = "{\"button\":[" +
//        
//        "{\"name\":\"鼓童\",\"sub_button\":[" +
//                                                "{\"type\":\"view\",\"name\":\"首页\",\"url\":\"http://dahuisir.nat123.net/school/index.jsp\"}" +
//                                            "]}" +
//    "]" +
//"}";

		String accessToken = "SY5nlqws2UFaUkKTg4rC5YdVz3VXnAMcmzfkjOADIBygDu8cnEjs7MByJco1fuDWp687oItvUCBR0OFR5gwv0ZE_BywciKz_X_ItH4ux2_aPwthWyu3e61S9DApArLYQIQBjAFAXFQ";// 你自己的token

		createMenu(s, accessToken);

	}

}
