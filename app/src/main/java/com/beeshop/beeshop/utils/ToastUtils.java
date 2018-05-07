package com.beeshop.beeshop.utils;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.application.BeeShopApplication;

/**
 * @author: Cooper
 * @date: 15/9/10.
 * @time: 14:49.
 * toast工具类
 */
public class ToastUtils {


    private static Toast mToast;
    private static Toast mCustomToast;

    /**
     * 防止重复toast
     */
     public static void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(BeeShopApplication.getInstance().getApplicationContext(), msg,
                    Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

//    public static void showCustomToast(String msg) {
//        if (mCustomToast == null) {
//            mCustomToast = new Toast(BeeShopApplication.getInstance().getApplicationContext());
//        }
//        View view = LayoutInflater.from(BeeShopApplication.getInstance().getApplicationContext()).inflate(R.layout.toast_custom, null);
//        TextView textView = view.findViewById(R.id.tv_toast);
//        textView.setText(msg);
//        mCustomToast.setView(view);
//        mCustomToast.setGravity(Gravity.FILL,0,0);
//        mCustomToast.setDuration(Toast.LENGTH_SHORT);
//        mCustomToast.show();
//    }

}
