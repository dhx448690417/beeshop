package com.beeshop.beeshop.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.beeshop.beeshop.R;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
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
}
