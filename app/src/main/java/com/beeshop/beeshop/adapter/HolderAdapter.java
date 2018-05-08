package com.beeshop.beeshop.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ah on 2016/1/10.
 */
public abstract class HolderAdapter<T> extends BaseAdapter {

    private Context context;
    private List<T> dateList;

    public HolderAdapter(Context context, List<T> dateList) {
        this.context = context;
        this.dateList = dateList;
    }

    @Override
    public int getCount() {
        return dateList != null?dateList.size():0;
    }

    @Override
    public Object getItem(int position) {
        return dateList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(buildLayoutView(),parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        buildViewData(context,viewHolder,dateList.get(position),position);
        return convertView;
    }

    /**
     * 绑定item的layout
     * @return
     */
    public abstract int buildLayoutView();

    /**
     * 填充具体数据逻辑
     * @param holder
     * @param t
     * @param position
     */
    public abstract void buildViewData(Context context, ViewHolder holder, T t, int position);

    /**
     * 清除数据并且页面刷新
     */
    public void clear(){
        dateList.clear();
        this.notifyDataSetChanged();
    }

    /**
     * 添加数据并且刷新页面
     * @param dateList
     */
    public void addAllDateList(List<T> dateList){
        this.dateList.addAll(dateList);
        this.notifyDataSetChanged();
    }

    public class ViewHolder {
        private final SparseArray<View> mViews;
        private View mConvertView;

        public ViewHolder(View convertView) {
            this.mViews = new SparseArray<>();
            this.mConvertView = convertView;
        }

        public <T extends View> T getView(int viewId) {

            View view = mViews.get(viewId);
            if (view == null) {
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        /**
         * 获取 TextView
         * @param viewId
         * @return
         */
        public TextView getTextView(int viewId){
            return getView(viewId);
        }

        /**
         * 获取 ImageView
         * @param viewId
         * @return
         */
        public ImageView getImageView(int viewId){
            return getView(viewId);
        }

        /**
         * 获取 LinearLayout
         * @param viewId
         * @return
         */
        public LinearLayout getLinearLayout(int viewId){
            return getView(viewId);
        }

        /**
         * 获取 RelativeLayout
         * @param viewId
         * @return
         */
        public RelativeLayout getRelativeLayout(int viewId){
            return getView(viewId);
        }

        /**
         * 获取 FrameLayout
         * @param viewId
         * @return
         */
        public FrameLayout getFrameLayout(int viewId){
            return getView(viewId);
        }

        /**
        * 获取 Button
        * @param viewId
        * @return
        */
        public Button getButton(int viewId){
            return getView(viewId);
        }

        /**
         * TextView设置文字
         * @param viewId
         * @param textViewContent
         * @return
         */
        public TextView setText(int viewId, String textViewContent){
            TextView textView = getView(viewId);
            textView.setText(textViewContent);
            return textView;
        }

        /**
         * TextView 设置显示状态
         * @param viewId
         * @param visibility
         * @return
         */
        public TextView setTextViewVisibility(int viewId, int visibility){
            TextView textView = getView(viewId);
            textView.setVisibility(visibility);
            return textView;
        }

        /**
         * TextView 设置文字颜色
         * @param viewId
         * @param textViewColor
         * @return
         */
        public TextView setTextViewColor(int viewId, int textViewColor){
            TextView textView = getView(viewId);
            textView.setTextColor(textViewColor);
            return textView;
        }

        /**
         * 设置ImageView背景图片
         * @param viewId
         * @param BackgroundId
         * @return
         */
        public ImageView setImageViewBackgroundId(int viewId, int BackgroundId){
            ImageView imageView = getView(viewId);
            imageView.setBackgroundResource(BackgroundId);
            return imageView;
        }

    }
}
