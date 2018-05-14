package com.beeshop.beeshop.net;

import android.text.TextUtils;
import android.util.Base64;


import com.beeshop.beeshop.config.AppConfig;
import com.beeshop.beeshop.utils.GsonUtil;
import com.beeshop.beeshop.utils.SharedPreferenceUtil;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Author : Cooper
 * Time : 2017/8/21  17:02
 * Description : 接口调用工具类
 */

public class HttpLoader {
    private static ApiManager mApiManager;
    private static HttpLoader sInstance;
    private HttpLoader() {
        mApiManager = RetrofitManager.getInstance().create(ApiManager.class);
    }

    public static HttpLoader getInstance() {
        if (sInstance == null) {
            synchronized (HttpLoader.class) {
                if (sInstance == null) {
                    sInstance = new HttpLoader();
                }
            }
        }
        return sInstance;
    }

    public static void removeHttpLoaderInstance() {
        sInstance = null;
    }

    /**
     * 创建请求（带参数）
     * @param dataParams
     * @return
     */
    private HashMap<String, Object> createRequest(HashMap<String, Object> dataParams) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("version", "1.0.0");
        params.put("rsa", "201805");
        params.put("data", RSAUtil.encryptDataByPublicKey(GsonUtil.gsonMapToString(dataParams).getBytes())); // RSA加密
        return params;
    }
    /**
     * 创建请求（不带参数）
     * @param method
     * @return
     */
//    private String createRequest(String method) {
//        RequestEntity requestEntity = new RequestEntity();
//        requestEntity.setId(UUID.randomUUID().hashCode());
//        requestEntity.setJsonrpc("2.0");
//        requestEntity.setMethod(method);
//        List<HashMap<String, Object>> list = new ArrayList<>();
//        requestEntity.setParams(list);
//        return GsonUtil.gsonString(requestEntity);
//    }



    /**
     * 请求接口统一调用
     * @param observable
     * @param compositeSubscription
     * @param func1
     * @param subscriberCallBack
     */
    private void normalPost(Observable observable, CompositeSubscription compositeSubscription, Func1 func1, SubscriberCallBack subscriberCallBack) {
        compositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(func1)
                .subscribe(subscriberCallBack)
        );
    }

    /**
     * 我的定期收益信息
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void getTest(final HashMap<String, Object> params,CompositeSubscription compositeSubscription,SubscriberCallBack<Test> subscriber) {
        normalPost(mApiManager.postLogin(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity<Test>>() {//将接口返回的String数据，转换为实体类
            @Override
            public ResponseEntity<Test> call(String s) {
                String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
                ResponseEntity<Test> response = GsonUtil.gsonToResponse(responseStr,new TypeToken<ResponseEntity<Test>>() {}.getType());
                LoggerUtil.i(JsonUtil.formatNetLog(GsonUtil.gsonMapToString(createRequest(params)),GsonUtil.gsonString(response)));
                return response;
            }
        },subscriber);
    }

    /**
     * 登录
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
//    public void getLogin(final HashMap<String, Object> params, CompositeSubscription compositeSubscription, SubscriberCallBack<String> subscriber) {
//        compositeSubscription.add(mApiManager.postLogin(params)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber)
//        );
//    }
//
//    /**
//     * 退出登录
//     * @param compositeSubscription
//     * @param subscriber
//     */
//    public void getLogout(CompositeSubscription compositeSubscription, SubscriberCallBack subscriber) {
//        compositeSubscription.add(mApiManager.logout(SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN,""))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber)
//        );
//    }



}
