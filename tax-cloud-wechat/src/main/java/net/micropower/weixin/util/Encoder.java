package net.micropower.weixin.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import sun.misc.BASE64Encoder;

public class Encoder {
	public static String chinaToUnicode(String str){  
        String result="";  
        for (int i = 0; i < str.length(); i++){  
            int chr1 = (char) str.charAt(i);  
            if(chr1>=19968&&chr1<=171941){
                result+="\\u" + Integer.toHexString(chr1);  
            }else{  
                result+=str.charAt(i);  
            }  
        }  
        return result;  
    } 
	
private static final String MESSAGE_DIGEST_TYPE = "MD5";
	
	public static String str2md5(String str) {
		try {
			MessageDigest alga = MessageDigest.getInstance(MESSAGE_DIGEST_TYPE);
			alga.update(str.getBytes());
			byte[] digesta = alga.digest();
			return base64Encoding(digesta);
		} catch (NoSuchAlgorithmException ex) {
			return str;
		}
	}
	
	@SuppressWarnings("unchecked")
    public static String base64Encoding(byte[] b) {

        return new BASE64Encoder().encode(b);
    }
    
    public static String decodeUnicode(String theString) {
	      char aChar;
	      int len = theString.length();
	      StringBuffer outBuffer = new StringBuffer(len);
	      for (int x = 0; x < len;) {
	       aChar = theString.charAt(x++);
	       if (aChar == '\\') {
	        aChar = theString.charAt(x++);
	        if (aChar == 'u') {
	         // Read the xxxx
	         int value = 0;
	         for (int i = 0; i < 4; i++) {
	          aChar = theString.charAt(x++);
	          switch (aChar) {
	          case '0':
	          case '1':
	          case '2':
	          case '3':
	          case '4':
	          case '5':
	          case '6':
	          case '7':
	          case '8':
	          case '9':
	           value = (value << 4) + aChar - '0';
	           break;
	          case 'a':
	          case 'b':
	          case 'c':
	          case 'd':
	          case 'e':
	          case 'f':
	           value = (value << 4) + 10 + aChar - 'a';
	           break;
	          case 'A':
	          case 'B':
	          case 'C':
	          case 'D':
	          case 'E':
	          case 'F':
	           value = (value << 4) + 10 + aChar - 'A';
	           break;
	          default:
	           throw new IllegalArgumentException(
	             "Malformed   \\uxxxx   encoding.");
	          }
	         }
	         outBuffer.append((char) value);
	        } else {
	         if (aChar == 't')
	          aChar = '\t';
	         else if (aChar == 'r')
	          aChar = '\r';
	         else if (aChar == 'n')
	          aChar = '\n';
	         else if (aChar == 'f')
	          aChar = '\f';
	         outBuffer.append(aChar);
	        }
	       } else
	        outBuffer.append(aChar);
	      }
	      return outBuffer.toString();
	     }
    
    private static final String MESSAGE_DIGEST_TYPE2 = "SHA";

	public static String str2sha(String str){
		try {
			MessageDigest alga = MessageDigest.getInstance(MESSAGE_DIGEST_TYPE2);
			alga.update(str.getBytes());
			byte[] digesta = alga.digest();
			return base64Encoding(digesta);
		} catch (NoSuchAlgorithmException ex) {
			return str;
		}
	}

    public static String evalSign(HttpServletRequest request, String key){
		String uri = request.getRequestURI();
		if(uri!=null&&uri.trim().lastIndexOf("?")>0&&uri.trim().length()>(uri.trim().lastIndexOf("?")+1)){
			String para = uri.trim().substring(uri.trim().lastIndexOf("?"));
			String sign = request.getParameter("sign");
			if(null==sign){
				sign = "";
			}
			if(para.startsWith("?sign=")){
				para = para.replace("?sign="+sign, "");
				if(para.startsWith("&")&&para.length()>1){
					para = para.substring(1);
					para = para+"&key="+key;
				}else{
					para = "key="+key;
				}
			}else if(para.indexOf("&sign=")>0){
				if(para.startsWith("?")&&para.length()>1){
					para = para.substring(1);
				}
				para = para.replace("&sign="+sign, "");
				para = para + "&key="+key;
			}
			
			return str2sha(para);
		}else{
			return null;
		}
	}
    
    public static String encryption(String plainText) {
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            re_md5 = buf.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
    }
    
    public static void main(String[] args){
		System.out.println(Encoder.chinaToUnicode("输入手机号码"));
    	/*
    	try{
    		java.text.SimpleDateFormat sf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		Calendar c = Calendar.getInstance();
    		c.setTime(sf.parse("2016-04-16 12:12:12"));
    		//c.set(Calendar.HOUR_OF_DAY, 0);
    		//c.set(Calendar.MINUTE, 0);
    		//c.set(Calendar.SECOND, 0);
    		//c.set(Calendar.MILLISECOND, 0);
    		System.out.println(c.get(Calendar.HOUR_OF_DAY));
    	}catch(Exception e){
    		
    	}
		*/
	}

/* public static void main(String[] args) throws ParseException{
	String[] aa = "aaa,bbb,ccc".split(",");
   String[] aa = "aaa|bbb|ccc".split("\\*"); ������ܵõ���ȷ�Ľ��    
   for (int i = 0 ; i <aa.length ; i++ ) {
      System.out.println("--"+aa[i]); 
    }  
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss"); 
	String a="20161203021412";
	String b="20161201021412"; 
	long c=sdf.parse(a).getTime()-sdf.parse(b).getTime();
	System.out.println(sdf.format(b));
	System.out.println(Encoder.chinaToUnicode("ɾ�����Ա��Ϣ�ɹ�"));
}*/
}
