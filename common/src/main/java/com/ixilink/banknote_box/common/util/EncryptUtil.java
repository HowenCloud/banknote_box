package com.ixilink.banknote_box.common.util;

import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.codec.binary.Base64;
//import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * description:
 * author: 张俊
 * date: 2019-11-05 10:11
 */
@Slf4j
public class EncryptUtil {
    public static final String KEY = "ixilink123456789";
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";
    private static final int LENTH = 128;
    private static String base64Encode(byte[] bytes){
        String newStr = java.util.Base64.getEncoder().encodeToString(bytes);
        log.debug("新版加密："+newStr);
//        String string = Base64.encodeBase64String(bytes);
//        log.debug("旧版加密："+string);
        return newStr;
    }
    private static byte[] base64Decode(String base64Code) throws Exception{
        java.util.Base64.Decoder decoder = java.util.Base64.getDecoder();
        byte[] buffer = decoder.decode(base64Code);
        log.debug(buffer.length+" 新 "+new String(buffer));
//        byte[] bytes = new BASE64Decoder().decodeBuffer(base64Code);
//        log.debug(bytes.length+" 旧 "+new String(bytes));
        return buffer;
    }
    private static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(LENTH);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));

        return cipher.doFinal(content.getBytes("utf-8"));
    }
    public static String aesEncrypt(String content, String encryptKey) throws Exception {
        return base64Encode(aesEncryptToBytes(content, encryptKey));
    }
    private static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
        secureRandom.setSeed(decryptKey.getBytes());
        kgen.init(LENTH,secureRandom);

        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);

        return new String(decryptBytes);
    }
    public static String  aesDecrypt(String encryptStr, String decryptKey) throws Exception {
        return aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
    }


    /**
     * 测试
     *
     */
    public static void main(String[] args) throws Exception {

//        String content = "123.png";  //0gqIDaFNAAmwvv3tKsFOFf9P9m/6MWlmtB8SspgxqpWKYnELb/lXkyXm7P4sMf3e
//        System.out.println("加密前：" + content);
//
//        System.out.println("加密密钥和解密密钥：" + KEY);
//
//        String encrypt = aesEncrypt(content, KEY);
//        System.out.println(encrypt.length()+":加密后：" + encrypt);
//
//        String decrypt = aesDecrypt(encrypt, KEY);
//        System.out.println("解密后：" + decrypt);

        String encrypt = aesEncrypt("123456",KEY);
        System.out.println(encrypt);
        System.out.println(aesDecrypt(encrypt, KEY));
        String ss = aesDecrypt("zZkDJ7IaYVpbPGqbEuqTUQ==", "cgsV8oUbX2iacwu8");
        System.out.println(ss);
    }
}