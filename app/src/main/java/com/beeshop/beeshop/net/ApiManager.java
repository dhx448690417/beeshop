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

    // 门店详情
    @FormUrlEncoded
    @POST("/shop/shophome")
    Observable<String> postShopDetail(@FieldMap HashMap<String, Object> map);

    // 门店 关注/取消关注
    @FormUrlEncoded
    @POST("/shop/setconcern")
    Observable<String> postCollect(@FieldMap HashMap<String, Object> map);

    // 产品详情
    @FormUrlEncoded
    @POST("/shop/productinfo")
    Observable<String> postProductInfo(@FieldMap HashMap<String, Object> map);

    // 产品详情
    @FormUrlEncoded
    @POST("/shop/customer")
    Observable<String> postCustomer(@FieldMap HashMap<String, Object> map);

    // 会员分类
    @FormUrlEncoded
    @POST("/shop/memberclass")
    Observable<String> postVipTypeList(@FieldMap HashMap<String, Object> map);

    // 添加会员分类
    @FormUrlEncoded
    @POST("/shop/addmemberclass")
    Observable<String> postAddVipType(@FieldMap HashMap<String, Object> map);

    // 便捷会员分类
    @FormUrlEncoded
    @POST("/shop/upmemberclass")
    Observable<String> postUpdateVipType(@FieldMap HashMap<String, Object> map);

    // 会员列表
    @FormUrlEncoded
    @POST("/shop/shopmember")
    Observable<String> postVipList(@FieldMap HashMap<String, Object> map);

    // 添加会员
    @FormUrlEncoded
    @POST("/shop/addmember")
    Observable<String> postAddVip(@FieldMap HashMap<String, Object> map);

    // 更新会员
    @FormUrlEncoded
    @POST("/shop/upmember")
    Observable<String> postUpdateVip(@FieldMap HashMap<String, Object> map);

    // 充值
    @FormUrlEncoded
    @POST("/shop/memberrecharge")
    Observable<String> postRecharge(@FieldMap HashMap<String, Object> map);

    // 消费
    @FormUrlEncoded
    @POST("/shop/memberconsume")
    Observable<String> postPay(@FieldMap HashMap<String, Object> map);

    // 消费记录
    @FormUrlEncoded
    @POST("/shop/memberconsumehistory")
    Observable<String> postPayHistoryRecord(@FieldMap HashMap<String, Object> map);

    // 充值记录
    @FormUrlEncoded
    @POST("/shop/memberrechargehistory")
    Observable<String> postRechargeHistoryRecord(@FieldMap HashMap<String, Object> map);

    // 开店申请
    @FormUrlEncoded
    @POST("/shop/setup")
    Observable<String> postRegisterShop(@FieldMap HashMap<String, Object> map);

    // 获取七牛上传图片token
    @FormUrlEncoded
    @POST("/qiniu/uploadToken")
    Observable<String> postQiNiuToken(@FieldMap HashMap<String, Object> map);

    // 获取我的店铺信息
    @FormUrlEncoded
    @POST("/shop/index")
    Observable<String> postShopInfo(@FieldMap HashMap<String, Object> map);

    // 更新我的店铺信息
    @FormUrlEncoded
    @POST("/shop/update")
    Observable<String> postShopInfoUpdate(@FieldMap HashMap<String, Object> map);

    // 获取店铺店铺照片
    @FormUrlEncoded
    @POST("/album/index")
    Observable<String> postShopPicList(@FieldMap HashMap<String, Object> map);

    // 添加店铺图片
    @FormUrlEncoded
    @POST("/album/addpic")
    Observable<String> postAddShopPic(@FieldMap HashMap<String, Object> map);

    // 删除店铺图片
    @FormUrlEncoded
    @POST("/album/delpic")
    Observable<String> postDeleteShopPic(@FieldMap HashMap<String, Object> map);

    // 设置店铺图片为封面
    @FormUrlEncoded
    @POST("/album/setcover")
    Observable<String> postSetShopCoverPic(@FieldMap HashMap<String, Object> map);

    // 添加产品
    @FormUrlEncoded
    @POST("/product/addone")
    Observable<String> postAddProduct(@FieldMap HashMap<String, Object> map);

    // 产品列表
    @FormUrlEncoded
    @POST("/product/index")
    Observable<String> postProductList(@FieldMap HashMap<String, Object> map);

    // 更新产品
    @FormUrlEncoded
    @POST("/product/update")
    Observable<String> postUpdateProduct(@FieldMap HashMap<String, Object> map);


    // 获取产品list照片
    @FormUrlEncoded
    @POST("/product/album")
    Observable<String> postProductPicList(@FieldMap HashMap<String, Object> map);

    // 添加产品图片
    @FormUrlEncoded
    @POST("/product/addpic")
    Observable<String> postAddProductPic(@FieldMap HashMap<String, Object> map);

    // 删除产品图片
    @FormUrlEncoded
    @POST("/product/delpic")
    Observable<String> postDeleteProductPic(@FieldMap HashMap<String, Object> map);

    // 设置产品图片为封面
    @FormUrlEncoded
    @POST("/product/setcover")
    Observable<String> postSetProductCoverPic(@FieldMap HashMap<String, Object> map);


    // ===============================  订单接口  =====================================

    // 获取我的订单列表
    @FormUrlEncoded
    @POST("/trade/userorder")
    Observable<String> postMyOrderList(@FieldMap HashMap<String, Object> map);

    // 获取商铺订单列表
    @FormUrlEncoded
    @POST("/trade/shoporder")
    Observable<String> postShopOrderList(@FieldMap HashMap<String, Object> map);

    // 下单
    @FormUrlEncoded
    @POST("/trade/createorder")
    Observable<String> postCreateOrder(@FieldMap HashMap<String, Object> map);

    // 支付
    @FormUrlEncoded
    @POST("/trade/pay")
    Observable<String> postConfirmPay(@FieldMap HashMap<String, Object> map);

    // 修改价格
    @FormUrlEncoded
    @POST("/trade/setprice")
    Observable<String> postModifyPrice(@FieldMap HashMap<String, Object> map);

    // 设置订单为发货状态
    @FormUrlEncoded
    @POST("/trade/delivery")
    Observable<String> postSetOrderState(@FieldMap HashMap<String, Object> map);

    // ===============================  电台接口  =====================================

    // 获取商铺排序
    @FormUrlEncoded
    @POST("/station/shopcharts")
    Observable<String> postShopSortList(@FieldMap HashMap<String, Object> map);

    // 获取附近广播
    @FormUrlEncoded
    @POST("/station/broadcast")
    Observable<String> postNearbyBroadcast(@FieldMap HashMap<String, Object> map);

    // 发布广播
    @FormUrlEncoded
    @POST("/station/publish")
    Observable<String> postPublishBroadcast(@FieldMap HashMap<String, Object> map);


    // ===============================  我的接口  =====================================

    // 我的广播
    @FormUrlEncoded
    @POST("/user/broadcast")
    Observable<String> postMyBroadcast(@FieldMap HashMap<String, Object> map);

    // 我加入的会员
    @FormUrlEncoded
    @POST("/user/member")
    Observable<String> postMyJoinedVips(@FieldMap HashMap<String, Object> map);

    // 我加入的会员 充值记录
    @FormUrlEncoded
    @POST("/user/memberrecharge")
    Observable<String> postMyJoinedVipRechargeRecord(@FieldMap HashMap<String, Object> map);

    // 我加入的会员 消费记录
    @FormUrlEncoded
    @POST("/user/memberconsume")
    Observable<String> postMyJoinedVipConsumeRecord(@FieldMap HashMap<String, Object> map);

    // 我的广播卡
    @FormUrlEncoded
    @POST("/user/broadcastcard")
    Observable<String> postMyBroadcastCard(@FieldMap HashMap<String, Object> map);

    // 上传我的信息
    @FormUrlEncoded
    @POST("/user/update")
    Observable<String> postSubmitMyInfo(@FieldMap HashMap<String, Object> map);

    // 获取我的信息
    @FormUrlEncoded
    @POST("/user/index")
    Observable<String> postMyInfo(@FieldMap HashMap<String, Object> map);


    // 我的地址
    @FormUrlEncoded
    @POST("/address/index")
    Observable<String> postMyAddress(@FieldMap HashMap<String, Object> map);

    // 增加地址
    @FormUrlEncoded
    @POST("/address/addone")
    Observable<String> postAddAddress(@FieldMap HashMap<String, Object> map);

    // 更新地址
    @FormUrlEncoded
    @POST("/address/update")
    Observable<String> postUpdateAddress(@FieldMap HashMap<String, Object> map);

}
