package com.beeshop.beeshop.net;


import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by ah on 2017/4/30.
 */

public interface ApiManager {

    // 测试
    @FormUrlEncoded
    @POST("/test")
    Observable<String> postTest(@FieldMap HashMap<String, Object> map);

    // 登录
    @FormUrlEncoded
    @POST("/user/login")
    Observable<String> postLogin(@FieldMap HashMap<String, Object> map);

    // 注册
    @FormUrlEncoded
    @POST("/user/reg")
    Observable<String> postRegister(@FieldMap HashMap<String, Object> map);

    // 获取短信验证码
    @FormUrlEncoded
    @POST("/index/smscode")
    Observable<String> postMessageCode(@FieldMap HashMap<String, Object> map);

    // 我关注的门店
    @FormUrlEncoded
    @POST("/shop/concern")
    Observable<String> postShops(@FieldMap HashMap<String, Object> map);

    // 我搜索的门店
    @FormUrlEncoded
    @POST("/shop/shoplist")
    Observable<String> postSearchShops(@FieldMap HashMap<String, Object> map);

    // 门店类型
    @FormUrlEncoded
    @POST("/shop/category")
    Observable<String> postShopCategory(@FieldMap HashMap<String, Object> map);


}
