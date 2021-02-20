package com.zhys.bw;



import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

//import org.junit.Test;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Util {
	/**
	 * 解码
	 * @param requestString
	 * @return
	 * @throws IOException
	 */
	public static byte[] base64Decoder(String requestString) throws IOException{
		return new BASE64Decoder().decodeBuffer(requestString);
	}

	/*
	 * 编码
	 */
	public static String base64encoder(byte[] bytes) throws IOException{
		BASE64Encoder enc = new BASE64Encoder();
		String encStr =enc.encode(bytes);
		return encStr;
	}
	// 加密  
    public static String getBase64(String str) {  
        byte[] b = null;  
        String s = null;  
        try {  
            b = str.getBytes("utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        if (b != null) {  
            s = new BASE64Encoder().encode(b);  
        }  
        return s;  
    }  
  
    // 解密  
    public static String getFromBase64(String s) {  
        byte[] b = null;  
        String result = null;  
        if (s != null) {  
            BASE64Decoder decoder = new BASE64Decoder();  
            try {  
                b = decoder.decodeBuffer(s);  
                result = new String(b, "utf-8");  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return result;  
    }  
}
