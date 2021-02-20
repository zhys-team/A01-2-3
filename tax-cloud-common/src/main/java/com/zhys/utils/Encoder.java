package com.zhys.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class Encoder {
	private static final String MESSAGE_DIGEST_TYPE = "MD5";
	
	/**
	 * @param str
	 * @return
	 */
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
	
	/**
     * @param b
     * @return
     */
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
}
