package com.beeshop.beeshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.model.PicListEntity;
import com.beeshop.beeshop.model.TransformUtil;
import com.beeshop.beeshop.utils.LogUtil;
import com.beeshop.beeshop.utils.SharedPreferenceUtil;
import com.beeshop.beeshop.utils.ToastUtils;
import com.beeshop.beeshop.utils.qiniu.PicUploadManager;
import com.beeshop.beeshop.views.HorizontalPicAddGallery;
import com.beeshop.beeshop.views.SelectPicImageView;
import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.qiniu.android.http.ResponseInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author : cooper
 * Time :  2018/6/16 下午3:10
 * Description :
 */
public abstract class PictureUploadActivity extends BaseActivity {

    @BindView(R.id.ll_pic_container)
    LinearLayout llPicContainer;
    @BindView(R.id.tv_remain_count)
    TextView tvRemainCount;
    @BindView(R.id.et_pic_title)
    protected EditText etPicTitle;
    @BindView(R.id.tv_upload_pic)
    TextView tvUploadPic;
    @BindView(R.id.iv_pic)
    protected SelectPicImageView ivPic;

    public static final int PIC_COUNT = 5;
    private String mPicInfo;

    private List<PicListEntity.ListBean> mPicList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_picture_upload);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {

        ivPic.setSelectPicClickCallBack(new SelectPicImageView.SelectPicClickCallBack() {
            @Override
            public void onClick(boolean isAddPic) {
                if (isAddPic) {
                    if (PIC_COUNT - mPicList.size() == 0) {
                        ToastUtils.showToast("已达到最大图片数");
                        return;
                    }
                    openPicSelecter(1, 1, null);
                } else {
                    openPicSelecter(1, 1, ivPic.getLocalMediaList());
                }
            }
        });

        tvRemainCount.setText("还可以添加"+PIC_COUNT+"张图片");
    }

    /**
     * 添加图片list
     * @param picList
     */
    protected void addPicList(List<PicListEntity.ListBean> picList) {
        mPicList.clear();
        mPicList.addAll(picList);
        initPicListView();
    }

    /**
     * 删除图片
     * @param position
     */
    protected void deletePic(int position) {
        mPicList.remove(position);
        initPicListView();
    }

    private void initPicListView() {
        tvRemainCount.setText("还可以添加"+(PIC_COUNT-mPicList.size())+"张图片");
        llPicContainer.removeAllViews();
        for (int i = 0; i < mPicList.size(); i++) {
            final PicListEntity.ListBean listBean = mPicList.get(i);

            View view = LayoutInflater.from(this).inflate(R.layout.item_picture_upload, new LinearLayout(this), false);
            ImageView iv_picture = view.findViewById(R.id.iv_picture);
            TextView tv_pic_title = view.findViewById(R.id.tv_pic_title);
            ImageView iv_delete = view.findViewById(R.id.iv_delete);
            ImageView iv_cover = view.findViewById(R.id.iv_cover);
            ImageView iv_set_cover = view.findViewById(R.id.iv_set_cover);

            if (i == 0) {
                iv_cover.setVisibility(View.VISIBLE);
                iv_set_cover.setVisibility(View.GONE);
            } else {
                iv_cover.setVisibility(View.GONE);
                iv_set_cover.setVisibility(View.VISIBLE);
            }

            Glide.with(this).load(listBean.getPath()).into(iv_picture);
            tv_pic_title.setText(listBean.getTitle());

            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 删除照片
                    deleteThisPic(listBean.getId());
                }
            });

            iv_set_cover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 设置为封面接口
                    setThisIsCover(listBean.getId());
                }
            });

            llPicContainer.addView(view);
        }

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

    @OnClick({R.id.tv_upload_pic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_upload_pic:
                mPicInfo = etPicTitle.getText().toString();

                if (TextUtils.isEmpty(ivPic.getPicPath())) {
                    ToastUtils.showToast("请选择上传图片");
                    return;
                }

                if (TextUtils.isEmpty(mPicInfo)) {
                    ToastUtils.showToast("请填写图片标题");
                    return;
                }

                uploadPicToQiNiu();
                break;
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
                uploadServer(key,mPicInfo);
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

    protected abstract void uploadServer(String key,String title);
    protected abstract void deleteThisPic(int id);
    protected abstract void setThisIsCover(int id);


}
