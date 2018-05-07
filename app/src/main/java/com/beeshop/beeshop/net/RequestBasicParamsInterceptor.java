package com.beeshop.beeshop.net;//package com.guazi.polaris.net;
//
//import android.text.TextUtils;
//
//
//import java.io.IOException;
//
//import okhttp3.Interceptor;
//import okhttp3.MediaType;
//import okhttp3.Request;
//import okhttp3.Response;
//
///**
// * Author : Cooper
// * Time : 2017/8/23  10:45
// * Description : 请求参数拦截器
// *               1.缓存：主要是通过http协议里面的control-cache控制缓存，而且是仅仅只能是Get请求才能缓存，如果Post请求OkHttp会让response返回null，同时报504错误，也就是没缓存。
// */
//
//public class RequestBasicParamsInterceptor implements Interceptor {
//    public static final MediaType MEDIA_TYPE
//            = MediaType.parse("application/json; charset=UTF-8");
//    @Override
//    public Response intercept(Chain chain) throws IOException {
//
//        Request request = chain.request();
//        Request.Builder builder = request.newBuilder();
//        String headInfo = DeviceUtil.getHeadInfo(ZhuanBeiApplication.getInstance().getApplication());
//        builder.addHeader("User-Agent",headInfo).build();
//        String lastSessionId = SharedPreferenceUtil.getSessionId(ZhuanBeiApplication.getInstance().getApplication());
//        LogUtil.e("Retrofit Request  session_id === " + lastSessionId);
//        if (!TextUtils.isEmpty(lastSessionId)) {
//            builder.addHeader("Cookie", lastSessionId);
//        }
//
//        request = builder.build();
//
//        Response response = chain.proceed(request);
//
//        // 获取后台返回cookie
//        String responseHeader = response.header("Set-Cookie");
//        if (responseHeader != null) {
//            String cookie = StringTools.splitCookie(responseHeader);
//            if (!TextUtils.isEmpty(cookie)) {
//                SharedPreferenceUtil.getSpInstance(ZhuanBeiApplication.getInstance().getApplication()).edit()
//                        .putString("session_id", cookie).commit();
//                LogUtil.e("Retrofit Reponse  session_id ===  "+ cookie);
//            }
//        }
//
//        return response;
//    }
//
//
//}
