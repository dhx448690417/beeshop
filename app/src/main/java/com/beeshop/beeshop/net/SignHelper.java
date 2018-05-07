package com.beeshop.beeshop.net;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * User: 孙伟力
 * Date: 2014/11/10
 * Time: 15:42
 */
public class SignHelper {

    public static String TOKEN = "d9628ffb2557c1e9362fd7e88604c3be";

    public static String getTokens(Map<String, String> getParams, Map<String, String> postParams) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        if (getParams != null) {
            hashMap.putAll(getParams);
        }
        if (postParams != null) {
            hashMap.putAll(postParams);
        }
        return getSign(hashMap);
    }

    private static String getSign(HashMap<String, String> params) {
        String sign = "";
        sign = getEncodeString(params);
        if (TextUtils.isEmpty(sign)) {
            sign = "1=1"; //如果没有任何参数的时候，签名是1=1用1=1计算
        }

        sign = sign + TOKEN;
        sign = Md5.md5s(sign) + TOKEN;
        sign = Md5.md5s(sign);
        return sign;
    }

    private static String getEncodeString(HashMap<String, String> params) {

        Iterator<String> it = params.keySet().iterator();
        ArrayList<String> al = new ArrayList<String>();

        while (it.hasNext()) {
            al.add(it.next());
        }

            /*
             * 字母升序排列
             */
        Collections.sort(al);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < al.size(); i++) {
            String key = al.get(i);
            sb.append(key);
            sb.append("=");

            String item = null;
            try {
                String value = params.get(key);
                value = value.replaceAll(" ", "");
                value = value.replaceAll("\n", "");
                value = value.replaceAll("\t", "");
                item = URLEncoder.encode(value, "UTF-8");
                //由于 java 在encode时 不会转换 * 字符 而ios会所导致的问题 需要强制转换一下
                item = item.replaceAll("\\*", "%2A");
            } catch (UnsupportedEncodingException e) {
            } catch (NullPointerException e) {
            }

            sb.append(item);
        }

        return sb.toString();
    }

}
