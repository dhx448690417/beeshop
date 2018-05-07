package com.beeshop.beeshop.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ProgressControlInterface;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.LogUtil;
import com.beeshop.beeshop.utils.SharedPreferenceUtil;
import com.beeshop.beeshop.utils.ViewUtil;
import com.beeshop.beeshop.views.DialogFactory;
import com.beeshop.beeshop.views.StatusView;

import java.util.HashMap;


import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.subscriptions.CompositeSubscription;

import static android.content.Context.POWER_SERVICE;

/**
 * Author : cooper
 * Time :  2017/12/28 下午4:47
 * Description :
 */

public abstract class BaseFragment extends Fragment implements ProgressControlInterface, StatusView.ReloadCallBack {

    /**
     * rootView是否初始化标志，防止回调函数在rootView为空的时候触发
     */
    private boolean hasCreateView;

    /**
     * 当前Fragment是否处于可见状态标志，防止因ViewPager的缓存机制而导致回调函数的触发
     */
    private boolean isFragmentVisible;

    /**
     * onCreateView()里返回的view，修饰为protected,所以子类继承该类时，在onCreateView里必须对该变量进行初始化
     */
    protected View rootView;

    /**
     * 网络异常显示页面
     */
    protected StatusView mStatusView;

    /**
     * 是否显示进度对话框
     */
    protected boolean isShowProgressDialog = true;

    /**
     * 加载进度框
     */
    private Dialog mProgressDialog;

    /**
     * 用于记录进度条显示次数，解决多次显示progressDialog，第一次隐藏后，还有借口请求没响应结束bug。
     */
    private int mShowDialogTimes;

    /**
     * 取消接口订阅
     */
    protected CompositeSubscription mCompositeSubscription;

    public PowerManager mPowerManager;

    Unbinder unbinder;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //LogUtil.e("setUserVisibleHint() -> isVisibleToUser: " + isVisibleToUser);
        if (rootView == null) {
            return;
        }
        hasCreateView = true;
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible) {
            onFragmentVisibleChange(false);
            isFragmentVisible = false;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initVariable();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = getRootView(inflater,container);
        unbinder = ButterKnife.bind(this, rootView);
        initVariable();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!hasCreateView && getUserVisibleHint()) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
        }
        initView();
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 获取fragment布局
     * @return
     */
    protected abstract View getRootView(LayoutInflater inflater, @Nullable ViewGroup container);


    /**
     * 布局view初始化
     * @return
     */
    protected abstract void initView();

    /**
     * 数据初始化
     * @return
     */
    protected void initData(){};


    /**************************************************************
     *  自定义的回调方法，子类可根据需求重写
     *************************************************************/

    protected boolean lazyLoad;
    /**
     * 当前fragment可见状态发生变化时会回调该方法
     * 如果当前fragment是第一次加载，等待onCreateView后才会回调该方法，其它情况回调时机跟 {@link #setUserVisibleHint(boolean)}一致
     * 在该回调方法中你可以做一些加载数据操作，甚至是控件的操作，因为配合fragment的view复用机制，你不用担心在对控件操作中会报 null 异常
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible) {
        //LogUtil.e("onFragmentVisibleChange -> isVisible: " + isVisible);
    }

    private void initVariable() {
        hasCreateView = false;
        isFragmentVisible = false;
        mProgressDialog = DialogFactory.createLoadingDialog(getActivity());
        mProgressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mShowDialogTimes = 0;
            }
        });
        mPowerManager = (PowerManager) getActivity().getSystemService(POWER_SERVICE);
        mCompositeSubscription = new CompositeSubscription();
    }

    //RXjava取消注册，以避免内存泄露
    private void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    /**
     * 设置页面邮箱水印
     * @param context
     * @return
     */
    protected Drawable setBackgroundShuiYinWithEmail(Context context,int width,int height) {
        String email = SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_EMAIL, "");
        return ViewUtil.setBackgroundShuiYin(context,width,height, email);
    }

    /**
     * 显示加载进度dialog
     */
    protected void showProgressDialog() {
        mShowDialogTimes++;
        if (isShowProgressDialog && mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    /**
     * 隐藏加载进度dialog
     */
    protected void hideProgressDialog() {
        if (mShowDialogTimes > 0) {
            mShowDialogTimes--;
        }
        if (mProgressDialog != null && mProgressDialog.isShowing() && mShowDialogTimes == 0) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * 是否显示加载框
     * @param isShow
     */
    protected void isShowProgressDialog(boolean isShow) {
        this.isShowProgressDialog = isShow;
    }

    @Override
    public void showProgress() {
        showProgressDialog();
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }

        /**
     * 显示网络异常页面
     */
    protected void showNoNetWork() {
        hideNoContentView();
        if (mStatusView == null) {
            mStatusView = (StatusView) rootView.findViewById(R.id.status_view);
        }
        mStatusView.setmReloadCallBack(this);
        mStatusView.setNoNetStatus();
        mStatusView.setVisibility(View.VISIBLE);
    }

    /**
     * 显示无数据view
     */
    protected void showNoContentView() {
        if (mStatusView == null) {
            mStatusView = (StatusView) rootView.findViewById(R.id.status_view);
        }
        mStatusView.setNoContentStatus();
        mStatusView.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏无数据vbiew
     */
    protected void hideNoContentView() {
        if (mStatusView == null) {
            return;
        }
        mStatusView.setVisibility(View.GONE);
    }

    @Override
    public void reload() {
        mStatusView.setVisibility(View.GONE);
        reFrensh();
    }

    /**
     * 刷新页面
     */
    protected void reFrensh() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        onUnsubscribe();
        hideProgressDialog();
    }
}
