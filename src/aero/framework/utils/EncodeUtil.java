package aero.framework.utils;

import java.io.UnsupportedEncodingException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class EncodeUtil {

	/**
	 * base64加密
	 * @param str
	 * @return
	 */
	public static String encodeBase64(String str){
		byte[] bytes = null;  
        String result = null;
        try {  
            bytes = str.getBytes("utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        if (bytes != null) {  
            result = new BASE64Encoder().encode(bytes);  
        }  
        return result; 
	}
	
	 /**
	  * base64解密
	  * @param str
	  * @return
	  */
    public static String praseBase64(String str) {  
        byte[] bytes = null;  
        String result = null;  
        if (str != null) {  
            BASE64Decoder decoder = new BASE64Decoder();  
            try {  
                bytes = decoder.decodeBuffer(str);  
                result = new String(bytes, "utf-8");  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return result;  
    }
    
}
