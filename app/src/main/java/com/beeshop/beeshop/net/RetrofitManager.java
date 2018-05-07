package com.beeshop.beeshop.net;


import com.beeshop.beeshop.config.AppConfig;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author : Cooper
 * Time : 2017/8/21  16:35
 * Description : Retrofit配置文件
 */

public class RetrofitManager {
    private static final int DEFAULT_TIME_OUT = 30;//超时时间 30s
    private static final int DEFAULT_READ_TIME_OUT = 30;
    private Retrofit mRetrofit;
    private static RetrofitManager sInstance = null;

    private RetrofitManager() {
        LoggerUtil.init();
        mRetrofit = new Retrofit.Builder().client(getOkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(AppConfig.BASE_URL)
                .build();
    }

    /**
     * DCL单例模式
     * 优点：第一次执行getInstance单利对象才被实例化，效率高。
     * 缺点：第一次加载反应慢。
     * @return
     */
    public static RetrofitManager getInstance() {
        if (sInstance == null) {
            synchronized (RetrofitManager.class) {
                if (sInstance == null) {
                    sInstance = new RetrofitManager();
                }
            }
        }
        return sInstance;
    }

    /**
     * 移除单例
     */
    public static void removeRetrofitManagerInstance() {
        sInstance = null;
    }

    /**
     * 最优单例模式
     * @param service
     * @param <T>
     * @return
     */
//    private static class RetrofitManagerHolder {
//        private static final RetrofitManager INSTANCE = new RetrofitManager();
//    }
//
//    public static RetrofitManager getInstance() {
//        return RetrofitManagerHolder.INSTANCE;
//    }

    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }

    private OkHttpClient getOkHttpClient() {

        OkHttpClient okHttpClient = null;
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] x509Certificates,
                    String s) throws java.security.cert.CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] x509Certificates,
                    String s) throws java.security.cert.CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[] {};
            }
        } };
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间
        builder.writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//读操作超时时间
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        if (sc != null) {
            builder.sslSocketFactory(sc.getSocketFactory(), new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] x509Certificates,
                        String s) throws java.security.cert.CertificateException {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] x509Certificates,
                        String s) throws java.security.cert.CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            });
        }
        // 设置 setLevel(HttpLoggingInterceptor.Level.BODY) 查看请求的所有log信息
        // 设置 setLevel(HttpLoggingInterceptor.Level.NONE) 不显示log信息
//        builder.addNetworkInterceptor(new HttpLoggingInterceptor(/*new HttpLogger()*/).setLevel(HttpLoggingInterceptor.Level.BODY))
        builder.protocols(Collections.singletonList(Protocol.HTTP_1_1));
//        File httpCacheDirectory = new File(ZhuanBeiApplication.getContext().getExternalCacheDir(), "responses_cache");
//        builder.cache(new Cache(httpCacheDirectory,10 * 1024 * 1024));
        builder.addInterceptor(new WuxianInterceptor());
        okHttpClient = builder.build();
        return okHttpClient;
    }

    /**
     * 格式化okhttp的log日志
     */
    private class HttpLogger implements HttpLoggingInterceptor.Logger {
        private StringBuilder mMessage = new StringBuilder();

        @Override
        public void log(String message) {
            // 请求或者响应开始
            if (message.startsWith("--> POST")) {
                mMessage.setLength(0);
            }
            // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
            if ((message.startsWith("{") && message.endsWith("}"))
                    || (message.startsWith("[") && message.endsWith("]"))) {
                message = JsonUtil.formatJson(message);
            }
            mMessage.append(message.concat("\n"));
            // 请求或者响应结束，打印整条日志
            if (message.startsWith("<-- END HTTP")) {
                LoggerUtil.d(mMessage.toString());
            }
        }
    }
}
