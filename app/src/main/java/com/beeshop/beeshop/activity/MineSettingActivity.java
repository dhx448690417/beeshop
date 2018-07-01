package com.beeshop.beeshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.beeshop.beeshop.R;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.LogUtil;
import com.beeshop.beeshop.utils.SharedPreferenceUtil;
import com.beeshop.beeshop.utils.ToastUtils;
import com.beeshop.beeshop.utils.qiniu.PicUploadManager;
import com.beeshop.beeshop.views.CodeUtils;
import com.beeshop.beeshop.views.SelectPicImageView;
import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.qiniu.android.http.ResponseInfo;

import java.util.HashMap;
import java.util.List;

/**
 * Author：cooper
 * Time：  2018/5/17 下午10:00
 * Description：设置页面
 */
public class MineSettingActivity extends BaseActivity  {

    private static final String TAG = MineSettingActivity.class.getName();
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_sign_name)
    EditText etSignName;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_identify_code)
    EditText etIdentifyCode;
    @BindView(R.id.iv_identity_code)
    ImageView ivIdentityCode;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.iv_pic)
    protected SelectPicImageView ivPic;

    private String mNewPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_mine_setting);
        ButterKnife.bind(this);
        setTitleAndBackPressListener("更新信息");

        ivPic.setSelectPicClickCallBack(new SelectPicImageView.SelectPicClickCallBack() {
            @Override
            public void onClick(boolean isAddPic) {
                if (isAddPic) {
                    openPicSelecter(1, 1, null);
                } else {
                    openPicSelecter(1, 1, ivPic.getLocalMediaList());
                }
            }
        });
    }

    @OnClick({/*R.id.iv_identity_code, R.id.iv_add_image, */R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.iv_identity_code:
//                break;
//            case R.id.iv_add_image:
//                break;
            case R.id.tv_submit:
                if (verify()) {
                    uploadPicToQiNiu();
                }
                break;
        }
    }

    private boolean verify() {
        mNewPassword = etPassword.getText().toString();
        if (TextUtils.isEmpty(mNewPassword)) {
            ToastUtils.showToast("请输入新密码");
            return false;
        }else if (TextUtils.isEmpty(ivPic.getPicPath())) {
            ToastUtils.showToast("请选择上传图片");
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    ivPic.setPic(selectList);
                    break;
            }
        }
    }

    /**
     * 上传图片到七牛
     */
    private void uploadPicToQiNiu() {
        showProgress();
        PicUploadManager.getInstance().uploadPic(ivPic.getPicPath(), SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_QI_NIU_TOKEN,""), new PicUploadManager.UploadPicCallBack() {
            @Override
            public void uploadSuccess(String key, ResponseInfo info) {
                LogUtil.e("upload success key ==  "+key);
                LogUtil.e("upload success info ==  "+info.toString());
                submitMyInfo(key);
            }

            @Override
            public void uploadFailed(String key, ResponseInfo info) {
                ToastUtils.showToast("上传图片失败");
                hideProgress();
            }

            @Override
            public void uploadProgress(String key, double percent) {
//                uploadPicEnrity.progress = (int)(percent*100);
            }
        });
    }


    /**
     * 上传我的信息
     */
    private void submitMyInfo(String picKey) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN, ""));
        params.put("password", mNewPassword);
        params.put("headimg", picKey);
        HttpLoader.getInstance().submitMyInfo(params, mCompositeSubscription, new SubscriberCallBack(this, this) {

            @Override
            protected void onSuccess() {
                super.onSuccess();
                ToastUtils.showToast("修改信息成功");
                finish();
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
            }
        });
    }

}
