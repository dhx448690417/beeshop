package com.beeshop.beeshop.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.SharedPreferenceUtil;
import com.beeshop.beeshop.utils.ToastUtils;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author : cooper
 * Time :  2018/5/24 下午2:52
 * Description :
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
    private String mMemberClassId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_vip_add);
        ButterKnife.bind(this);
        setTitleAndBackPressListener("添加会员");

        initView();
    }

    private void initView() {
        List<String> dataset = new LinkedList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
        nsVipType.attachDataSource(dataset);

        tvVipAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
            }
        });
    }
}
