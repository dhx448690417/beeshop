package com.beeshop.beeshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.fragment.ClientFragment;
import com.beeshop.beeshop.fragment.HomeFragment;
import com.beeshop.beeshop.fragment.MineFragment;
import com.beeshop.beeshop.fragment.StationFragment;
import com.beeshop.beeshop.utils.SharedPreferenceUtil;
import com.beeshop.beeshop.utils.StatusBarUtil;
import com.beeshop.beeshop.views.BottomTabView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fl_main_container)
    FrameLayout flMainContainer;
    @BindView(R.id.tab_bottom)
    TabLayout tabBottom;

    private HomeFragment mHomeFragment;
    private ClientFragment mClientFragment;
    private StationFragment mStationFragment;
    private MineFragment mMineFragment;

    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;

    private int mTabIndex = 0; // tab的索引

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mFragmentManager = getSupportFragmentManager();
        // 设置状态栏颜色
//        StatusBarUtil.setColor(this, ContextCompat.getColor(this,R.color.title_backgroud_color),ContextCompat.getColor(this,R.color.transparent));
        initView();
        if (savedInstanceState != null) { // 解决应用后台杀死后，再次打开，fragment错乱问题
            mTabIndex = savedInstanceState.getInt("tab_index",0);
            mHomeFragment = (HomeFragment) mFragmentManager.findFragmentByTag(HomeFragment.class.getSimpleName());
            mClientFragment = (ClientFragment) mFragmentManager.findFragmentByTag(ClientFragment.class.getSimpleName());
            mStationFragment = (StationFragment) mFragmentManager.findFragmentByTag(StationFragment.class.getSimpleName());
            mMineFragment = (MineFragment) mFragmentManager.findFragmentByTag(MineFragment.class.getSimpleName());
            tabBottom.getTabAt(mTabIndex).select();
        }

    }

    private void initView() {
        tabBottom.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mTabIndex = tab.getPosition();
                ((BottomTabView) tab.getCustomView()).setTabStatus(true);
                setSelectedFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ((BottomTabView) tab.getCustomView()).setTabStatus(false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabBottom.addTab(tabBottom.newTab().setCustomView(new BottomTabView(this,R.drawable.icon_home_normal,R.drawable.icon_home_selected,"蜂店")));
        tabBottom.addTab(tabBottom.newTab().setCustomView(new BottomTabView(this,R.drawable.icon_news_normal,R.drawable.icon_news_selected,"客户")));
        tabBottom.addTab(tabBottom.newTab().setCustomView(new BottomTabView(this,R.drawable.icon_station_normal,R.drawable.icon_station_selected,"电台")));
        tabBottom.addTab(tabBottom.newTab().setCustomView(new BottomTabView(this,R.drawable.icon_account_normal,R.drawable.icon_account_selected,"我的")));

        for (int i = 0; i < tabBottom.getTabCount(); i++) {  // 判断是否登录，没有登录则跳转到登录页面
            View tabView = (View) tabBottom.getTabAt(i).getCustomView().getParent();
            tabView.setTag(i);
            tabView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        int pos = (int) v.getTag();
                        if ((pos == 1 || pos == 3) && TextUtils.isEmpty(SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN, ""))) {
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            });

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab_index", mTabIndex);
    }

    /**
     * 根据当前fragment设置底部tab状态
     */
    public void setTabBottomSelectedStatus() {
        if (mCurrentFragment == mHomeFragment) {
            tabBottom.getTabAt(0).select();
        } else if (mCurrentFragment == mClientFragment) {
            tabBottom.getTabAt(1).select();
        } else if (mCurrentFragment == mStationFragment) {
            tabBottom.getTabAt(2).select();
        }
        else if (mCurrentFragment == mMineFragment) {
            tabBottom.getTabAt(3).select();
        }

    }

    private void setSelectedFragment(int position) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        hideFragment(ft);

        if (position == 0) { //首页

            mHomeFragment = (HomeFragment) mFragmentManager.findFragmentByTag(HomeFragment.class.getSimpleName());
            if (mHomeFragment == null) {
                mHomeFragment = new HomeFragment();
                ft.add(R.id.fl_main_container, mHomeFragment, HomeFragment.class.getSimpleName());
            } else {
                ft.show(mHomeFragment);
            }
            mCurrentFragment = mHomeFragment;

        } else if (position == 1) { //客户
            mClientFragment = (ClientFragment) mFragmentManager.findFragmentByTag(ClientFragment.class.getSimpleName());//无ui获取fragment
            if (mClientFragment == null) {
                mClientFragment = new ClientFragment();
                ft.add(R.id.fl_main_container, mClientFragment, ClientFragment.class.getSimpleName());
            } else {
                ft.show(mClientFragment);
            }
            mCurrentFragment = mClientFragment;
        }
        else if (position == 2) { //应用中心
            mStationFragment = (StationFragment) mFragmentManager.findFragmentByTag(StationFragment.class.getSimpleName());//无ui获取fragment
            if (mStationFragment == null) {
                mStationFragment = new StationFragment();
                ft.add(R.id.fl_main_container, mStationFragment, StationFragment.class.getSimpleName());
            } else {
                ft.show(mStationFragment);
            }
            mCurrentFragment = mStationFragment;
        }
        else { //我的
            if (TextUtils.isEmpty(SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN, ""))) {
                startActivity(new Intent(this,LoginActivity.class));
                return;
            }
            mMineFragment = (MineFragment) mFragmentManager.findFragmentByTag(MineFragment.class.getSimpleName());
            if (mMineFragment == null) {
                mMineFragment = new MineFragment();
                ft.add(R.id.fl_main_container, mMineFragment, MineFragment.class.getSimpleName());
            } else {
                ft.show(mMineFragment);
            }
            mCurrentFragment = mMineFragment;
        }

        ft.commitAllowingStateLoss();
    }

    private void hideFragment(FragmentTransaction ft) {

        if (mHomeFragment != null) {
            ft.hide(mHomeFragment);
        }

        if (mClientFragment != null) {
            ft.hide(mClientFragment);
        }

        if (mStationFragment != null) {
            ft.hide(mStationFragment);
        }

        if (mMineFragment != null) {
            ft.hide(mMineFragment);
        }

    }


}
