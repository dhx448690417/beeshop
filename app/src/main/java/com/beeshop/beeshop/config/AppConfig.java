package com.beeshop.beeshop.config;

import android.Manifest;

/**
 * Author : cooper
 * Time :  2017/12/27 下午6:58
 * Description : 项目配置文件
 */

public class AppConfig {


    public static final boolean DEBUG = true;

//    public static final String URL_TEST = "http://api.bi.bi-testing.guazi-corp.com"; //测试环境
//    public static final String URL_TEST = "http://polaris.bi-testing.guazi-corp.com"; //测试环境
//    public static final String URL_PREPARE = "http://api.bi.guazi-corp.com"; //预发布环境
    public static final String URL_ONLINE = "http://bee.anlmm.com"; //正式环境

    public static final String BASE_URL = URL_ONLINE;
//    public static final String BASE_URL = URL_TEST;
//    public static final String BASE_URL = URL_PREPARE;

    public static final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE, // 缓存
            Manifest.permission.READ_PHONE_STATE,       // 读取deviceId
    };


}
