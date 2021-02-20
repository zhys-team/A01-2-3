package com.zhys.bw;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.dom4j.DocumentException;


public class Fpcy {

	private static String client_id = "152877251001";
    private static String client_secret = "b8c37e33defde51cf91e1e03e51657da";
    private static String sqm = "1c65cef3dfd1e00c0b03923a1c591db4";
    private static String Client = "杭州中航云税科技有限公司"; //公司名称
    private static String GTAXID = "91330105MA2B0AD277";//公司税号
	
    public static void main(String[] args) {
    	testFpcy("","","","","","","");
	}
    
    public static String testFpcy(String InvoiceCode,String InvoiceNumber,String BillingDate,String TotalAmount,String CheckCode_6,String accessToken,String openId) {
        //Map<String, String> mapp = getResult();//access_token 与 openID 存在缓存，可以失效时在调用此方法获取
        Map<String, Object> f1 = new HashMap<String, Object>();
        Map<String, Object> f2 = new HashMap<String, Object>();
        Map<String, Object> f4 = new HashMap<String, Object>();
        f4.put("Client", Client);
        f4.put("Time", new Date());
        f4.put("GTAXID", GTAXID);
        Map<String, String> invMap1 = new HashMap<String, String>();

        invMap1.put("InvoiceCode", InvoiceCode);
        invMap1.put("InvoiceNumber",InvoiceNumber );
        invMap1.put("BillingDate", BillingDate);
        invMap1.put("TotalAmount", TotalAmount);
        invMap1.put("CheckCode_6", CheckCode_6);

        f2.put("BODY", invMap1);
        f2.put("HEAD", f4);
        f1.put("REQUEST", f2);

        String url = "https://www.fapiao.com/Entoauth/thirdApi";
        Map<String, String> params = new HashMap<String, String>();
        params.put("sign", "getSecondAllQuery");
        params.put("client_id", client_id);
        params.put("sqm", sqm);
        params.put("dataType", "0");
        params.put("busiType", "vat_income");
        params.put("access_token", accessToken);
        params.put("openID", openId);
        params.put("data", Base64Util.getBase64(JSONObject.fromObject(f1).toString()));
        
        Map<String, Object> str = HttpUtil.post(url, params);
        String result = str.get("response").toString();
        //System.out.println(params);
        System.out.println("=============" + result);
        return result;
    }
    
    
    public static Map<String, String> getResult() {
        String access_token = "3BC20C950BCD06B6F89A78D93C219E31";
        String openid = "356E4E8DC8562AB661386FC4E29504F4";
        Map<String, String> result = new HashMap<String, String>();
        result.put("accessToken", access_token);
        result.put("openId", openid);
        
//        String sb = getToken();
//        Map tempMap = new HashMap();
//        try {
//            tempMap = XmlMapUtil.xml2Map(sb);
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//        if (tempMap.get("BODY") != null || !tempMap.get("BODY").equals("")) {
//            access_token = ((Map) tempMap.get("BODY")).get("access_token").toString();
//            result.put("accessToken", access_token);
//        }
//        String sb1 = getOpenId(access_token);
//        Map tempMap1 = new HashMap();
//        try {
//            tempMap1 = XmlMapUtil.xml2Map(sb1);
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//        if (tempMap1.get("BODY") != null || !tempMap1.get("BODY").equals("")) {
//            openid = ((Map) tempMap1.get("BODY")).get("openID").toString();
//            result.put("openId", openid);
//        }
        return result;
    }
    
    public static String getToken() {
        String url = "https://www.fapiao.com/Entoauth/TokenAction?sign=token" + "&client_id="
                + client_id + "&client_secret=" + client_secret;
        String str = HttpUtil.get(url, null).get("response").toString();
        System.out.println("token:"+str);
        return str;
    }

    public static String getOpenId(String accessToken) {
        String url = "https://www.fapiao.com/Entoauth/OpenAction?sign=open" + "&client_id="
                + client_id + "&access_token=" + accessToken;
        String str = HttpUtil.get(url, null).get("response").toString();
        System.out.println("openid:"+str);
        return str;
    }
    
}
