package com.beeshop.beeshop.net;


import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Author : cooper
 * Time :  2018/5/9 下午5:16
 * Description :
 */

public class RSAUtil {

    // 字符串公钥，可以直接保存在客户端
    public static final String PUBLIC_KEY_STR = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC9DgZawWWG3gvI2XvcULWJDLl6\n" +
            "cU8HSRqJw0rEM7/KwlARoeV5FHIXcZfWNYaHlq3wS6ppCFYnlSzpfDP6CoPI2NgU\n" +
            "HQamumeO57L0VpIDUt3SS/FjownBmVbH2gkQBGVEQOzF6KAkuplCG9l5PJXUWqKc\n" +
            "an54zCgaOyeT1T9z2wIDAQAB";

    public static final String PRIVATE_KEY_STR = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAL0OBlrBZYbeC8jZ\n" +
            "e9xQtYkMuXpxTwdJGonDSsQzv8rCUBGh5XkUchdxl9Y1hoeWrfBLqmkIVieVLOl8\n" +
            "M/oKg8jY2BQdBqa6Z47nsvRWkgNS3dJL8WOjCcGZVsfaCRAEZURA7MXooCS6mUIb\n" +
            "2Xk8ldRaopxqfnjMKBo7J5PVP3PbAgMBAAECgYAutA5B0VEBdk02+vMsRAv0pV4U\n" +
            "r3U0eYzCx6nqulYBAuvAz1vfA3wD6p313xcy27hwimC4vcR61tFXhMVaWcqgIKCh\n" +
            "yc2jLaSyMCw6YO0uJIBjmWBRnJSjQ8Qeg2gvUowayR0UqNKy0fvhaahf+LJ2cQ1+\n" +
            "lTL1GB12Qq1DzfX74QJBAONSwJUdZBYTqBBISa02Gdda4HKYplAsH5RvMAoviwGT\n" +
            "pQxq/WesVJSszTd165ZbTDY1C0ZU44+IfYazB+wmqwkCQQDU52s18xyT0l7fymKa\n" +
            "UBFNfVBg0746AUaua+fEGyD4pRLWJEX1oAunkqcWY/hlhKQ7g84B44e3WWZfNAZn\n" +
            "gMzDAkAruNXbmEyQxnUTtKOfEgHEXvE+eUe7sdDdHsmRm/VTd09OZSGaGBYohlfu\n" +
            "K9sFD0hIQMmLXHZsO+dQygxjCkjpAkEAhzQrhohMJqfbLj1lXt/oDRC+wa7WSBnV\n" +
            "XCEUfxpHVv/ltW41q2Wm4RUl3PdBoJ6aeV0br2FiJ5Kzi1QLmSFXZwJAC4oOFDvM\n" +
            "KZM18DTbjM1nkEbkgeZaWLSA+syBRsoD8Mrlt2GZ3WE0jhQ9tPveYm8cc4Bh7Wc3\n" +
            "bcm4inbo+z2Q4g==";

    //构建Cipher实例时所传入的的字符串，默认为"RSA/NONE/PKCS1Padding"
    private static String sTransform = "RSA/ECB/PKCS1Padding";

    //进行Base64转码时的flag设置，默认为Base64.DEFAULT
    private static int sBase64Mode = Base64.DEFAULT;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    //初始化方法，设置参数
    public static void init(String transform, int base64Mode) {
        sTransform = transform;
        sBase64Mode = base64Mode;
    }


