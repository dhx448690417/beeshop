package com.beeshop.beeshop.views;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.beeshop.beeshop.R;


public class DialogFactory {


    public static Dialog createDialog(Context context, View view) {

        Dialog dialog = new Dialog(context, R.style.myStyleDialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        dialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));
        return dialog;

    }

    /**
     * 透明背景dialog
     *
     * @param context
     * @param view
     * @return
     */
    public static Dialog createCivilDialog(Context context, View view) {
        Dialog dialog = new Dialog(context, R.style.myStyleDialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        return dialog;
    }



    /**
     * /**
     *
     * @param context
     * @param view
     * @param flag    false表示屏蔽返回键
     * @return 圆角的dialog
     */
    public static Dialog createRoundDialog(Context context, View view, boolean flag) {

        Dialog dialog = new Dialog(context, R.style.myStyleDialog);
        dialog.setCanceledOnTouchOutside(flag);
        dialog.setCancelable(flag);
        dialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));
        return dialog;

    }

    /**
     * 加载loading
     *
     * @param context
     * @return
     */
    public static Dialog createLoadingDialog(Context context) {
        final Dialog dialog = new Dialog(context, R.style.MyDialogStyle);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        window.setContentView(R.layout.dialog_loading_progress_layout);
        WindowManager.LayoutParams params = window.getAttributes();
        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        params.width = widthPixels / 10 * 8;
        window.setAttributes(params);

//        ImageView iv_loading = (ImageView) window.findViewById(R.id.iv_loading);
//        final AnimationDrawable animationDrawable = (AnimationDrawable) iv_loading.getDrawable();
//        animationDrawable.start();
//        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                if (animationDrawable.isRunning()) {
//                    animationDrawable.stop();
//                }
//            }
//        });
        return dialog;

    }

}
