package com.beeshop.beeshop.application;

import android.app.Application;
import android.content.Context;

import com.beeshop.beeshop.config.AppConfig;
import com.beeshop.beeshop.utils.DeviceUtil;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Author : cooper
 * Time :  2017/12/27 下午6:59
 * Description :
 */

public class BeeShopApplication extends Application {

    private static BeeShopApplication mInstance;
    private static Context mContext;


    public static Context getContext() {
        return mContext;
    }

    public static BeeShopApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mContext = this.getApplicationContext();
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        strategy.setAppPackageName(getPackageName());
        strategy.setAppVersion(DeviceUtil.getVersion(getApplicationContext()));
        strategy.setAppReportDelay(10000);
        CrashReport.initCrashReport(getApplicationContext(), "383c85e78e", AppConfig.DEBUG);
        CrashReport.setIsDevelopmentDevice(getApplicationContext(), AppConfig.DEBUG);
    }


}
