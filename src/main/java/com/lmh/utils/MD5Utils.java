package com.lmh.utils;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;

/**
 * @ClassName: MD5Utils
 * @Description: TODO
 * @author: ALin
 * @date: 2020/3/29 下午7:49
 */
public class MD5Utils {
    /**
     * @Description: 对字符串进行md5加密
     */
    public static String getMD5Str(String strValue) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        String newstr = Base64.encodeBase64String(md5.digest(strValue.getBytes()));
        return newstr;
    }
}
