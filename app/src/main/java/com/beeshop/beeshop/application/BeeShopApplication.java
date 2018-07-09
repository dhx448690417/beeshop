package com.beeshop.beeshop.application;

import android.app.Application;
import android.content.Context;

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

        CrashReport.initCrashReport(getApplicationContext(), "383c85e78e", false);
    }


}
