package com.beeshop.beeshop.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.beeshop.beeshop.model.PicListEntity;
import com.beeshop.beeshop.model.ShopMineInfoEntity;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.SharedPreferenceUtil;
import com.beeshop.beeshop.utils.ToastUtils;

import java.util.HashMap;

/**
 * Author : cooper
 * Time :  2018/6/22 下午4:17
 * Description : 店铺图片上传页面
 */
public class ShopPictureUploadActivity extends PictureUploadActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleAndBackPressListener("店铺图片管理");
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPicList();
    }

    @Override
    protected void uploadServer(String key,String title) {
        addShopPic(key,title);
    }

    @Override
    protected void deleteThisPic(int id) {
        deleteShopPic(id+"");
    }

    @Override
    protected void setThisIsCover(int id) {
        setShopCoverPic(id + "");
    }

    /**
     * 获取店铺照片别表
     */
    private void getPicList() {
        HashMap<String, Object> params1 = new HashMap<>();
        params1.put("token", mToken);
        HttpLoader.getInstance().getShopPicList(params1, mCompositeSubscription, new SubscriberCallBack<PicListEntity>(this, this) {

            @Override
            protected void onSuccess(PicListEntity response) {
                super.onSuccess(response);
                addPicList(response.getList());
                hideProgress();
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
                hideProgress();
            }

        });
    }

    /**
     * 上传店铺照片
     *
     */
    private void addShopPic(String key,String title) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN, ""));
        params.put("title", title);
        params.put("key", key);
        HttpLoader.getInstance().addShopPic(params, mCompositeSubscription, new SubscriberCallBack(this, this) {

            @Override
            protected void onSuccess(ResponseEntity response) {
                super.onSuccess(response);
                getPicList();
                ivPic.setAddView();
                etPicTitle.setText("");
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
            }
        });
    }

    /**
     * 删除店铺照片
     *
     */
    private void deleteShopPic(String id) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN, ""));
        params.put("id", id);
        HttpLoader.getInstance().deleteShopPic(params, mCompositeSubscription, new SubscriberCallBack(this, this) {

            @Override
            protected void onSuccess(ResponseEntity response) {
                super.onSuccess(response);
                getPicList();
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
            }
        });
    }

    /**
     * 设置店铺照片封面
     *
     */
    private void setShopCoverPic(String id) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN, ""));
        params.put("id", id);
        HttpLoader.getInstance().setShopCoverPic(params, mCompositeSubscription, new SubscriberCallBack(this, this) {

            @Override
            protected void onSuccess(ResponseEntity response) {
                super.onSuccess(response);
                getPicList();
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
            }
        });
    }
}
