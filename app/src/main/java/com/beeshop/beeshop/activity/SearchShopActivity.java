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

import java.util.ArrayList;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_search_shop);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        final List<String> tags = new ArrayList<>();
        tags.add("医院");
        tags.add("理发店");
        tags.add("书店");
        tags.add("学校");
        tags.add("工商银行");
        tags.add("流行服装");
        tagShopType.setTags(tags);
        tagShopType.setOnTagClickListener(new TagCloudView.OnTagClickListener() {
            @Override
            public void onTagClick(int position) {
                etSearch.setText(tags.get(position));
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
}
