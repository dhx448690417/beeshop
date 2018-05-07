package com.beeshop.beeshop.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.WindowManager;

import com.beeshop.beeshop.R;


/**
 * Author : Cooper
 * Time : 2017/3/7  11:14
 * Description : view工具类
 */

public class ViewUtil {

    /**
     * 获取view高度
     * @param view
     * @return
     */
    public static float getViewHeight(View view) {
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(width,height);
        return view.getMeasuredHeight();
    }

    /**
     * 获取view宽度
     * @param view
     * @return
     */
    public static float getViewWidth(View view) {
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(width,height);
        return view.getMeasuredWidth();
    }

    /**
     * 获取屏幕宽高
     * @param context
     * @return wh[0]宽  wh[1]高
     */
    public static int[] getScreenWidthAndHeight(Context context) {
        int[] wh = new int[2];
        WindowManager wm1 = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wh[0] = wm1.getDefaultDisplay().getWidth();
        wh[1] = wm1.getDefaultDisplay().getHeight();
        return wh;
    }

    public static BitmapDrawable setBackgroundShuiYin(Context context,int width,int height,String content) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#EBEDF0"));
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(DensityUtil.dip2px(context,20));
        for(int i = 0;i<3;i++) {
            for(int j = 0;j<2;j++) {
                Path path = new Path();
                path.moveTo(width/50+j*width/2, height/60+i*height/3);
                path.lineTo(width/2+j*width/2, height/3+i*height/3);
                canvas.drawTextOnPath(content, path, 20, 0, paint);
            }
        }
        BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
        return bitmapDrawable;

    }
}
