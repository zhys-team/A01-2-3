package net.micropower.weixin.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class PostUtil {
	
	public static boolean post(String params, String url) {

		StringBuffer bufferRes = new StringBuffer();

		try {

			URL realUrl = new URL(
					url);

			HttpURLConnection conn = (HttpURLConnection) realUrl
					.openConnection();

			// 连接超时

			conn.setConnectTimeout(25000);

			// 读取超时 --服务器响应比较慢,增大时间

			conn.setReadTimeout(25000);

			HttpURLConnection.setFollowRedirects(true);

			// 请求方式

			conn.setRequestMethod("POST");
          
			conn.setDoOutput(true);

			conn.setDoInput(true);

			conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:21.0) Gecko/20100101 Firefox/21.0");

			conn.setRequestProperty("Referer", "https://api.weixin.qq.com/");

			conn.connect();

			// 获取URLConnection对象对应的输出流

			OutputStreamWriter out = new OutputStreamWriter(conn
					.getOutputStream(), "UTF-8");

			// 发送请求参数

			//out.write(URLEncoder.encode(params,"GBK"));

			out.write(params);

			out.flush();

			out.close();

			InputStream in = conn.getInputStream();

			BufferedReader read = new BufferedReader(new InputStreamReader(in,
					"UTF-8"));

			String valueString = null;

			while ((valueString = read.readLine()) != null) {

				bufferRes.append(valueString);

			}

			System.out.println("返回："+bufferRes.toString());
			in.close();

			if (conn != null) {

				// 关闭连接

				conn.disconnect();

			}
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;

		}

	}
	
public static void main1(String[] args) {
	
	String SendUrl="https://nnfpdev.jss.com.cn/shop/buyer/allow/cxfKp/cxfServerKpOrderSync.action"; 
	String order="{\"identity\":\"93363DCC6064869708F1F3C72A0CE72A713A9D425CD50CDE\","    
	+ "\"order\":{\"orderno\":\"No1233.12016101300002\",\"saletaxnum\":\"339901999999142\","    
			+ "\"saleaddress\":\"&*杭州市中河中路 222 号平海国际商务大厦 5 楼 \",\"salephone\":\"0571-87022168\","    
	+ "\"saleaccount\":\"东亚银行杭州分行 131001088303400\",\"clerk\":\"袁牧庆\",\"payee\":\"络克\","    
			+ "\"checker\":\"朱燕\",\"invoicedate\":\"2016-06-15 01:51:41\","     
	+ "\"kptype\":\"1\",\"address\":\"\","     
			+ "\"phone\":\"13185029520\",\"taxnum\":\"110101TRDX8RQU1\",\"buyername\":\" 个人 \",\"account\":\"\","    
	+ "\"fpdm\":\"\",\"fphm\":\"\",\"tsfs\":\"0\",\"email\":\"1173499611@qq.com\",\"message\":\"开票机号为02 请前往诺诺网 (www.jss.com.cn)查询发票详情\","     
			+ "\"qdbz\":\"0\",\"qdxmmc\":\"详见销货清单\","    
	+ "\"detail\":[{\"goodsname\":\"1\",\"spec\":\"1\",\"unit\":\"1\",\"hsbz\":\"1\",\"num\":\"1\","     
			+ "\"price\":\"19.99\","     
	+ "\"spbm\":\"1090511030000000000\",\"fphxz\":\"0\",\"yhzcbs\":\"0\",\"zzstsgl\":\"222222\",\"l slbs\":\"\","     
			+ "\"taxrate\":\"0.16\"},"    
	+ "{\"goodsname\":\"2\",\"spec\":\"2\","     
			+ "\"unit\":\"2\",\"hsbz\":\"1\",\"num\":\"1\",\"price\":\"20\","     
	+ "\"spbm\":\"1090511030000000000\",\"fphxz\":\"0\",\"yhzcbs\":\"0\",\"zzstsgl\":\"222222\",\"l slbs\":\"\","     
			+ "\"taxrate\":\"0.16\"}]}}";   
	order=DESDZFP.encrypt(order);   
	HttpClient httpclient=null;   
	PostMethod post=null;   
	try{    
		httpclient = new HttpClient();    
		post = new PostMethod(SendUrl);    
		//设置编码方式   
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");    
		//添加参数    
		post.addParameter("order",order);    
		//执行    
		httpclient.executeMethod(post);    
		//接口返回信息   
		String info = new String(post.getResponseBody(),"UTF-8");    
		System.out.println(info);   
		}catch (Exception e) {    
			e.printStackTrace();   
			}finally {     
				//关闭连接，释放资源     
				post.releaseConnection();   
			 ((SimpleHttpConnectionManager)httpclient.getHttpConnectionManager()).shutdown();    }
	}


public static void main(String[] args) {
	String SendUrl="https://nnfpdev.jss.com.cn/shop/buyer/allow/ecOd/queryElectricKp.action";   
	String order="{\"identity\":\"93363DCC6064869708F1F3C72A0CE72A713A9D425CD50CDE\",\"fpqqlsh\":[\"18070214571001021613\"]}";   
	order=DESDZFP.encrypt(order);   
	HttpClient httpclient=null;   
	PostMethod post=null;   
	try{    
		httpclient = new HttpClient();    
		post = new PostMethod(SendUrl);    //设置编码方式   
	post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");   
	//添加参数   
	post.addParameter("order",order);    
	//执行    
	httpclient.executeMethod(post);    
	//接口返回信息    
	String info = new String(post.getResponseBody(),"UTF-8");    
	System.out.println(info);   
	}catch (Exception e) {    
		e.printStackTrace();   
		}finally {     
			//关闭连接，释放资源     
			post.releaseConnection();    
			((SimpleHttpConnectionManager)httpclient.getHttpConnectionManager()).shutdown();    
			} 
	
	}
}

