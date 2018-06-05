package com.beeshop.beeshop.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.SharedPreferenceUtil;
import com.beeshop.beeshop.utils.ToastUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author : cooper
 * Time :  2018/5/24 上午10:36
 * Description : 添加/更新会员类别
 */
public class VipTypeUpdateActivity extends BaseActivity {


    @BindView(R.id.et_vip_type_name)
    EditText etVipTypeName;
    @BindView(R.id.et_sort_number)
    EditText etSortNumber;
    @BindView(R.id.et_vip_type_introduce)
    EditText etVipTypeIntroduce;
    @BindView(R.id.tv_introduce_number)
    TextView tvIntroduceNumber;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    public static final String PARAM_CLASS_ID = "param_class_id";

    private String mTitle;
    private String mDescribe;
    private String mListOrder;
    private int mClassId = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_vip_type_update);
        ButterKnife.bind(this);
        mClassId = getIntent().getIntExtra(PARAM_CLASS_ID, -1);
        if (mClassId != -1) {
            setTitleAndBackPressListener("编辑会员类别");
        } else {
            setTitleAndBackPressListener("添加会员类别");
        }

        initView();
    }

    private void initView() {
        etVipTypeIntroduce.addTextChangedListener(new TextWatcher() {
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

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verify()) {
                    if (mClassId != -1) {
                        updateVipType();
                    }else{
                        addVipType();
                    }
                }
            }
        });
    }

    private boolean verify() {
        mTitle = etVipTypeName.getText().toString();
        mListOrder = etSortNumber.getText().toString();
        mDescribe = etVipTypeIntroduce.getText().toString();

        if (mClassId == -1 && TextUtils.isEmpty(mTitle)) {
            ToastUtils.showToast("请输入名称");
            return false;
        } else if(TextUtils.isEmpty(mListOrder)) {
            ToastUtils.showToast("请输入排序值");
            return false;
        } else if(TextUtils.isEmpty(mDescribe)) {
            ToastUtils.showToast("请输入介绍");
            return false;
        }
        return true;
    }

    /**
     * 添加会员分类
     *
     */
    private void addVipType() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN, ""));
        params.put("title", mTitle);
        params.put("describe", mDescribe);
        params.put("list_order", mListOrder);
        HttpLoader.getInstance().addVipType(params, mCompositeSubscription, new SubscriberCallBack(this, this) {

            @Override
            protected void onSuccess(ResponseEntity response) {
                super.onSuccess(response);
                ToastUtils.showToast("添加会员分类成功");
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
            }
        });
    }

    /**
     * 编辑会员分类
     *
     */
    private void updateVipType() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN, ""));
        params.put("class_id", mClassId);
        params.put("describe", mDescribe);
        params.put("list_order", mListOrder);
        HttpLoader.getInstance().addVipType(params, mCompositeSubscription, new SubscriberCallBack(this, this) {

            @Override
            protected void onSuccess(ResponseEntity response) {
                super.onSuccess(response);
                ToastUtils.showToast("更新会员分类成功");
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
            }
        });
    }
}
