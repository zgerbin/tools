package pers.zgerbin.tools.security;

import org.apache.commons.codec.binary.Hex;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

    private static BASE64Encoder base64Encoder = new BASE64Encoder();

    public static String encrypt(String plainText, String charset) {
        String cipherText = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charset != null) {
                try {
                    md.update(plainText.getBytes(charset));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                md.update(plainText.getBytes());
            }
            byte[] engineDigest = md.digest();
            cipherText = Hex.encodeHexString(engineDigest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        return cipherText;
    }

    public static String encrypt(String plainText) {
        return encrypt(plainText, null);
    }


    public static String encryptWithSalt(String plainText, String salt, String charset) {
        return encrypt(plainText + salt, charset);
    }

    public static String encryptWithSalt(String plainText, String salt) {
        return encrypt(plainText + salt, null);
    }

    public static String base64EncodeBeforeEncrypt(String plainText) {
        plainText = base64Encoder.encode(plainText.getBytes());
        return encrypt(plainText, null);
    }


    public static String base64EncodeBeforeEncryptWithSalt(String plainText, String salt, String charset) {
        plainText = plainText + salt;
        try {
            plainText = base64Encoder.encode(plainText.getBytes(charset));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        return encrypt(plainText, charset);
    }

    public static String base64EncodeBeforeEncryptWithSalt(String plainText, String salt) {
        plainText = plainText + salt;
        plainText = base64Encoder.encode(plainText.getBytes());
        return encrypt(plainText, null);
    }

}