    /*
        产生密钥对
        @param keyLength
        密钥长度，小于1024长度的密钥已经被证实是不安全的，通常设置为1024或者2048，建议2048
     */
    public static KeyPair generateRSAKeyPair(int keyLength) {
        KeyPair keyPair = null;
        try {

            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            //设置密钥长度
            keyPairGenerator.initialize(keyLength);
            //产生密钥对
            keyPair = keyPairGenerator.generateKeyPair();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return keyPair;
    }

    /*
        加密或解密数据的通用方法
        @param srcData
        待处理的数据
        @param key
        公钥或者私钥
        @param mode
        指定是加密还是解密，值为Cipher.ENCRYPT_MODE或者Cipher.DECRYPT_MODE

     */
    private static byte[] processData(byte[] srcData, Key key, int mode) {

        //用来保存处理结果
        byte[] resultBytes = null;

        try {

            //获取Cipher实例
            Cipher cipher = Cipher.getInstance(sTransform);
            //初始化Cipher，mode指定是加密还是解密，key为公钥或私钥
            cipher.init(mode, key);
            //处理数据
            resultBytes = cipher.doFinal(srcData);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return resultBytes;
    }


    /*
     * 使用公钥加密数据，结果用Base64转码
     * */
    public static String encryptDataByPublicKey(byte[] srcData, PublicKey publicKey) {

        byte[] resultBytes = processData(srcData, publicKey, Cipher.ENCRYPT_MODE);

        return Base64.encodeToString(resultBytes, sBase64Mode);

    }

    /*
     * 使用公钥加密数据，结果用Base64转码
     * */
    public static String encryptDataByPublicKey(byte[] srcData) {
        PublicKey publicKey = RSAUtil.keyStrToPublicKey(RSAUtil.PUBLIC_KEY_STR);

        try {

            return encryptByPublicKey(srcData, publicKey);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /*
        使用私钥解密，返回解码数据
     */
    public static byte[] decryptDataByPrivate(String encryptedData, PrivateKey privateKey) {

        byte[] bytes = Base64.decode(encryptedData, sBase64Mode);

        return processData(bytes, privateKey, Cipher.DECRYPT_MODE);
    }

    /*
        使用私钥进行解密，解密数据转换为字符串，使用utf-8编码格式
     */
    public static String decryptedToStrByPrivate(String encryptedData, PrivateKey privateKey) {
        return new String(decryptDataByPrivate(encryptedData, privateKey));
    }

    /*
        使用私钥解密，解密数据转换为字符串，并指定字符集
     */
    public static String decryptedToStrByPrivate(String encryptedData, PrivateKey privateKey, String charset) {
        try {

            return new String(decryptDataByPrivate(encryptedData, privateKey), charset);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }


        /*
            使用私钥加密，结果用Base64转码
         */

    public static String encryptDataByPrivateKey(byte[] srcData, PrivateKey privateKey) {

        byte[] resultBytes = processData(srcData, privateKey, Cipher.ENCRYPT_MODE);

        return Base64.encodeToString(resultBytes, sBase64Mode);
    }

        /*
            使用公钥解密，返回解密数据
         */

    public static byte[] decryptDataByPublicKey(String encryptedData, PublicKey publicKey) {

        byte[] bytes = Base64.decode(encryptedData, sBase64Mode);

        return processData(bytes, publicKey, Cipher.DECRYPT_MODE);

    }

    private static final String TRANSFORMATION = "RSA";

    /**
     * 解密
     *
     * @param content
     * @param key
     * @return
     * @throws Exception
     */
    public static String decrypByKey(String content, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, key);
        InputStream ins = new ByteArrayInputStream(Base64.decode(content, Base64.DEFAULT));
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        // rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
        byte[] buf = new byte[MAX_DECRYPT_BLOCK];
        int bufl;

        while ((bufl = ins.read(buf)) != -1) {
            byte[] block = null;

            if (buf.length == bufl) {
                block = buf;
            } else {
                block = new byte[bufl];
                for (int i = 0; i < bufl; i++) {
                    block[i] = buf[i];
                }
            }
            writer.write(cipher.doFinal(block));
        }
        return new String(writer.toByteArray(), "utf-8");
    }

    public static String decryptByPublicKey(byte[] encryptedData) {

        PublicKey publicKey = RSAUtil.keyStrToPublicKey(RSAUtil.PUBLIC_KEY_STR);
        byte[] resultBytes = null;
        String result = null;
        try {
            Cipher cipher = Cipher.getInstance(sTransform);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            int inputLen = encryptedData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            resultBytes = out.toByteArray();
            out.close();
            result = new String(resultBytes, "utf-8");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String encryptByPublicKey(byte[] data, Key key)
            throws Exception {
        // 对数据加密
        Cipher cipher = Cipher.getInstance(sTransform);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return Base64.encodeToString(encryptedData, sBase64Mode);
    }

    /*
        使用公钥解密，结果转换为字符串，使用默认字符集utf-8
     */
    public static String decryptedToStrByPublicKey(String encryptedData, PublicKey publicKey) {
        return new String(decryptDataByPublicKey(encryptedData, publicKey));
    }


        /*
            使用公钥解密，结果转换为字符串，使用指定字符集
         */

    public static String decryptedToStrByPublicKey(String encryptedData, PublicKey publicKey, String charset) {
        try {

            return new String(decryptDataByPublicKey(encryptedData, publicKey), charset);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }




        /*
            将字符串形式的公钥转换为公钥对象
         */

    public static PublicKey keyStrToPublicKey(String publicKeyStr) {

        PublicKey publicKey = null;

        byte[] keyBytes = Base64.decode(publicKeyStr, sBase64Mode);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

        try {

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            publicKey = keyFactory.generatePublic(keySpec);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return publicKey;

    }

        /*
            将字符串形式的私钥，转换为私钥对象
         */

    public static PrivateKey keyStrToPrivate(String privateKeyStr) {

        PrivateKey privateKey = null;

        byte[] keyBytes = Base64.decode(privateKeyStr, sBase64Mode);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

        try {

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            privateKey = keyFactory.generatePrivate(keySpec);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return privateKey;

    }

}
