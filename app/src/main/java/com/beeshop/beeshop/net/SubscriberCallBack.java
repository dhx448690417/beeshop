package com.beeshop.beeshop.net;

import android.app.Activity;
import android.content.Intent;


import com.beeshop.beeshop.application.BeeShopApplication;
import com.beeshop.beeshop.utils.LogUtil;
import com.beeshop.beeshop.utils.NetworkUtils;
import com.beeshop.beeshop.utils.ToastUtils;

import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;

import javax.net.ssl.SSLHandshakeException;

import rx.Subscriber;

/**
 * Author : Cooper
 * Time : 2018/1/2  15:40
 * Description : 返回数据封装处理
 */
public abstract class SubscriberCallBack<T> extends Subscriber<ResponseEntity<T>> {

    private final int ERROR_CODE_NO_LOGIN = 401; //用户未登录
    private final int ERROR_CODE_OTHER_LOGIN = 1510; //其他设备登录下线处理
    private final int ERROR_CODE_OTHER_EXCEPTION = 1000; //其他异常
    private final int ERROR_CODE_NO_NET = 1001; //网络未连接
    private final int ERROR_CODE_NET_CONNECT_EXCEPTION = 1002; //网络连接异常

    private ProgressControlInterface mProgressControlInterface;
    private WeakReference<Activity> mActivityWeakReference;

    public SubscriberCallBack(Activity activity, ProgressControlInterface mProgressControlInterface) {
        this.mProgressControlInterface = mProgressControlInterface;
        this.mActivityWeakReference = new WeakReference<Activity>(activity);
    }

    public SubscriberCallBack(ProgressControlInterface mProgressControlInterface) {
        this.mProgressControlInterface = mProgressControlInterface;
    }

    public SubscriberCallBack() {
    }

    @Override
    public void onNext(ResponseEntity responseEntity) {
        if (responseEntity.getCode() == 200 ) {
            if (responseEntity.getData() != null) { // 有返回业务数据data
                onSuccess((T) responseEntity.getData());
            } else { // 无返回业务数据data
                onSuccess();
            }
            onSuccess(responseEntity);// 后台强调有其他code类型时，需要业务页面处理成功code，分别处理各种code对应逻辑
        } else{
            handleParticularErrorMessage(responseEntity);
        }
//        LoggerUtil.d(JsonUtil.formatJson(GsonUtil.gsonString(responseEntity)));
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mProgressControlInterface != null) {
            mProgressControlInterface.showProgress();
        }
        if (!NetworkUtils.isNetworkAvailable(BeeShopApplication.getContext())) {
            ResponseEntity errorBean = new ResponseEntity();
            errorBean.setCode(ERROR_CODE_NO_NET);
            errorBean.setMsg("网络未连接");
            onNetFailure(errorBean);
            onFailure(errorBean);
            onCompleted();
        }
    }

    @Override
    public void onCompleted() {
        if (mProgressControlInterface != null) {
            mProgressControlInterface.hideProgress();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (NetworkUtils.isNetworkAvailable(BeeShopApplication.getContext())) {
            ResponseEntity errorBean = new ResponseEntity();
            try {
                throw (Exception) e;
            } catch (SocketTimeoutException e1) {
                e1.printStackTrace();
                errorBean.setCode(ERROR_CODE_NET_CONNECT_EXCEPTION);
                errorBean.setMsg("网络连接异常");
                onNetFailure(errorBean);
            } catch (SSLHandshakeException e1) {
                e1.printStackTrace();
                errorBean.setCode(ERROR_CODE_NET_CONNECT_EXCEPTION);
                errorBean.setMsg("网络连接异常");
                onNetFailure(errorBean);
            } catch (Exception e1) {
                e1.printStackTrace();
                LogUtil.e("ah   SubscriberCallBack  onError == "+e.getMessage());
                errorBean.setCode(ERROR_CODE_OTHER_EXCEPTION);
                errorBean.setMsg("服务器异常");
            }
            onFailure(errorBean);
            onCompleted();
        }
    }

    /**
     * 提前检查错误信息，处理特殊错误code
     * @param errorBean
     */
    private void handleParticularErrorMessage(ResponseEntity errorBean) {
        // 用户登录过期与被挤下线单独处理 无需回调error信息
        if (errorBean.getCode() == ERROR_CODE_NO_LOGIN || errorBean.getCode() == ERROR_CODE_OTHER_LOGIN) {
            String message = errorBean.getMsg();
            Activity activity = mActivityWeakReference.get();
            ToastUtils.showToast(message);
//            activity.startActivity(new Intent(activity, LoginActivity.class));

        }
        onFailure(errorBean);
    }

    /**
     * 直接获取返回结果
     * @param response
     */
    protected void onSuccess(T response){};

    /**
     * 需要返回code
     * @param response
     */
    protected void onSuccess(ResponseEntity<T> response){};

    /**
     * 需要返回code
     */
    protected void onSuccess(){};

    /**
     * 失败回调（错误信息包含服务器返回错误code和本地网络未连接、网络连接异常code）
     * @param errorBean
     */
    protected abstract void onFailure(ResponseEntity errorBean);

    /**
     * 失败回调 （仅包含 本地网络未连接 和 网络连接异常 ，供一些页面对网络异常添加特殊处理）
     * @param errorBean
     */
    protected void onNetFailure(ResponseEntity errorBean) {}


}
