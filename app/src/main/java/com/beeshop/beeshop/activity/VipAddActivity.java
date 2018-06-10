package com.beeshop.beeshop.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.model.VipTypeEntity;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.SharedPreferenceUtil;
import com.beeshop.beeshop.utils.ToastUtils;

import com.beeshop.beeshop.views.CodeUtils;
import org.angmarch.views.NiceSpinner;

import java.util.*;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author : cooper
 * Time :  2018/5/24 下午2:52
 * Description : 添加会员
 */
public class VipAddActivity extends BaseActivity {

    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.ns_vip_type)
    NiceSpinner nsVipType;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.tv_vip_add)
    TextView tvVipAdd;

    private String mPhone;
    private String mRemark;
    private String mVipName;
    private int mMemberClassId;

    protected List<VipTypeEntity.ListBean> mVipTypeList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_vip_add);
        ButterKnife.bind(this);
        setTitleAndBackPressListener("添加会员");

        initView();
    }

    private void initView() {

        nsVipType.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mMemberClassId = mVipTypeList.get(position).getId();
            }
        });

        tvVipAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verify()) {
                    addVip();
                }
            }
        });

        getVipTypeList();
    }

    private boolean verify() {
        mPhone = etPhoneNumber.getText().toString();
        mRemark = etPhoneNumber.getText().toString();
        if (TextUtils.isEmpty(mPhone)) {
            ToastUtils.showToast("请输入手机号");
            return false;
        } else if (mPhone.length() != 11) {
            ToastUtils.showToast("请输入正确手机号");
            return false;
        } else if (TextUtils.isEmpty(mRemark)) {
            ToastUtils.showToast("请输入备注");
            return false;
        }
        return true;
    }

    /**
     * 添加会员
     *
     */
    private void addVip() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN, ""));
        params.put("member_class_id", mMemberClassId);
        params.put("phone", mPhone);
        params.put("remark", mRemark);
//        params.put("name", mVipName);
        HttpLoader.getInstance().addVip(params, mCompositeSubscription, new SubscriberCallBack(this, this) {

            @Override
            protected void onSuccess(ResponseEntity response) {
                super.onSuccess(response);
                ToastUtils.showToast("添加会员成功");
                VipAddActivity.this.finish();
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
            }
        });
    }

    private void getVipTypeList() {
        HashMap<String, Object> params1 = new HashMap<>();
        params1.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN, ""));
        HttpLoader.getInstance().getVipTypeList(params1, mCompositeSubscription, new SubscriberCallBack<VipTypeEntity>(this, this) {

            @Override
            protected void onSuccess(VipTypeEntity response) {
                super.onSuccess(response);
                if (response.getList().size() > 0) {
                    mVipTypeList.addAll(response.getList());
                    List<String> dataset = new ArrayList<>();
                    for (int i = 0; i < response.getList().size(); i++) {
                        VipTypeEntity.ListBean listBean = response.getList().get(i);
                        dataset.add(listBean.getTitle());
                    }
                    nsVipType.attachDataSource(dataset);
                    mMemberClassId = mVipTypeList.get(0).getId();
                    hideNoContentView();
                } else {
                    showNoContentView();
                }
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                if (errorBean.getCode() == 410) {
                    showNoContentView();
                }
                ToastUtils.showToast(errorBean.getMsg());
            }

            @Override
            protected void onNetFailure(ResponseEntity errorBean) {
                super.onNetFailure(errorBean);
                showNoNetWork();
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
            }
        });
    }
}
