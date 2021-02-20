package net.micropower.weixin.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import java.util.List;




public class HttpRequest {

	 public static String sendPost(String url, String param,String referer,String bm) {
	        PrintWriter out = null;
	        BufferedReader in = null;
	        String result = "";
	        try {
	            URL realUrl = new URL(referer);
	            // �򿪺�URL֮���l��
	            URLConnection conn = realUrl.openConnection();
	            // ����ͨ�õ���������
	            conn.setRequestProperty("user-agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:35.0) Gecko/20100101 Firefox/35.0");
	            List<String> cookies = conn.getHeaderFields().get("Set-Cookie");
	            URL u = new URL(url);
	            URLConnection conn1 = u.openConnection();
	            for(String cookie:cookies){
	            	conn1.addRequestProperty("Cookie", cookie.split(";",2)[0]);
	            }
	            CookieHandler.setDefault(new CookieManager(null,CookiePolicy.ACCEPT_ALL));
	            conn1.setRequestProperty("accept", "*/*");
	            conn1.setRequestProperty("connection", "Keep-Alive");
	            conn1.setRequestProperty("Referer", referer);
	            conn1.setRequestProperty("user-agent",
	                    "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:35.0) Gecko/20100101 Firefox/35.0");
	            conn1.setRequestProperty("Accept-Language",
	            "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
	            //conn1.setRequestProperty("Cookie", "JSESSIONID=JRKMJTLwzDJxw5qsKmtLvvsp6W0QjQvXTxrs0pxQMbLNgPngNdkW!1408424434");
	            // ����POST���������������}��
	            conn1.setDoOutput(true);
	            conn1.setDoInput(true);
	            // ��ȡURLConnection�����Ӧ�������
	            out = new PrintWriter(conn1.getOutputStream());
	            // �����������
	            out.print(param);
	            // flush�����Ļ���
	            out.flush();
	            // ����BufferedReader������4��ȡURL����Ӧ
	            in = new BufferedReader(
	                    new InputStreamReader(conn1.getInputStream(),bm));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	            System.out.println("���� POST ��������쳣��"+e);
	            e.printStackTrace();
	        }
	        //ʹ��finally��4�ر������������
	        finally{
	            try{
	                if(out!=null){
	                    out.close();
	                }
	                if(in!=null){
	                    in.close();
	                }
	            }
	            catch(IOException ex){
	                ex.printStackTrace();
	            }
	        }
	        System.out.println(result);
	        return result;
	    }    
	    
	    public static void main(String[] args) {
	    	String sr=HttpRequest.sendPost("http://www.zjtax.gov.cn/fpcx/include2/fpcy_jg_lscx.jsp", "ywlx=FPCX_WLFP&ywlxbf=FPCX_WLFP&anbz=wqr&cxbz=lscx&fpdm=&fphm=&kjfsbh_qp=&rq_qp=&je_qp=&zjlsh=3350234251144720&rq_wp=20150701&je_wp=27080","http://www.zjtax.gov.cn/fpcx/include2/wlfpcybd_lscx.jsp","gbk");
//			Document d = Jsoup.parse(sr, "utf-8");
//			Element e = d.body();
//			String s = d.select("thead").get(0).select("td").html();
//			System.out.println(s);
			
//	    	try {
//				HttpRequest.sendSms();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
	    }  
	    
	    public static void sendSms() throws Exception{
	    	BufferedReader in = null;
	    	String result = "";
	        String path ="http://www.zjtax.gov.cn/fpcx/include2/wlfpcybd_lscx.jsp";
	        URL url =new URL(path);
	        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	        conn.setConnectTimeout(5*1000);
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("user-agent",
            "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:35.0) Gecko/20100101 Firefox/35.0");
	        InputStream inStream = conn.getInputStream();    
	        in = new BufferedReader(
                    new InputStreamReader(inStream,"gbk"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            System.out.println(result);
	    }
    
    
}