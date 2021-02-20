package net.micropower.weixin.util;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.zhys.base.BaseRedisService;

import java.util.Map;
import java.util.HashMap;
import java.util.Formatter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;

public class sign {

    public static Map<String, String> setSign(String jsapi_ticket,String url) {

        // 注意 URL 一定要动态获取，不能 hardcode
        //String url = "http://ztcw.zjzhongtian.com/baojia/views/wechat/invoice.jsp";
        Map<String, String> ret = sign(jsapi_ticket, url);
        for (Map.Entry entry : ret.entrySet()) {
            System.out.println(entry.getKey() + "：" + entry.getValue());
        }
        Map<String, String> map = new HashMap<String,String>();
        map.put("timestamp", ret.get("timestamp"));
        map.put("nonceStr", ret.get("nonceStr"));
        map.put("signature", ret.get("signature"));
        return map;
	}
    
    public static Map<String, String> getTicket(String accessToken,String url) {
    	String re = HttpGetUtil.get("https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token="+accessToken);
    	JSONObject js = JSONObject.parseObject(re);
    	String ticket = js.getString("ticket");
    	return setSign(ticket,url);
    }
    
    public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
        System.out.println(string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}
