package com.xian.scooter.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignUtils {

    private static SignUtils instance;

    public static SignUtils getInstance() {
        if (instance == null) {
            synchronized (SignUtils.class) {
                if (instance == null) {
                    instance = new SignUtils();
                }
            }
        }
        return instance;
    }
//    public String getSignValue(String... values){
//        for ()
//        getMd5Value()
//        return
//    }
    /**
     * 32位MD5加密方法
     * 16位小写加密只需getMd5Value("xxx").substring(8, 24);即可
     *
     * @param sSecret
     * @return
     */
    public String getMd5Value(String sSecret) {
        try {
            MessageDigest bmd5 = MessageDigest.getInstance("MD5");
            bmd5.update(sSecret.getBytes());
            int i;
            StringBuffer buf = new StringBuffer();
            byte[] b = bmd5.digest();// 加密
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
