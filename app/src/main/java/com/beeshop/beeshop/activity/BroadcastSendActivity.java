package com.beeshop.beeshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.model.BroadcastCardEntity;
import com.beeshop.beeshop.model.ShopCategoryEntity;
import com.beeshop.beeshop.model.TransformUtil;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.LogUtil;
import com.beeshop.beeshop.utils.SharedPreferenceUtil;
import com.beeshop.beeshop.utils.ToastUtils;
import com.beeshop.beeshop.utils.qiniu.PicUploadManager;
import com.beeshop.beeshop.views.HorizontalPicAddGallery;
import com.beeshop.beeshop.views.SelectPicImageView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.qiniu.android.http.ResponseInfo;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author : cooper
 * Time :  2018/6/11 上午10:47
 * Description : 我要广播
 */
public class BroadcastSendActivity extends BaseActivity {

    @BindView(R.id.et_broadcast_title)
    EditText etBroadcastTitle;
    @BindView(R.id.et_broadcast_content)
    EditText etBroadcastContent;
    @BindView(R.id.tv_introduce_number)
    TextView tvIntroduceNumber;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.ns_broadcast_card)
    NiceSpinner nsBroadcastCard;
    @BindView(R.id.iv_pic)
    SelectPicImageView ivPic;

    private List<BroadcastCardEntity.ListBean> mBeanList = new ArrayList<>();
    private int mBroadcastCardId;
    private String mTitle;
    private String mContent;
    private String mPicPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_broadcast_send);
        ButterKnife.bind(this);
        setTitleAndBackPressListener("我要广播");
        initView();
    }

    private void initView() {

        etBroadcastContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvIntroduceNumber.setText(s.length() + "/200");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        nsBroadcastCard.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mBroadcastCardId = mBeanList.get(position).getId();
            }
        });

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

        getMyBroadcastCardList();
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

    @OnClick({R.id.tv_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_send:
                if (verify()) {
                    showProgress();
                    if (!TextUtils.isEmpty(ivPic.getPicPath())) {
                        uploadPicToQiNiu();
                    } else {
                        publishBroadcast();
                    }
                }
                break;
        }
    }

    private boolean verify() {
        mTitle = etBroadcastTitle.getText().toString();
        mContent = etBroadcastContent.getText().toString();
        if (TextUtils.isEmpty(mTitle)) {
            ToastUtils.showToast("请输入广播标题");
            return false;
        }else if (TextUtils.isEmpty(mContent)) {
            ToastUtils.showToast("请输入广播内容");
            return false;
        }
        return true;
    }

    /**
     * 上传图片到七牛
     */
    private void uploadPicToQiNiu() {
        PicUploadManager.getInstance().uploadPic(ivPic.getPicPath(), SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_QI_NIU_TOKEN,""), new PicUploadManager.UploadPicCallBack() {
            @Override
            public void uploadSuccess(String key, ResponseInfo info) {
                LogUtil.e("upload success key ==  "+key);
                LogUtil.e("upload success info ==  "+info.toString());
                mPicPath = key;
                publishBroadcast();
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
     * 发布广播
     *
     */
    private void publishBroadcast() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN, ""));
        params.put("title", mTitle);
        params.put("content", mContent);
        params.put("card_id", mBroadcastCardId);
        params.put("img", mPicPath);
        HttpLoader.getInstance().publishBroadcast(params, mCompositeSubscription, new SubscriberCallBack(this, this) {

            @Override
            protected void onSuccess(ResponseEntity response) {
                super.onSuccess(response);
                ToastUtils.showToast("发布广播成功");
                BroadcastSendActivity.this.finish();
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
            }
        });
    }

    /**
     * 获取我的广播卡
     */
    private void getMyBroadcastCardList() {
        HashMap<String, Object> params1 = new HashMap<>();
        params1.put("token", mToken);
        HttpLoader.getInstance().getMyBroadcastCardList(params1, mCompositeSubscription, new SubscriberCallBack<BroadcastCardEntity>(this,this) {

            @Override
            protected void onSuccess(BroadcastCardEntity response) {
                super.onSuccess(response);
                if (response.getList().size() > 0) {
                    mBeanList.clear();
                    mBeanList.addAll(response.getList());
                    List<String> dataset = new ArrayList<>();
                    for (BroadcastCardEntity.ListBean listBean : response.getList()) {
                        dataset.add(listBean.getTitle());
                    }
                    nsBroadcastCard.attachDataSource(dataset);
                    mBroadcastCardId = response.getList().get(0).getId();
                }
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
            }

        });
    }
}
