package com.beeshop.beeshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.beeshop.beeshop.R;
import com.beeshop.beeshop.model.TransformUtil;
import com.beeshop.beeshop.utils.LogUtil;
import com.beeshop.beeshop.utils.qiniu.PicUploadManager;
import com.beeshop.beeshop.views.HorizontalPicAddGallery;
import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.qiniu.android.http.ResponseInfo;

import java.util.List;

/**
 * Author：cooper
 * Time：  2018/5/20 上午11:58
 * Description：
 */
public class ShopInfoEditActivity extends BaseActivity {

    @BindView(R.id.et_shop_name)
    EditText etShopName;
    @BindView(R.id.rg_shop_type)
    RadioGroup rgShopType;
    @BindView(R.id.sp_shop_type)
    Spinner spShopType;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.et_people_name)
    EditText etPeopleName;
    @BindView(R.id.et_id_number)
    EditText etIdNumber;
    @BindView(R.id.et_business_license_code)
    EditText etBusinessLicenseCode;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.hpag_add_pic)
    HorizontalPicAddGallery hpagAddPic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_shop_info_edit);
        ButterKnife.bind(this);
        setTitleAndBackPressListener("商铺信息编辑");

        initView();
    }

    private void initView() {
        hpagAddPic.setPicAddListener(new HorizontalPicAddGallery.PicAddListener() {
            @Override
            public void deletePic(int position) {

            }

            @Override
            public void retryUploadPic(int position) {

            }

            @Override
            public void picClicked(int position) {
                PictureSelector.create(ShopInfoEditActivity.this).externalPicturePreview(position,hpagAddPic.getChoosedPic());
            }

            @Override
            public void addPic() {
                PictureSelector.create(ShopInfoEditActivity.this)
                        .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .maxSelectNum(2)// 最大图片选择数量
                        .minSelectNum(1)// 最小选择数量
                        .imageSpanCount(3)// 每行显示个数
                        .selectionMedia(hpagAddPic.getChoosedPic())
                        .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                        .previewImage(true)// 是否可预览图片
                        .previewVideo(false)// 是否可预览视频
                        .enablePreviewAudio(false) // 是否可播放音频
                        .isCamera(true)// 是否显示拍照按钮
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                        .setOutputCameraPath("/BeeShopCache")// 自定义拍照保存路径
                        .enableCrop(false)// 是否裁剪
                        .compress(true)// 是否压缩
                        .synOrAsy(true)//同步true或异步false 压缩 默认同步
                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                        .withAspectRatio(16, 9)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                        .isGif(false)// 是否显示gif图片
                        .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                        .circleDimmedLayer(false)// 是否圆形裁剪
                        .openClickSound(true)// 是否开启点击声音
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    hpagAddPic.setPicList(TransformUtil.transUploadPicEnrity(selectList));
                    for (int i = 0; i < hpagAddPic.getPicList().size()-1; i++) {
                        final HorizontalPicAddGallery.UploadPicEnrity uploadPicEnrity = hpagAddPic.getPicList().get(i);
                        PicUploadManager.getInstance().uploadPic(uploadPicEnrity.localMedia.getPath(), new PicUploadManager.UploadPicCallBack() {
                            @Override
                            public void uploadSuccess(String key, ResponseInfo info) {
                                LogUtil.e("upload success key ==  "+key);
                                LogUtil.e("upload success info ==  "+info.toString());
                            }

                            @Override
                            public void uploadFailed(String key, ResponseInfo info) {
                                LogUtil.e("upload failed key ==  "+key);
                                uploadPicEnrity.isFailed = 1;
                                hpagAddPic.refreshGallery();
                            }

                            @Override
                            public void uploadProgress(String key, double percent) {
                                uploadPicEnrity.progress = (int)(percent*100);
                                hpagAddPic.refreshGallery();
                            }
                        });
                    }
                    break;
            }
        }
    }


}
