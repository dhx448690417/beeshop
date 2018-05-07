package com.beeshop.beeshop.net;

import android.text.TextUtils;


import com.beeshop.beeshop.config.AppConfig;
import com.beeshop.beeshop.utils.SharedPreferenceUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
     * 登录
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
//    public void getLogin(final HashMap<String, Object> params, CompositeSubscription compositeSubscription, SubscriberCallBack<UserEntity> subscriber) {
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
