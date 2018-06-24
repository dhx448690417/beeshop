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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.model.ShopCategoryEntity;
import com.beeshop.beeshop.model.ShopMineInfoEntity;
import com.beeshop.beeshop.model.TransformUtil;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.LogUtil;
import com.beeshop.beeshop.utils.SharedPreferenceUtil;
import com.beeshop.beeshop.utils.ToastUtils;
import com.beeshop.beeshop.utils.qiniu.PicUploadManager;
import com.beeshop.beeshop.views.HorizontalPicAddGallery;
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
    @BindView(R.id.ns_shop_type)
    NiceSpinner nsShopType;
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
    @BindView(R.id.et_shop_describe)
    EditText etShopDescribe;
    @BindView(R.id.tv_introduce_number)
    TextView tvIntroduceNumber;
    @BindView(R.id.rl_business_license_code)
    RelativeLayout rlBusinessLicenseCode;
    @BindView(R.id.ll_license_img_upload)
    LinearLayout llLicenseImgUpload;
    @BindView(R.id.rb_people)
    RadioButton rbPeople;
    @BindView(R.id.rb_company)
    RadioButton rbCompany;

    private String title; // 门点名称
    private int type = 1; // 门店类型1个人2企业
    private int category; // 门店类别
    private String id_no; // 身份证号
    private String id_name; // 身份证姓名
    private String license_no; // 营业执照号，当type=2时必填
    private String license_img; // 营业执照图片，当type=2时必填
    private String contact_tel; // 门店联系电话
    private String address; // 门店地址
    private String business; // 主营业务

    private List<ShopCategoryEntity.ListBean> mShopCategoryEntityList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_shop_info_edit);
        ButterKnife.bind(this);
        setTitleAndBackPressListener("商铺信息编辑");

        initView();
        getShopCategory();
    }

    private void initView() {
        nsShopType.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                category = mShopCategoryEntityList.get(position).getId();
            }
        });
        rgShopType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_people:
                        type = 1;
                        rlBusinessLicenseCode.setVisibility(View.GONE);
                        llLicenseImgUpload.setVisibility(View.GONE);
                        break;
                    case R.id.rb_company:
                        type = 2;
                        rlBusinessLicenseCode.setVisibility(View.VISIBLE);
                        llLicenseImgUpload.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        etShopDescribe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvIntroduceNumber.setText(s.length() + "/120");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        hpagAddPic.setPicAddListener(new HorizontalPicAddGallery.PicAddListener() {
            @Override
            public void deletePic(int position) {

            }

            @Override
            public void retryUploadPic(int position) {

            }

            @Override
            public void picClicked(int position) {
                PictureSelector.create(ShopInfoEditActivity.this).externalPicturePreview(position, hpagAddPic.getChoosedPic());
            }

            @Override
            public void addPic() {
                if (!TextUtils.isEmpty(license_img)) {
                    ToastUtils.showToast("只能上传一张图片");
                    return;
                }
                PictureSelector.create(ShopInfoEditActivity.this)
                        .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .maxSelectNum(1)// 最大图片选择数量
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

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verify()) {
                    if (SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_OPEN_SHOP, -1) == 1) {
                        updateShopInfo();
                    } else {
                        registerShop();
                    }
                }
            }
        });
    }

    private boolean verify() {
        title = etShopName.getText().toString();
        contact_tel = etPhoneNumber.getText().toString();
        id_name = etPeopleName.getText().toString();
        id_no = etIdNumber.getText().toString();
        license_no = etBusinessLicenseCode.getText().toString();
        address = etAddress.getText().toString();
        business = etShopDescribe.getText().toString();
        if (TextUtils.isEmpty(title)) {
            ToastUtils.showToast("请输入商铺名称");
            return false;
        } else if (TextUtils.isEmpty(contact_tel)) {
            ToastUtils.showToast("请输入手机号");
            return false;
        } else if (contact_tel.length() != 11) {
            ToastUtils.showToast("请输入正确手机号");
            return false;
        } else if (TextUtils.isEmpty(id_name)) {
            ToastUtils.showToast("请输入法人姓名");
            return false;
        } else if (TextUtils.isEmpty(id_no)) {
            ToastUtils.showToast("请输入身份证号");
            return false;
        } else if (type == 2 && TextUtils.isEmpty(license_no)) {
            ToastUtils.showToast("请输入营业执照号");
            return false;
        } else if (TextUtils.isEmpty(address)) {
            ToastUtils.showToast("请输入商铺地址");
            return false;
        } else if (TextUtils.isEmpty(business)) {
            ToastUtils.showToast("请输入主要经营业务");
            return false;
        } else if (type == 2 && TextUtils.isEmpty(license_img)) {
            ToastUtils.showToast("请上传营业执照图片");
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
                    hpagAddPic.setPicList(TransformUtil.transUploadPicEnrity(selectList));
                    for (int i = 0; i < hpagAddPic.getPicList().size() - 1; i++) {
                        final HorizontalPicAddGallery.UploadPicEnrity uploadPicEnrity = hpagAddPic.getPicList().get(i);
                        PicUploadManager.getInstance().uploadPic(uploadPicEnrity.localMedia.getPath(), SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_QI_NIU_TOKEN, ""), new PicUploadManager.UploadPicCallBack() {
                            @Override
                            public void uploadSuccess(String key, ResponseInfo info) {
                                LogUtil.e("upload success key ==  " + key);
                                LogUtil.e("upload success info ==  " + info.toString());
                                license_img = key;
                            }

                            @Override
                            public void uploadFailed(String key, ResponseInfo info) {
                                LogUtil.e("upload failed key ==  " + key);
                                uploadPicEnrity.isFailed = 1;
                                hpagAddPic.refreshGallery();
                                license_img = "";
                            }

                            @Override
                            public void uploadProgress(String key, double percent) {
                                uploadPicEnrity.progress = (int) (percent * 100);
                                hpagAddPic.refreshGallery();
                            }
                        });
                    }
                    break;
            }
        }
    }

    private void setShopInfo(ShopMineInfoEntity shopMineInfoEntity) {

        etShopName.setText(shopMineInfoEntity.getTitle());
        etPeopleName.setText(shopMineInfoEntity.getId_name());
        etIdNumber.setText(shopMineInfoEntity.getId_no());
        etBusinessLicenseCode.setText(shopMineInfoEntity.getLicense_no());

        etPhoneNumber.setText(shopMineInfoEntity.getContact());
        etAddress.setText(shopMineInfoEntity.getAddress());
        etShopDescribe.setText(shopMineInfoEntity.getBusiness());

        type = shopMineInfoEntity.getType();
        if (shopMineInfoEntity.getType() == 1) {
            rbPeople.setChecked(true);
            rbCompany.setChecked(false);
        } else if (shopMineInfoEntity.getType() == 2) {
            rbPeople.setChecked(false);
            rbCompany.setChecked(true);
        }

        HorizontalPicAddGallery.UploadPicEnrity uploadPicEnrity = new HorizontalPicAddGallery.UploadPicEnrity();
        uploadPicEnrity.path = shopMineInfoEntity.getLicense_img();
        uploadPicEnrity.isDelete = 1;
        uploadPicEnrity.isFailed = 0;
        uploadPicEnrity.progress = 100;
        uploadPicEnrity.isAddPic = false;
        hpagAddPic.setPic(uploadPicEnrity);
        nsShopType.setSelectedIndex(shopMineInfoEntity.getCategory());

        if (shopMineInfoEntity.getStatus() == 2) { // 审核已通过时 开店申请时填写的信息只允许修改地址、经营内容、联系电话;
            etBusinessLicenseCode.setEnabled(false);
            etPeopleName.setEnabled(false);
            etIdNumber.setEnabled(false);
            etShopName.setEnabled(false);
            nsShopType.setEnabled(false);
            rbCompany.setEnabled(false);
            rbPeople.setEnabled(false);
        } else {
            etBusinessLicenseCode.setEnabled(true);
            etPeopleName.setEnabled(true);
            etIdNumber.setEnabled(true);
            etShopName.setEnabled(true);
            nsShopType.setEnabled(true);
            rbCompany.setEnabled(true);
            rbPeople.setEnabled(true);
        }
    }

    /**
     * 开店申请
     *
     */
    private void registerShop() {

        HashMap<String, Object> params = new HashMap<>();
        params.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN, ""));
        params.put("title", title);
        params.put("type", type);
        params.put("category", category);
        params.put("id_no", id_no);
        params.put("id_name", id_name);
        params.put("license_no", license_no);
        params.put("license_img", license_img);
        params.put("contact_tel", contact_tel);
        params.put("address", address);
        params.put("business", business);
        HttpLoader.getInstance().registerShop(params, mCompositeSubscription, new SubscriberCallBack(this, this) {

            @Override
            protected void onSuccess(ResponseEntity response) {
                super.onSuccess(response);
                ToastUtils.showToast("申请已提交，请等待审核。");
                ShopInfoEditActivity.this.finish();
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
            }
        });
    }


    /**
     * 更新店铺信息
     *
     */
    private void updateShopInfo() {

        HashMap<String, Object> params = new HashMap<>();
        params.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN, ""));
        params.put("title", title);
        params.put("type", type);
        params.put("category", category);
        params.put("id_no", id_no);
        params.put("id_name", id_name);
        params.put("license_no", license_no);
        params.put("license_img", license_img);
        params.put("contact_tel", contact_tel);
        params.put("address", address);
        params.put("business", business);
        HttpLoader.getInstance().updateShopInfo(params, mCompositeSubscription, new SubscriberCallBack(this, this) {

            @Override
            protected void onSuccess(ResponseEntity response) {
                super.onSuccess(response);
                ToastUtils.showToast("更新已提交，请等待审核。");
                ShopInfoEditActivity.this.finish();
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
            }
        });
    }

    /**
     * 获取店铺信息
     */
    private void getShopInfo() {
        HashMap<String, Object> params1 = new HashMap<>();
        params1.put("token", mToken);
        HttpLoader.getInstance().getShopInfo(params1, mCompositeSubscription, new SubscriberCallBack<ShopMineInfoEntity>(this, this) {

            @Override
            protected void onSuccess(ShopMineInfoEntity response) {
                super.onSuccess(response);
                setShopInfo(response);
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
     * 店铺类型
     */
    private void getShopCategory() {
        showProgress();
        HashMap<String, Object> params1 = new HashMap<>();
//        params1.put("category", "");
        HttpLoader.getInstance().getShopCategory(params1, mCompositeSubscription, new SubscriberCallBack<ShopCategoryEntity>(this, this) {

            @Override
            protected void onSuccess(ShopCategoryEntity response) {
                super.onSuccess(response);
                if (response.getList().size() > 0) {
                    mShopCategoryEntityList.clear();
                    mShopCategoryEntityList.addAll(response.getList());
                    category = response.getList().get(0).getId();
                    List<String> dataset = new ArrayList<>();
                    for (ShopCategoryEntity.ListBean listBean : response.getList()) {
                        dataset.add(listBean.getName());
                    }
                    nsShopType.attachDataSource(dataset);
                    if (SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_OPEN_SHOP, -1) == 1) {
                        getShopInfo();
                    } else {
                        hideProgress();
                    }
                }
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
                hideProgress();
            }
        });
    }


}
