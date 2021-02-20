/**
 * 
 */
package top.anets.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Administrator
 *
 */ 
@Slf4j
public class Xuanji {
	/**
	 * 电子发票
	 */
	public  static String InvoiceType_electron="026";
	/**
	 * 专用发票
	 */
	public  static String InvoiceType_standard="004";
	/**
	 * 普通发票
	 */
	public  static String InvoiceType_normal="007";
	
	/**
	 * 普通请求的xml转换
	* 
	*@param method
	*@param map
	*@return
	 */
	public static String toXml(String method,Map<String , String > map) {
		if(map ==null) {
        	return null;
        }
        String prefix ="<?xml version=\"1.0\" encoding=\"utf-8\"?><business id=\""+method+"\"><body><input>";
        String suffix ="</input></body></business>";
        String body = CommonUtils.map2Xml(map);
        String xml = prefix+body+suffix;
		return xml;
	}
	
	/**
	 * 开票请求的xml转换
	* 
	*@param method
	*@param map
	*@return
	 */
	public static String toXmlForInvoice(String method,Map<String,String> invoiceHeadData,List<Map<String,String>> linesData) {
		if(linesData==null||invoiceHeadData==null) {
        	return null;
        }
        String prefix ="<?xml version=\"1.0\" encoding=\"utf-8\"?><business id=\""+method+"\"><body><input>";
        String suffix ="</input></body></business>";
        String body = CommonUtils.map2Xml(invoiceHeadData);
        
        String lines = "<fyxm count=\""+linesData.size()+"\">";
        for (int i=0;i<linesData.size();i++) {
			String linePrefix ="<group xh=\""+(i+1)+"\">";
			String lineBody =CommonUtils.map2Xml(linesData.get(i));
			String lineSuffix ="</group>";
			
			lines+=(linePrefix+lineBody+lineSuffix);
		}
        lines+="</fyxm>";
        
        
        String xml = prefix+body+lines+suffix;
		return xml;
	}
	
	/**
	 * 
	* 请求
	*@param reqXml
	*@param reqPath
	*@param reqMethod  | POST / GET
	*@throws Exception
	 */
	public static String request(String  reqXml,String reqPath,String reqMethod) throws Exception {
		URL reqURL = new URL(reqPath);
		HttpURLConnection conn   = (HttpURLConnection) reqURL.openConnection();
		conn.setRequestMethod(reqMethod);
		//设置发送数据
		conn.setDoOutput(true);
		conn.setRequestProperty("Accept", "text/plain");
		log.info("请求报文：[" + reqXml + "]");
		reqXml = reqXml.replaceAll("\\r", "").replaceAll("\\n", "");
		reqXml = Base64.base64encode(reqXml, "utf-8");
		
		byte[] bytes = reqXml.getBytes("UTF-8");
		conn.getOutputStream().write(bytes);
		
		InputStream resInput = conn.getInputStream();
		 //定义字节数组
        byte[] b = new byte[1024];
        
        //定义一个输出流存储接收到的数据
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        
        //开始接收数据
        int len = 0;
        while (true) {
            len = resInput.read(b);
            if (len == -1) {
                //数据读完
                break;
            }
            byteArrayOutputStream.write(b, 0, len);
        }
        //从输出流中获取读取到数据(服务端返回的)
        String response = byteArrayOutputStream.toString();
        String result=Base64.base64decode(response, "utf-8");
		log.info("请求结果：[" + result + "]");
        return result;
	}
	
	public static String postRequest(String  reqXml,String reqPath) throws Exception{
		return Xuanji.request(reqXml, reqPath, "POST");
	}
	public static String getRequest(String  reqXml,String reqPath) throws Exception{
		return Xuanji.request(reqXml, reqPath, "GET");
	}
	
}


