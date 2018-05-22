package com.beeshop.beeshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.model.Shop;
import com.beeshop.beeshop.model.ShopCategoryEntity;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.next.tagview.TagCloudView;

/**
 * Author：cooper
 * Time：  2018/5/20 上午10:39
 * Description：
 */
public class SearchShopActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tag_shop_type)
    TagCloudView tagShopType;

    private List<String> mTags = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_search_shop);
        ButterKnife.bind(this);

        initView();
        getShopCategory();
    }

    private void initView() {
        tagShopType.setOnTagClickListener(new TagCloudView.OnTagClickListener() {
            @Override
            public void onTagClick(int position) {
                etSearch.setText(mTags.get(position));
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                this.finish();
                break;
            case R.id.tv_search:
                startActivity(new Intent(this,SearchResultActivity.class));
                break;
        }
    }

    private void getShopCategory() {
        HashMap<String, Object> params1 = new HashMap<>();
//        params1.put("category", "");
        HttpLoader.getInstance().getShopCategory(params1, mCompositeSubscription, new SubscriberCallBack<ShopCategoryEntity>(this,this){

            @Override
            protected void onSuccess(ShopCategoryEntity response) {
                super.onSuccess(response);
                for (ShopCategoryEntity.ListBean listBean : response.getList()) {
                    mTags.add(listBean.getName());
                }
                tagShopType.setTags(mTags);
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
            }
        });
    }
}
