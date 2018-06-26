package com.beeshop.beeshop.net;

import android.util.Base64;


import com.alibaba.fastjson.JSONObject;
import com.beeshop.beeshop.model.AddressEntity;
import com.beeshop.beeshop.model.BroadcastCardEntity;
import com.beeshop.beeshop.model.BroadcastListEntity;
import com.beeshop.beeshop.model.ClientChatEntity;
import com.beeshop.beeshop.model.OrderCreateEntity;
import com.beeshop.beeshop.model.OrderListEntity;
import com.beeshop.beeshop.model.PicListEntity;
import com.beeshop.beeshop.model.ProductInfo;
import com.beeshop.beeshop.model.ProductListEntity;
import com.beeshop.beeshop.model.QiNiuTokenEntity;
import com.beeshop.beeshop.model.ShopMineInfoEntity;
import com.beeshop.beeshop.model.VipMoneyHistoryRecord;
import com.beeshop.beeshop.model.ProductDetailEntity;
import com.beeshop.beeshop.model.SearchShopEntity;
import com.beeshop.beeshop.model.Shop;
import com.beeshop.beeshop.model.ShopCategoryEntity;
import com.beeshop.beeshop.model.ShopDetailEntity;
import com.beeshop.beeshop.model.UserEntity;
import com.beeshop.beeshop.model.VipEntity;
import com.beeshop.beeshop.model.VipTypeEntity;
import com.beeshop.beeshop.utils.GsonUtil;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Author : Cooper
 * Time : 2017/8/21  17:02
 * Description : 接口调用工具类
 *
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

    /**
     * 会员分类列表
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void getVipTypeList(final HashMap<String, Object> params, CompositeSubscription compositeSubscription, SubscriberCallBack<VipTypeEntity> subscriber) {
        normalPost(mApiManager.postVipTypeList(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity<VipTypeEntity>>() {//将接口返回的String数据，转换为实体类
            @Override
            public ResponseEntity<VipTypeEntity> call(String s) {
                String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
                ResponseEntity<VipTypeEntity> response = GsonUtil.gsonToResponse(responseStr,new TypeToken<ResponseEntity<VipTypeEntity>>() {}.getType());
                LoggerUtil.i(JsonUtil.formatNetLog(GsonUtil.gsonMapToString(params),GsonUtil.gsonString(response)));
                return response;
            }
        },subscriber);
    }

    /**
     * 添加会员分类
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void addVipType(final HashMap<String, Object> params,CompositeSubscription compositeSubscription,SubscriberCallBack subscriber) {
        normalPost(mApiManager.postAddVipType(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity>() {//将接口返回的String数据，转换为实体类
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
     * 添加会员分类
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void uptadeVipType(final HashMap<String, Object> params,CompositeSubscription compositeSubscription,SubscriberCallBack subscriber) {
        normalPost(mApiManager.postUpdateVipType(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity>() {//将接口返回的String数据，转换为实体类
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
     * 会员列表
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void getVipList(final HashMap<String, Object> params, CompositeSubscription compositeSubscription, SubscriberCallBack<VipEntity> subscriber) {
        normalPost(mApiManager.postVipList(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity<VipEntity>>() {//将接口返回的String数据，转换为实体类
            @Override
            public ResponseEntity<VipEntity> call(String s) {
                String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
                ResponseEntity<VipEntity> response = GsonUtil.gsonToResponse(responseStr,new TypeToken<ResponseEntity<VipEntity>>() {}.getType());
                LoggerUtil.i(JsonUtil.formatNetLog(GsonUtil.gsonMapToString(params),GsonUtil.gsonString(response)));
                return response;
            }
        },subscriber);
    }

    /**
     * 添加会员
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void addVip(final HashMap<String, Object> params,CompositeSubscription compositeSubscription,SubscriberCallBack subscriber) {
        normalPost(mApiManager.postAddVip(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity>() {//将接口返回的String数据，转换为实体类
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
     * 更新会员
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void updateVip(final HashMap<String, Object> params,CompositeSubscription compositeSubscription,SubscriberCallBack subscriber) {
        normalPost(mApiManager.postUpdateVip(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity>() {//将接口返回的String数据，转换为实体类
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
     * 充值
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void recharge(final HashMap<String, Object> params,CompositeSubscription compositeSubscription,SubscriberCallBack subscriber) {
        normalPost(mApiManager.postRecharge(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity>() {//将接口返回的String数据，转换为实体类
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
     * 消费
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void pay(final HashMap<String, Object> params,CompositeSubscription compositeSubscription,SubscriberCallBack subscriber) {
        normalPost(mApiManager.postPay(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity>() {//将接口返回的String数据，转换为实体类
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
     * 消费历史记录
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void payHistoryRecord(final HashMap<String, Object> params, CompositeSubscription compositeSubscription, SubscriberCallBack<VipMoneyHistoryRecord> subscriber) {
        normalPost(mApiManager.postPayHistoryRecord(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity<VipMoneyHistoryRecord>>() {//将接口返回的String数据，转换为实体类
            @Override
            public ResponseEntity<VipMoneyHistoryRecord> call(String s) {
                String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
                ResponseEntity<VipMoneyHistoryRecord> response = GsonUtil.gsonToResponse(responseStr,new TypeToken<ResponseEntity<VipMoneyHistoryRecord>>() {}.getType());
                LoggerUtil.i(JsonUtil.formatNetLog(GsonUtil.gsonMapToString(params),GsonUtil.gsonString(response)));
                return response;
            }
        },subscriber);
    }

    /**
     * 消费历史记录
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void rechargeHistoryRecord(final HashMap<String, Object> params, CompositeSubscription compositeSubscription, SubscriberCallBack<VipMoneyHistoryRecord> subscriber) {
        normalPost(mApiManager.postRechargeHistoryRecord(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity<VipMoneyHistoryRecord>>() {//将接口返回的String数据，转换为实体类
            @Override
            public ResponseEntity<VipMoneyHistoryRecord> call(String s) {
                String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
                ResponseEntity<VipMoneyHistoryRecord> response = GsonUtil.gsonToResponse(responseStr,new TypeToken<ResponseEntity<VipMoneyHistoryRecord>>() {}.getType());
                LoggerUtil.i(JsonUtil.formatNetLog(GsonUtil.gsonMapToString(params),GsonUtil.gsonString(response)));
                return response;
            }
        },subscriber);
    }

    /**
     * 注册商店
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void registerShop(final HashMap<String, Object> params,CompositeSubscription compositeSubscription,SubscriberCallBack subscriber) {
        normalPost(mApiManager.postRegisterShop(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity>() {//将接口返回的String数据，转换为实体类
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
     * 获取七牛token
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void getQiNiuToken(final HashMap<String, Object> params, CompositeSubscription compositeSubscription, SubscriberCallBack<QiNiuTokenEntity> subscriber) {
        normalPost(mApiManager.postQiNiuToken(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity<QiNiuTokenEntity>>() {//将接口返回的String数据，转换为实体类
            @Override
            public ResponseEntity<QiNiuTokenEntity> call(String s) {
                String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
                ResponseEntity<QiNiuTokenEntity> response = GsonUtil.gsonToResponse(responseStr,new TypeToken<ResponseEntity<QiNiuTokenEntity>>() {}.getType());
                LoggerUtil.i(JsonUtil.formatNetLog(GsonUtil.gsonMapToString(params),GsonUtil.gsonString(response)));
                return response;
            }
        },subscriber);
    }

    /**
     * 获取店铺信息
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void getShopInfo(final HashMap<String, Object> params, CompositeSubscription compositeSubscription, SubscriberCallBack<ShopMineInfoEntity> subscriber) {
        normalPost(mApiManager.postShopInfo(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity<ShopMineInfoEntity>>() {//将接口返回的String数据，转换为实体类
            @Override
            public ResponseEntity<ShopMineInfoEntity> call(String s) {
                String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
                ResponseEntity<ShopMineInfoEntity> response = GsonUtil.gsonToResponse(responseStr,new TypeToken<ResponseEntity<ShopMineInfoEntity>>() {}.getType());
                LoggerUtil.i(JsonUtil.formatNetLog(GsonUtil.gsonMapToString(params),GsonUtil.gsonString(response)));
                return response;
            }
        },subscriber);
    }

    /**
     * 更新商店信息
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void updateShopInfo(final HashMap<String, Object> params,CompositeSubscription compositeSubscription,SubscriberCallBack subscriber) {
        normalPost(mApiManager.postShopInfoUpdate(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity>() {//将接口返回的String数据，转换为实体类
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
     * 获取店铺图片列表
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void getShopPicList(final HashMap<String, Object> params, CompositeSubscription compositeSubscription, SubscriberCallBack<PicListEntity> subscriber) {
        normalPost(mApiManager.postShopPicList(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity<PicListEntity>>() {//将接口返回的String数据，转换为实体类
            @Override
            public ResponseEntity<PicListEntity> call(String s) {
                String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
                ResponseEntity<PicListEntity> response = GsonUtil.gsonToResponse(responseStr,new TypeToken<ResponseEntity<PicListEntity>>() {}.getType());
                LoggerUtil.i(JsonUtil.formatNetLog(GsonUtil.gsonMapToString(params),GsonUtil.gsonString(response)));
                return response;
            }
        },subscriber);
    }

    /**
     * 上传商店图片
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void addShopPic(final HashMap<String, Object> params,CompositeSubscription compositeSubscription,SubscriberCallBack subscriber) {
        normalPost(mApiManager.postAddShopPic(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity>() {//将接口返回的String数据，转换为实体类
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
     * 删除商店图片
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void deleteShopPic(final HashMap<String, Object> params,CompositeSubscription compositeSubscription,SubscriberCallBack subscriber) {
        normalPost(mApiManager.postDeleteShopPic(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity>() {//将接口返回的String数据，转换为实体类
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
     * 设置商店图片为封面
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void setShopCoverPic(final HashMap<String, Object> params,CompositeSubscription compositeSubscription,SubscriberCallBack subscriber) {
        normalPost(mApiManager.postSetShopCoverPic(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity>() {//将接口返回的String数据，转换为实体类
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
     * 更新产品
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void updateProduct(final HashMap<String, Object> params,CompositeSubscription compositeSubscription,SubscriberCallBack<ProductInfo> subscriber) {
        normalPost(mApiManager.postUpdateProduct(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity<ProductInfo>>() {//将接口返回的String数据，转换为实体类
            @Override
            public ResponseEntity<ProductInfo> call(String s) {
                String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
                ResponseEntity<ProductInfo> response = GsonUtil.gsonToResponse(responseStr,new TypeToken<ResponseEntity<ProductInfo>>() {}.getType());
                LoggerUtil.i(JsonUtil.formatNetLog(GsonUtil.gsonMapToString(params),GsonUtil.gsonString(response)));
                return response;
            }
        },subscriber);
    }

    /**
     * 添加产品
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void addProduct(final HashMap<String, Object> params, CompositeSubscription compositeSubscription, SubscriberCallBack<ProductInfo> subscriber) {
        normalPost(mApiManager.postAddProduct(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity<ProductInfo>>() {//将接口返回的String数据，转换为实体类
            @Override
            public ResponseEntity<ProductInfo> call(String s) {
                String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
                ResponseEntity<ProductInfo> response = GsonUtil.gsonToResponse(responseStr,new TypeToken<ResponseEntity<ProductInfo>>() {}.getType());
                LoggerUtil.i(JsonUtil.formatNetLog(GsonUtil.gsonMapToString(params),GsonUtil.gsonString(response)));
                return response;
            }
        },subscriber);
    }


    /**
     * 获取产品列表
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void getProductList(final HashMap<String, Object> params, CompositeSubscription compositeSubscription, SubscriberCallBack<ProductListEntity> subscriber) {
        normalPost(mApiManager.postProductList(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity<ProductListEntity>>() {//将接口返回的String数据，转换为实体类
            @Override
            public ResponseEntity<ProductListEntity> call(String s) {
                String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
                ResponseEntity<ProductListEntity> response = GsonUtil.gsonToResponse(responseStr,new TypeToken<ResponseEntity<ProductListEntity>>() {}.getType());
                LoggerUtil.i(JsonUtil.formatNetLog(GsonUtil.gsonMapToString(params),GsonUtil.gsonString(response)));
                return response;
            }
        },subscriber);
    }


    /**
     * 获取产品图片列表
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void getProductPicList(final HashMap<String, Object> params, CompositeSubscription compositeSubscription, SubscriberCallBack<PicListEntity> subscriber) {
        normalPost(mApiManager.postProductPicList(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity<PicListEntity>>() {//将接口返回的String数据，转换为实体类
            @Override
            public ResponseEntity<PicListEntity> call(String s) {
                String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
                ResponseEntity<PicListEntity> response = GsonUtil.gsonToResponse(responseStr,new TypeToken<ResponseEntity<PicListEntity>>() {}.getType());
                LoggerUtil.i(JsonUtil.formatNetLog(GsonUtil.gsonMapToString(params),GsonUtil.gsonString(response)));
                return response;
            }
        },subscriber);
    }

    /**
     * 上传产品图片
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void addProductPic(final HashMap<String, Object> params,CompositeSubscription compositeSubscription,SubscriberCallBack subscriber) {
        normalPost(mApiManager.postAddProductPic(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity>() {//将接口返回的String数据，转换为实体类
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
     * 删除商店图片
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void deleteProductPic(final HashMap<String, Object> params,CompositeSubscription compositeSubscription,SubscriberCallBack subscriber) {
        normalPost(mApiManager.postDeleteProductPic(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity>() {//将接口返回的String数据，转换为实体类
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
     * 设置商店图片为封面
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void setProductCoverPic(final HashMap<String, Object> params,CompositeSubscription compositeSubscription,SubscriberCallBack subscriber) {
        normalPost(mApiManager.postSetProductCoverPic(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity>() {//将接口返回的String数据，转换为实体类
            @Override
            public ResponseEntity call(String s) {
                String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
                ResponseEntity response = GsonUtil.gsonToResponse(responseStr,new TypeToken<ResponseEntity>() {}.getType());
                LoggerUtil.i(JsonUtil.formatNetLog(GsonUtil.gsonMapToString(params),GsonUtil.gsonString(response)));
                return response;
            }
        },subscriber);
    }

    // ===============================  订单接口  =====================================

    /**
     * 下单
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void createOrder(final HashMap<String, Object> params, CompositeSubscription compositeSubscription, SubscriberCallBack<OrderCreateEntity> subscriber) {
        normalPost(mApiManager.postCreateOrder(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity<OrderCreateEntity>>() {//将接口返回的String数据，转换为实体类
            @Override
            public ResponseEntity<OrderCreateEntity> call(String s) {
                String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
                ResponseEntity<OrderCreateEntity> response = GsonUtil.gsonToResponse(responseStr,new TypeToken<ResponseEntity<OrderCreateEntity>>() {}.getType());
                LoggerUtil.i(JsonUtil.formatNetLog(GsonUtil.gsonMapToString(params),GsonUtil.gsonString(response)));
                return response;
            }
        },subscriber);
    }

    /**
     * 支付
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void confirmPay(final HashMap<String, Object> params, CompositeSubscription compositeSubscription, SubscriberCallBack<JSONObject> subscriber) {
        normalPost(mApiManager.postConfirmPay(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity<JSONObject>>() {//将接口返回的String数据，转换为实体类
            @Override
            public ResponseEntity<JSONObject> call(String s) {
                String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
                ResponseEntity<JSONObject> response = GsonUtil.gsonToResponse(responseStr,new TypeToken<ResponseEntity<JSONObject>>() {}.getType());
                LoggerUtil.i(JsonUtil.formatNetLog(GsonUtil.gsonMapToString(params),GsonUtil.gsonString(response)));
                return response;
            }
        },subscriber);
    }

    /**
     * 获取我的订单列表
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void getMyOrderList(final HashMap<String, Object> params, CompositeSubscription compositeSubscription, SubscriberCallBack<OrderListEntity> subscriber) {
        normalPost(mApiManager.postMyOrderList(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity<OrderListEntity>>() {//将接口返回的String数据，转换为实体类
            @Override
            public ResponseEntity<OrderListEntity> call(String s) {
                String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
                ResponseEntity<OrderListEntity> response = GsonUtil.gsonToResponse(responseStr,new TypeToken<ResponseEntity<OrderListEntity>>() {}.getType());
                LoggerUtil.i(JsonUtil.formatNetLog(GsonUtil.gsonMapToString(params),GsonUtil.gsonString(response)));
                return response;
            }
        },subscriber);
    }

    /**
     * 获取产品订单列表
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void getShopOrderList(final HashMap<String, Object> params, CompositeSubscription compositeSubscription, SubscriberCallBack<OrderListEntity> subscriber) {
        normalPost(mApiManager.postShopOrderList(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity<OrderListEntity>>() {//将接口返回的String数据，转换为实体类
            @Override
            public ResponseEntity<OrderListEntity> call(String s) {
                String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
                ResponseEntity<OrderListEntity> response = GsonUtil.gsonToResponse(responseStr,new TypeToken<ResponseEntity<OrderListEntity>>() {}.getType());
                LoggerUtil.i(JsonUtil.formatNetLog(GsonUtil.gsonMapToString(params),GsonUtil.gsonString(response)));
                return response;
            }
        },subscriber);
    }


    // ===============================  电台类接口  =====================================

    /**
     * 获取店铺排序列表
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void getShopSortList(final HashMap<String, Object> params, CompositeSubscription compositeSubscription, SubscriberCallBack<Shop> subscriber) {
        normalPost(mApiManager.postShopSortList(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity<Shop>>() {//将接口返回的String数据，转换为实体类
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
     * 获取附近广播列表
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void getNearbyBroadcastList(final HashMap<String, Object> params, CompositeSubscription compositeSubscription, SubscriberCallBack<BroadcastListEntity> subscriber) {
        normalPost(mApiManager.postNearbyBroadcast(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity<BroadcastListEntity>>() {//将接口返回的String数据，转换为实体类
            @Override
            public ResponseEntity<BroadcastListEntity> call(String s) {
                String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
                ResponseEntity<BroadcastListEntity> response = GsonUtil.gsonToResponse(responseStr,new TypeToken<ResponseEntity<BroadcastListEntity>>() {}.getType());
                LoggerUtil.i(JsonUtil.formatNetLog(GsonUtil.gsonMapToString(params),GsonUtil.gsonString(response)));
                return response;
            }
        },subscriber);
    }

    /**
     * 发布广播
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void publishBroadcast(final HashMap<String, Object> params,CompositeSubscription compositeSubscription,SubscriberCallBack subscriber) {
        normalPost(mApiManager.postPublishBroadcast(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity>() {//将接口返回的String数据，转换为实体类
            @Override
            public ResponseEntity call(String s) {
                String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
                ResponseEntity response = GsonUtil.gsonToResponse(responseStr,new TypeToken<ResponseEntity>() {}.getType());
                LoggerUtil.i(JsonUtil.formatNetLog(GsonUtil.gsonMapToString(params),GsonUtil.gsonString(response)));
                return response;
            }
        },subscriber);
    }


    // ===============================  我的接口  =====================================

    /**
     * 获取我的广播列表
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void getMyBroadcastList(final HashMap<String, Object> params, CompositeSubscription compositeSubscription, SubscriberCallBack<BroadcastListEntity> subscriber) {
        normalPost(mApiManager.postMyBroadcast(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity<BroadcastListEntity>>() {//将接口返回的String数据，转换为实体类
            @Override
            public ResponseEntity<BroadcastListEntity> call(String s) {
                String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
                ResponseEntity<BroadcastListEntity> response = GsonUtil.gsonToResponse(responseStr,new TypeToken<ResponseEntity<BroadcastListEntity>>() {}.getType());
                LoggerUtil.i(JsonUtil.formatNetLog(GsonUtil.gsonMapToString(params),GsonUtil.gsonString(response)));
                return response;
            }
        },subscriber);
    }

    /**
     * 获取我的广播卡列表
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void getMyBroadcastCardList(final HashMap<String, Object> params, CompositeSubscription compositeSubscription, SubscriberCallBack<BroadcastCardEntity> subscriber) {
        normalPost(mApiManager.postMyBroadcastCard(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity<BroadcastCardEntity>>() {//将接口返回的String数据，转换为实体类
            @Override
            public ResponseEntity<BroadcastCardEntity> call(String s) {
                String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
                ResponseEntity<BroadcastCardEntity> response = GsonUtil.gsonToResponse(responseStr,new TypeToken<ResponseEntity<BroadcastCardEntity>>() {}.getType());
                LoggerUtil.i(JsonUtil.formatNetLog(GsonUtil.gsonMapToString(params),GsonUtil.gsonString(response)));
                return response;
            }
        },subscriber);
    }

    /**
     * 获取我的地址列表
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void getMyAddress(final HashMap<String, Object> params, CompositeSubscription compositeSubscription, SubscriberCallBack<AddressEntity> subscriber) {
        normalPost(mApiManager.postMyAddress(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity<AddressEntity>>() {//将接口返回的String数据，转换为实体类
            @Override
            public ResponseEntity<AddressEntity> call(String s) {
                String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
                ResponseEntity<AddressEntity> response = GsonUtil.gsonToResponse(responseStr,new TypeToken<ResponseEntity<AddressEntity>>() {}.getType());
                LoggerUtil.i(JsonUtil.formatNetLog(GsonUtil.gsonMapToString(params),GsonUtil.gsonString(response)));
                return response;
            }
        },subscriber);
    }

    /**
     * 添加地址
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void addAddress(final HashMap<String, Object> params,CompositeSubscription compositeSubscription,SubscriberCallBack subscriber) {
        normalPost(mApiManager.postAddAddress(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity>() {//将接口返回的String数据，转换为实体类
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
     * 更新地址
     * @param params
     * @param compositeSubscription
     * @param subscriber
     */
    public void updateAddress(final HashMap<String, Object> params,CompositeSubscription compositeSubscription,SubscriberCallBack subscriber) {
        normalPost(mApiManager.postUpdateAddress(createRequest(params)),compositeSubscription,new Func1<String, ResponseEntity>() {//将接口返回的String数据，转换为实体类
            @Override
            public ResponseEntity call(String s) {
                String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
                ResponseEntity response = GsonUtil.gsonToResponse(responseStr,new TypeToken<ResponseEntity>() {}.getType());
                LoggerUtil.i(JsonUtil.formatNetLog(GsonUtil.gsonMapToString(params),GsonUtil.gsonString(response)));
                return response;
            }
        },subscriber);
    }
}
