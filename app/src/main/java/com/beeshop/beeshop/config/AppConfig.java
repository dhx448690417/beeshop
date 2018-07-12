package com.beeshop.beeshop.config;

import android.Manifest;

/**
 * Author : cooper
 * Time :  2017/12/27 下午6:58
 * Description : 项目配置文件
 */

public class AppConfig {

    // 18233608179

    /**
     * keystore
     * beeshop_keystore   password:beeshop123
     * <p>
     * 蜂店应用签名：f5342955ea4cf08259d23277c510ab5d
     * debug 签名：9b7405a5f08659f5e7d9d6acdd110c15
     * <p>
     * 微信 appid wx234f5277333171ba
     */

    public static final String WEIXIN_APP_ID = "wx234f5277333171ba";


    public static final boolean DEBUG = true;

//    public static final String URL_TEST = "http://api.bi.bi-testing.guazi-corp.com"; //测试环境
//    public static final String URL_TEST = "http://polaris.bi-testing.guazi-corp.com"; //测试环境
//    public static final String URL_PREPARE = "http://api.bi.guazi-corp.com"; //预发布环境
    public static final String URL_ONLINE = "http://bee.fdwl1688.com"; //正式环境
    public static final String H5_URL_ONLINE = "http://bee.fdwl1688.com/chat/index"; //聊天

    public static final String BASE_URL = URL_ONLINE;
//    public static final String BASE_URL = URL_TEST;
//    public static final String BASE_URL = URL_PREPARE;

    public static final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE, // 缓存
            Manifest.permission.READ_PHONE_STATE,       // 读取deviceId
    };


}
