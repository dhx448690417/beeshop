package com.beeshop.beeshop.views;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beeshop.beeshop.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Author : cooper
 * Time :  2018/6/15 下午3:50
 * Description : 纵向单选view
 */
public class TypeRadioView extends LinearLayout {

    private Context mContext;
    private List<TypeItem> mTypeItemList = new ArrayList<>();
    private TypeRadioCheckedCallBack mTypeRadioCheckedCallBack;

    public void setTypeRadioCheckedCallBack(TypeRadioCheckedCallBack typeRadioCheckedCallBack) {
        this.mTypeRadioCheckedCallBack = typeRadioCheckedCallBack;
    }

    public TypeRadioView(Context context) {
        super(context);
        initView(context);
    }

    public TypeRadioView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TypeRadioView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        this.setOrientation(VERTICAL);
        this.setBackgroundResource(R.color.white);
    }

    public void addTypeItem(List<TypeItem> list) {
        this.mTypeItemList.clear();
        this.removeAllViews();
        this.mTypeItemList.addAll(list);
        for (int i = 0; i < list.size(); i++) {
            TypeItem typeItem = list.get(i);
            addRadioView(typeItem,i);
            View view = new View(mContext);
            view.setBackgroundColor(ContextCompat.getColor(mContext,R.color.line_color));
            LinearLayout.LayoutParams viewLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1,
                    getResources().getDisplayMetrics()));
            this.addView(view,viewLp);
        }
        requestLayout();
    }

    private void addRadioView(final TypeItem typeItem,final int position) {
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(HORIZONTAL);
        linearLayout.setPadding(30,50,30,50);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        linearLayout.setId(position);

        ImageView imageView = new ImageView(mContext);
        if (typeItem.isSelected) {
            imageView.setBackgroundResource(R.drawable.icon_chat_album_selected);
        } else {
            imageView.setBackgroundResource(R.drawable.icon_chat_album_unselected);
        }

        imageView.setId(position+100);
        linearLayout.addView(imageView);

        TextView textView = new TextView(mContext);
        textView.setText(typeItem.lable);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,17);
        textView.setTextColor(ContextCompat.getColor(mContext,R.color.text_color_black));

        LinearLayout.LayoutParams tvLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvLp.leftMargin = 20;

        linearLayout.addView(textView,tvLp);

        linearLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < mTypeItemList.size(); i++) {
                    if (i == position) {
                        mTypeItemList.get(i).isSelected = true;
                    } else {
                        mTypeItemList.get(i).isSelected = false;
                    }
                }
                if (mTypeRadioCheckedCallBack != null) {
                    mTypeRadioCheckedCallBack.itemChecked(position,typeItem.key);
                }
                refreshViews();
            }
        });
        this.addView(linearLayout);
    }

    private void refreshViews() {
        if (TypeRadioView.this.getChildCount() > 0) {
            for (int i = 0; i < mTypeItemList.size(); i++) {
                LinearLayout linearLayout = (LinearLayout) TypeRadioView.this.findViewById(i);
                ImageView imageView = (ImageView) linearLayout.findViewById(i+100);
                if (mTypeItemList.get(i).isSelected) {
                    imageView.setBackgroundResource(R.drawable.icon_chat_album_selected);
                } else {
                    imageView.setBackgroundResource(R.drawable.icon_chat_album_unselected);
                }
            }
        }
    }

    public static Creator with() {
        return new Creator();
    }

    public static class Creator {
        private final List<TypeItem> typeItemList;
        public Creator() {
            typeItemList = new ArrayList<>();
        }
        public Creator addItem(TypeItem item) {
            typeItemList.add(item);
            return this;
        }

        public List<TypeItem> create() {
            return typeItemList;
        }
    }

    public static class TypeItem{

        public TypeItem(String lable, String key, boolean isSelected) {
            this.lable = lable;
            this.key = key;
            this.isSelected = isSelected;
        }

        public String lable;
        public String key;
        public boolean isSelected;
    }

    public interface TypeRadioCheckedCallBack{
        void itemChecked(int position, String key);
    }

}
