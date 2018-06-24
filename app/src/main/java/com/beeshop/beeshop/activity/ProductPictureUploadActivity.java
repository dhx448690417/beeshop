package com.beeshop.beeshop.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.beeshop.beeshop.model.PicListEntity;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.SharedPreferenceUtil;
import com.beeshop.beeshop.utils.ToastUtils;

import java.util.HashMap;

/**
 * Author : cooper
 * Time :  2018/6/22 下午4:17
 * Description : 产品图片上传页面
 */
public class ProductPictureUploadActivity extends PictureUploadActivity {

    public static final String PARAM_PRODUCT_ID = "param_product_id";
    private int mProductId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleAndBackPressListener("产品图片管理");

        mProductId = getIntent().getIntExtra(PARAM_PRODUCT_ID,-1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProductPicList();
    }

    @Override
    protected void uploadServer(String key,String title) {
        addProductPic(key,title);
    }

    @Override
    protected void deleteThisPic(int id) {
        deleteProductPic(id+"");
    }

    @Override
    protected void setThisIsCover(int id) {
        setProductCoverPic(id + "");
    }

    /**
     * 获取店铺照片别表
     */
    private void getProductPicList() {
        HashMap<String, Object> params1 = new HashMap<>();
        params1.put("token", mToken);
        params1.put("product_id", mProductId);
        HttpLoader.getInstance().getProductPicList(params1, mCompositeSubscription, new SubscriberCallBack<PicListEntity>(this, this) {

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
    private void addProductPic(String key,String title) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN, ""));
        params.put("title", title);
        params.put("key", key);
        params.put("product_id", mProductId);
        HttpLoader.getInstance().addProductPic(params, mCompositeSubscription, new SubscriberCallBack(this, this) {

            @Override
            protected void onSuccess(ResponseEntity response) {
                super.onSuccess(response);
                getProductPicList();
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
    private void deleteProductPic(String id) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN, ""));
        params.put("id", id);
        HttpLoader.getInstance().deleteProductPic(params, mCompositeSubscription, new SubscriberCallBack(this, this) {

            @Override
            protected void onSuccess(ResponseEntity response) {
                super.onSuccess(response);
                getProductPicList();
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
    private void setProductCoverPic(String id) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN, ""));
        params.put("id", id);
        HttpLoader.getInstance().setProductCoverPic(params, mCompositeSubscription, new SubscriberCallBack(this, this) {

            @Override
            protected void onSuccess(ResponseEntity response) {
                super.onSuccess(response);
                getProductPicList();
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
            }
        });
    }
}
