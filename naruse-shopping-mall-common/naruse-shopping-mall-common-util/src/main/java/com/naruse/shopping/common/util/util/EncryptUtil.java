package com.naruse.shopping.common.util.util;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * 加密工具
 * 采用sha256 + MD5
 */
@Slf4j
public class EncryptUtil {

    /**
     * 加密（sha256 + MD5）
     * @param str
     * @return
     */
    public static String encrypt(String str) {
        return md5Encrypt(sha256Encrypt(str));
    }

    /**
     * 利用java原生的类实现SHA256加密
     *
     * @param str 加密后的报文
     * @return
     */
    public static String sha256Encrypt(String str) {
        MessageDigest messageDigest;
        String encodestr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            encodestr = byte2Hex(messageDigest.digest());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return encodestr;
    }

    /**
     * 将byte转为16进制
     *
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        String temp;
        for (byte aByte : bytes) {
            temp = Integer.toHexString(aByte & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuilder.append("0");
            }
            stringBuilder.append(temp);
        }
        return stringBuilder.toString();
    }

    /**
     * 获取指定字符串的MD5
     * @param str 需要加密的字符串
     * @param salt 盐值
     * @return
     */
    public static String md5Encrypt(String str, String salt) {
        return md5Encrypt(str + salt);
    }

    /**
     * md5加密
     * @param str
     * @return
     */
    private static String md5Encrypt(String str) {
        String res = "";

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes(StandardCharsets.UTF_8));

            byte[] digest = md5.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : digest) {
                stringBuilder.append(Integer.toHexString((0x000000FF & b) | 0xFFFFFF00).substring(6));
            }

            res = stringBuilder.toString();
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return res;
    }
}
