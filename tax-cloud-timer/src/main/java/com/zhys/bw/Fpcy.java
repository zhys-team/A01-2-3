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
	
    
    
    
    
    
    public static Map<String,String> getResult() {
        
        String access_token = "";
        String openid="";
        String sb = getToken();
        Map tempMap = new HashMap();
        try {
            tempMap = XmlMapUtil.xml2Map(sb);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        if (tempMap.get("BODY") != null || !tempMap.get("BODY").equals("")) {
            access_token = ((Map) tempMap.get("BODY")).get("access_token").toString();
        }
        String sb1 = getOpenId(access_token);
        Map tempMap1 = new HashMap();
        try {
            tempMap1 = XmlMapUtil.xml2Map(sb1);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        if (tempMap1.get("BODY") != null || !tempMap1.get("BODY").equals("")) {
            openid = ((Map) tempMap1.get("BODY")).get("openID").toString();
        }
        Map<String,String> m = new HashMap<String,String>();
        m.put("accessToken", access_token);
        m.put("openId", openid);
        return m;
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
