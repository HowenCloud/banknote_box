package com.ixilink.banknote_box.common.util;

import java.security.MessageDigest;

public class PasswordUtil {
	

	public static String encodeMD5(String str){
		return strEncode(str, "MD5");
	}
	
	public static String encodeSHA(String str){
		return strEncode(str, "SHA");
	}
	
	public static String strEncode(String str, String algorithm){
		byte[] unencodedStr = str.getBytes();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (Exception e) {
            return str;
        }
        md.reset();
        md.update(unencodedStr);
        byte[] encodedStr = md.digest();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < encodedStr.length; i
        		++) {
            if ((encodedStr[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString(encodedStr[i] & 0xff, 16));
        }
        return buf.toString();
	}
	
	public static boolean isEmpty(String str){
		return str == null ? true : str.trim().equals("");
	}
	
	//首字母转小写
	public static String toLowerCaseFirstOne(String s){
	  if(Character.isLowerCase(s.charAt(0))) {
          return s;
      } else {
          return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
      }
	}
	//首字母转大写
	public static String toUpperCaseFirstOne(String s){
	  if(Character.isUpperCase(s.charAt(0))) {
          return s;
      } else {
          return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
      }
	}
	//通过对字母ascll码偏移让首字母大写（最高效）
    public static String captureName(String name) {
        char[] cs=name.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
    }
}
