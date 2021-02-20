package top.anets.utils.guoXin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.annotation.PostConstruct;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.transport.http.CommonsHttpMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import top.anets.exception.ServiceException;

/**
 * 请求工具类
 * 
 * @author yuanxiaojun
 *
 */

@Component
@Slf4j 
public class RequestUtils {
	private static SSLContext sslContext;
	
	@Value("${guoXin.KEY_STORE_FILE}")
	private   String KEY_STORE_FILE1; 
	
	@Value("${guoXin.KEY_STORE_PASS}")
	private   String KEY_STORE_PASS1; 
	
	
	
	 @Autowired
	private ApplicationContext appContext;

	     
	@PostConstruct
	public void init() {
		RequestUtils.KEY_STORE_FILE=this.KEY_STORE_FILE1;
		RequestUtils.KEY_STORE_PASS=this.KEY_STORE_PASS1;
		
		if(StringUtils.isBlank(KEY_STORE_FILE)||StringUtils.isBlank(KEY_STORE_PASS)) {
			log.error("初始化失败:KEY_STORE_FILE或者KEY_STORE_PASS没有配置，请在setting.properties中配置");
		} 
		File file = new File(KEY_STORE_FILE); 
		if(!file.exists()) {
			log.info(KEY_STORE_FILE);
			log.error("初始化失败:根据KEY_STORE_FILE配置的路径没有找到这个文件");
//			终止程序
//			SpringApplication.exit(appContext, () -> -1);
			throw new ServiceException("初始化失败:根据KEY_STORE_FILE配置的路径没有找到这个文件");
		}
		
	}
	
//	public static String KEY_STORE_FILE = "F:\\zhys\\客户\\涂鸦-电子发票核心板项目\\文档\\服务器版企业接口规范20200701\\服务器版企业接口规范\\证书\\testISSUE.pfx";
//	public static String KEY_STORE_PASS = "123456";
	public static String KEY_STORE_FILE = null;
	public static String KEY_STORE_PASS = null;


	/**
	 * https post请求方式
	 * 
	 * @param xml
	 * @param address
	 * @return
	 */
	public static String getHttpConnectResult(String xml, String address) {
		String resultData = "";
		System.out.println("http请求开始，请求地址：" + address);
		OutputStream wr = null;
		HttpsURLConnection conn = null;
		try {
			URL url = new URL(address);
			conn = (HttpsURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(3000);// 设置连接主机的超时时间
			conn.setReadTimeout(3000);// 设置从主机读取数据的超时时间

			// 打开和URL之间的连接
			if (conn instanceof HttpsURLConnection) {
				((HttpsURLConnection) conn).setSSLSocketFactory(getSSLContext().getSocketFactory());
			}
			wr = conn.getOutputStream();
			wr.write(xml.getBytes("utf-8"));
			wr.flush();
			resultData = IOUtils.toString(conn.getInputStream(), "utf-8");
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.out.println("http请求失败！请求地址不正确！请求地址：" + address);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("http请求失败！发生i/o错误，请求地址：" + address);
		} finally {
			try {
				if (wr != null) {
					wr.close();
				}
				if (conn != null) {
					conn.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("请求结果:"+resultData);
		return resultData;
	}

	/**
	 * 使用xfile的webserivce
	 * 
	 * @param xml
	 * @param method
	 * @param address
	 * @return
	 */
	public static String webServiceXfile(String requestData, String method, String address) {
		String val = "";
		try {
			System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
			System.setProperty("javax.net.ssl.keyStore", KEY_STORE_FILE);
			System.setProperty("javax.net.ssl.keyStorePassword", KEY_STORE_PASS);
			Object[] params = new Object[] { requestData, Boolean.TRUE };
			Object[] result = { "" };
			Client client = null;
			String timeOut = "6000";
			client = new Client(new URL(address));
			client.setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, timeOut);
			result = client.invoke(method, params);
			val = result[0].toString();
		} catch (Exception e) {
			e.printStackTrace();
			val = e.getLocalizedMessage();
		}

		return val;
	}

	/**
	 * 使用axis的webserivce
	 * 
	 * @param xml
	 * @param method
	 * @param address
	 * @return
	 */
	public static String webServiceAxis(String requestData, String method, String address) {
		Call call;
		String val = "";
		try {
			 System.setProperty("javax.net.ssl.keyStoreType","PKCS12") ;
			 System.setProperty("javax.net.ssl.keyStore",KEY_STORE_FILE);
			 System.setProperty("javax.net.ssl.keyStorePassword",KEY_STORE_PASS) ;
			 Service s = new Service();
			 call = (Call) s.createCall();
			 call.setTargetEndpointAddress(new java.net.URL(address));
			 call.setOperation(method);
			 Object[] fn01 = { requestData };
			 val = (String) call.invoke(fn01);
		} catch (Exception e) {
			val = e.getMessage();// 返回错误信息
		}
		return val;
	}

	public static SSLContext getSSLContext() {
		if (sslContext == null) {
			try {
				KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
				kmf.init(getkeyStore(), KEY_STORE_PASS.toCharArray());
				KeyManager[] keyManagers = kmf.getKeyManagers();
				sslContext=SSLContext.getInstance("SSL");
				TrustManager[] trustManagers={new MyX509TrustManager()};
				sslContext = SSLContext.getInstance("TLSv1.2");
				sslContext.init(keyManagers, trustManagers, new SecureRandom());
				HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
					public boolean verify(String hostname, SSLSession session) {
						return true;
					}
				});
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnrecoverableKeyException e) {
				e.printStackTrace();
			} catch (KeyStoreException e) {
				e.printStackTrace();
			} catch (KeyManagementException e) {
				e.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return sslContext;
	}

	public static KeyStore getkeyStore() {
		KeyStore keySotre = null;
		try {
			keySotre = KeyStore.getInstance("PKCS12");
			FileInputStream fis = new FileInputStream(new File(KEY_STORE_FILE));
			keySotre.load(fis, KEY_STORE_PASS.toCharArray());
			fis.close();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return keySotre;
	}
}
