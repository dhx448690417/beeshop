package com.beeshop.beeshop.net;

import android.text.TextUtils;
import android.util.Base64;


import com.beeshop.beeshop.config.AppConfig;
import com.beeshop.beeshop.model.ClientChatEntity;
import com.beeshop.beeshop.model.ProductDetailEntity;
import com.beeshop.beeshop.model.SearchShopEntity;
import com.beeshop.beeshop.model.Shop;
import com.beeshop.beeshop.model.ShopCategoryEntity;
import com.beeshop.beeshop.model.ShopDetailEntity;
import com.beeshop.beeshop.model.UserEntity;
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
     * 测试
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void getTest(final HashMap<String, Object> params,CompositeSubscription compositeSubscription,SubscriberCallBack<Test> subscriber) {
        normalPost(mApiManager.postTest(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity<Test>>() {//将接口返回的String数据，转换为实体类
            @Override
            public ResponseEntity<Test> call(String s) {
                String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
                ResponseEntity<Test> response = GsonUtil.gsonToResponse(responseStr,new TypeToken<ResponseEntity<Test>>() {}.getType());
                LoggerUtil.i(JsonUtil.formatNetLog(GsonUtil.gsonMapToString(params),GsonUtil.gsonString(response)));
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
    public void login(final HashMap<String, Object> params,CompositeSubscription compositeSubscription,SubscriberCallBack<UserEntity> subscriber) {
        normalPost(mApiManager.postLogin(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity<UserEntity>>() {//将接口返回的String数据，转换为实体类
            @Override
            public ResponseEntity<UserEntity> call(String s) {
                String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
                ResponseEntity<UserEntity> response = GsonUtil.gsonToResponse(responseStr,new TypeToken<ResponseEntity<UserEntity>>() {}.getType());
                LoggerUtil.i(JsonUtil.formatNetLog(GsonUtil.gsonMapToString(params),GsonUtil.gsonString(response)));
                return response;
            }
        },subscriber);
    }

    /**
     * 注册
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void register(final HashMap<String, Object> params,CompositeSubscription compositeSubscription,SubscriberCallBack subscriber) {
        normalPost(mApiManager.postRegister(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity>() {//将接口返回的String数据，转换为实体类
            @Override
            public ResponseEntity call(String s) {
                String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
                ResponseEntity response = GsonUtil.gsonToResponse(responseStr,new TypeToken<ResponseEntity>() {}.getType());
                LoggerUtil.i(JsonUtil.formatNetLog(GsonUtil.gsonMapToString(params),GsonUtil.gsonString(response)));
                return response;
            }
        },subscriber);
    }

    /**
     * 获取短信验证码
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void getMessageCode(final HashMap<String, Object> params,CompositeSubscription compositeSubscription,SubscriberCallBack subscriber) {
        normalPost(mApiManager.postMessageCode(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity>() {//将接口返回的String数据，转换为实体类
            @Override
            public ResponseEntity call(String s) {
                String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
                ResponseEntity response = GsonUtil.gsonToResponse(responseStr,new TypeToken<ResponseEntity>() {}.getType());
                LoggerUtil.i(JsonUtil.formatNetLog(GsonUtil.gsonMapToString(params),GsonUtil.gsonString(response)));
                return response;
            }
        },subscriber);
    }


    /**
     * 我关注的店铺
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void getShops(final HashMap<String, Object> params,CompositeSubscription compositeSubscription,SubscriberCallBack<Shop> subscriber) {
        normalPost(mApiManager.postShops(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity<Shop>>() {//将接口返回的String数据，转换为实体类
            @Override
            public ResponseEntity<Shop> call(String s) {
                String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
                ResponseEntity<Shop> response = GsonUtil.gsonToResponse(responseStr,new TypeToken<ResponseEntity<Shop>>() {}.getType());
                LoggerUtil.i(JsonUtil.formatNetLog(GsonUtil.gsonMapToString(params),GsonUtil.gsonString(response)));
                return response;
            }
        },subscriber);
    }

    /**
     * 我关注的店铺
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void getSearchShops(final HashMap<String, Object> params,CompositeSubscription compositeSubscription,SubscriberCallBack<SearchShopEntity> subscriber) {
        normalPost(mApiManager.postSearchShops(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity<SearchShopEntity>>() {//将接口返回的String数据，转换为实体类
            @Override
            public ResponseEntity<SearchShopEntity> call(String s) {
                String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
                ResponseEntity<SearchShopEntity> response = GsonUtil.gsonToResponse(responseStr,new TypeToken<ResponseEntity<SearchShopEntity>>() {}.getType());
                LoggerUtil.i(JsonUtil.formatNetLog(GsonUtil.gsonMapToString(params),GsonUtil.gsonString(response)));
                return response;
            }
        },subscriber);
    }

    /**
     * 店铺类型
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void getShopCategory(final HashMap<String, Object> params,CompositeSubscription compositeSubscription,SubscriberCallBack<ShopCategoryEntity> subscriber) {
        normalPost(mApiManager.postShopCategory(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity<ShopCategoryEntity>>() {//将接口返回的String数据，转换为实体类
            @Override
            public ResponseEntity<ShopCategoryEntity> call(String s) {
                String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
                ResponseEntity<ShopCategoryEntity> response = GsonUtil.gsonToResponse(responseStr,new TypeToken<ResponseEntity<ShopCategoryEntity>>() {}.getType());
                LoggerUtil.i(JsonUtil.formatNetLog(GsonUtil.gsonMapToString(params),GsonUtil.gsonString(response)));
                return response;
            }
        },subscriber);
    }

    /**
     * 店铺详情
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void getShopDetail(final HashMap<String, Object> params, CompositeSubscription compositeSubscription, SubscriberCallBack<ShopDetailEntity> subscriber) {
        normalPost(mApiManager.postShopDetail(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity<ShopDetailEntity>>() {//将接口返回的String数据，转换为实体类
            @Override
            public ResponseEntity<ShopDetailEntity> call(String s) {
                String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
                ResponseEntity<ShopDetailEntity> response = GsonUtil.gsonToResponse(responseStr,new TypeToken<ResponseEntity<ShopDetailEntity>>() {}.getType());
                LoggerUtil.i(JsonUtil.formatNetLog(GsonUtil.gsonMapToString(params),GsonUtil.gsonString(response)));
                return response;
            }
        },subscriber);
    }

    /**
     * 店铺的关注or取消关注
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void collect(final HashMap<String, Object> params,CompositeSubscription compositeSubscription,SubscriberCallBack subscriber) {
        normalPost(mApiManager.postCollect(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity>() {//将接口返回的String数据，转换为实体类
            @Override
            public ResponseEntity call(String s) {
                String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
                ResponseEntity response = GsonUtil.gsonToResponse(responseStr,new TypeToken<ResponseEntity>() {}.getType());
                LoggerUtil.i(JsonUtil.formatNetLog(GsonUtil.gsonMapToString(params),GsonUtil.gsonString(response)));
                return response;
            }
        },subscriber);
    }

    /**
     * 产品详情
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void getProductInfo(final HashMap<String, Object> params, CompositeSubscription compositeSubscription, SubscriberCallBack<ProductDetailEntity> subscriber) {
        normalPost(mApiManager.postProductInfo(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity<ProductDetailEntity>>() {//将接口返回的String数据，转换为实体类
            @Override
            public ResponseEntity<ProductDetailEntity> call(String s) {
                String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
                ResponseEntity<ProductDetailEntity> response = GsonUtil.gsonToResponse(responseStr,new TypeToken<ResponseEntity<ProductDetailEntity>>() {}.getType());
                LoggerUtil.i(JsonUtil.formatNetLog(GsonUtil.gsonMapToString(params),GsonUtil.gsonString(response)));
                return response;
            }
        },subscriber);
    }

    /**
     * 客户聊天列表
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void getChat(final HashMap<String, Object> params, CompositeSubscription compositeSubscription, SubscriberCallBack<ClientChatEntity> subscriber) {
        normalPost(mApiManager.postCustomer(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity<ClientChatEntity>>() {//将接口返回的String数据，转换为实体类
            @Override
            public ResponseEntity<ClientChatEntity> call(String s) {
                String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
                ResponseEntity<ClientChatEntity> response = GsonUtil.gsonToResponse(responseStr,new TypeToken<ResponseEntity<ClientChatEntity>>() {}.getType());
                LoggerUtil.i(JsonUtil.formatNetLog(GsonUtil.gsonMapToString(params),GsonUtil.gsonString(response)));
                return response;
            }
        },subscriber);
    }

}
