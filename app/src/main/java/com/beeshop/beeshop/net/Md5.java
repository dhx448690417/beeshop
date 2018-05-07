package com.beeshop.beeshop.net;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * User: 孙伟力
 * Date: 2014/11/10
 * Time: 12:16
 */
class Md5 {

    public String str;

    public Md5() {
    }

    public static String md5s(String plainText) {
        String str = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte[] b = md.digest();

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                int i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            str = buf.toString();

            str = buf.toString();
        } catch (NoSuchAlgorithmException e) {
        }

        return str;
    }
}

