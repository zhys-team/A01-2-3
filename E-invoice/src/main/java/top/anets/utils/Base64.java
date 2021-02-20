package top.anets.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**  
 * @author : zhoubin 
 * @date : 2013-12-10
 * @version ：
 */
public class Base64 {
	
	private static Logger LOG = Logger.getLogger(Base64.class);
	
	/**         
     * BASE64加密   
   * @param key          
     * @return          
     * @throws Exception          
     */              
    public static final String encryptBASE64(byte[] key) throws Exception {
        //return (new BASE64Encoder()).encodeBuffer(key);    
    	return (new BASE64Encoder()).encode(key);
    }
    
    public static final String base64encode(String str,String charset) throws UnsupportedEncodingException{
    	return new String(org.apache.commons.codec.binary.Base64.encodeBase64(str.getBytes("UTF-8")),charset);
    }
    
    public static final String base64decode(String str,String charset) throws UnsupportedEncodingException{
    	return new String(org.apache.commons.codec.binary.Base64.decodeBase64(str.getBytes("UTF-8")),charset);
    }

    public static final String encodeGBK(String str){
    	try{
    		if(StringUtils.isNotBlank(str)){
    			return new String(org.apache.commons.codec.binary.Base64.encodeBase64(str.getBytes("GBK")),"GBK");
    		}
    	}catch(UnsupportedEncodingException e){
    		LOG.error("", e);
    	}
    	return "";
    }
    
    /**
     *  BASE64 加密4次
     * @param data
     * @return
     * @throws Exception
     */
    public static final String encryptBASE64For4(String data) throws Exception{
    	String result = "";
    	for (int i = 0; i < 4; i++) {
    		if(i == 0){
    			result = new BASE64Encoder().encode(data.getBytes());
    		} else {
    			result = new BASE64Encoder().encode(result.getBytes());
    		}
		}
    	return result;   	
    }
    
    /**
     *  BASE64 加密4次
     * @param data
     * @return
     * @throws Exception
     */
    public static final String encryptBASE64For4Utf8(String data) throws Exception{
    	String result = "";
    	for (int i = 0; i < 4; i++) {
    		if(i == 0){
    			result = new BASE64Encoder().encode(data.getBytes("utf-8"));
    		} else {
    			result = new BASE64Encoder().encode(result.getBytes("utf-8"));
    		}
		}
    	return result;   	
    }
    
    /**    
     * BASE64解密   
   * @param key          
     * @return          
     * @throws Exception          
     */              
    public static byte[] decryptBASE64(String key) throws Exception {               
        return (new BASE64Decoder()).decodeBuffer(key);               
    }
    /**    
     * BASE64解密   
   * @param key          
     * @return          
     * @throws Exception          
     */              
    public static String decryptBASE64ToStr(String key) throws Exception {               
        return new String(decryptBASE64(key),"utf-8");               
    }
    /**
     * BASE64 解密4次
     * @param agrs
     */
    public static byte[] decryptBASE64For4(String key) throws Exception{
    	byte[] by =null;
    	for(int i = 0 ; i < 4 ; i ++){
    		if(i==0){
    			by = new BASE64Decoder().decodeBuffer(key);
    		}else{
    			by = new BASE64Decoder().decodeBuffer(new String(by,"UTF-8"));
    		}
    	}
    	return by;
    }
    
    public static void main(String[] agrs) {
    	try {
//			System.out.println(Base64.encryptBASE64("1".getBytes()));
//			System.out.println(encryptBASE64For4("1"));
			//System.out.println(new String(Base64.decryptBASE64For4("VlVWUk5VNUhTbGhrTW1ScllsWmFOVmw2U25Oa2JVcHhUVWRzVGxWNlVqTlRWMnhEWWtkS2RGUnVXbUZTTW5neFYyNXZkMkZXYjNsVA0KYmtwS1lWVkZkbFZIY0RSamJVWkpZVVJTVVEwS1lXNW5lRnBHWkhOaE1VSnhVMVJXVTFaSGREVlZiWFJXWlZFd1MxUXdVbTVOYkVZMg0KVmxST1QxWkZNSHBWYkZaUFVqRkdjbEpZYkZKbGJWSkhWREZTUm1Rd01YRlpla0pQVmtkak1BMEtWVlpTWVZJeFJYZFZXSEJQWVRCdw0KUTFRd1ZrZFNWVEZXVTJ0V1RtVnNSalZVV0hCclVtczVSV0pGVmxKaGJYaEhWVmh3V21WUk1FdFVWM0JPVFVVeFZWb3pjRk5XUlVZMg0KVld0U2NnMEtaREZLUmxWVVRsRlJlbXQ0V2taa2MyRXhRbkZsU0hCYVRUQTFlRlZIY0VaTlJUVlZVVmh3VG1Wc1JqUlViRkpHVGtVeA0KUlZWVWFFMU5NRFZ4V1hwS2Rrc3hRa1JQV0Vwb1UwZG5NQTBLVlVkak9WQlJQVDA9"),"utf-8"));
    		String str =base64decode("eyJwcm9qZWN0IjoiaW52b2ljZVNpZ24iLCJmaWxlVXJsIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2R6ZnAvc2tmdy9mcFZpZXcvdG9Eb3dubG9hZFBkZi5odG0/aWQ9MjM5OTAiLCJmaWxlVHlwZSI6IjAiLCJmaWxlRXh0ZW5zaW9uIjoicGRmIiwiY2FsbGJhY2siOiJodHRwOi8vbG9jYWxob3N0OjgwODAvZHpmcC9oeFVzZXIvdG9Eb3dubG9hZEZpbGUuaHRtIiwicGRmRmlsZWZsYWciOiJFNEVBNkVGNjY2QjgwQzg2IiwiTlNSTUMiOiLljYfnuqfniYjmtYvor5XnlKjmiLcxNDkzIiwiTlNSU0JIIjoiNTAwMTAyMDEwMDAxNDkzIn0=","UTF-8");
    		System.out.println(str);
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
